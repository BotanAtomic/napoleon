import io.deepn.script.DefaultExecutionEnvironment
import io.deepn.script.error.DeepScriptError
import io.deepn.script.error.SyntaxErrorEnum
import io.deepn.script.variables.Null
import io.deepn.script.variables.Variable
import io.deepn.script.variables.memory.MemoryAddressVariable
import kotlin.reflect.KClass

private fun compare(aVariable: Variable<*>?, b: Any?): Boolean {
    val a = aVariable?.value
    if ((a is Int || b is Int) && (a is Long || b is Long)) {
        if (a is Int) return a.compareTo(b as Long) == 0
        else if (a is Long) return a.compareTo(b as Int) == 0
    }

    if (a != null && b != null && a::class == Any::class && b::class == Any::class) {
        return true
    }
    if (aVariable is MemoryAddressVariable && b is Null || aVariable is Null && b is MemoryAddressVariable) return true

    return a == b
}


fun scriptAssert(code: String, value: Any?, numberOfExecution: Int = 1) {
    val compiler = DefaultExecutionEnvironment(code)

    val (grammarErrors, compilationTime, compilationSuccess) = compiler.compile()

    if (!compilationSuccess)
        throw Error("Compilation failed (${compilationTime}ms) : $grammarErrors")

    for (i in 0 until numberOfExecution - 1) {
        compiler.execute().let {
            if (!it.success)
                throw Error("Execution failed (${it.time}ms): ${it.error}")
        }
    }

    val (error, executionTime, variable, success) = compiler.execute()

    if (!success)
        throw Error("Execution failed (${executionTime}ms): $error")

    if (value == null) return

    if (value is List<*> && variable?.value is List<*>) {
        assert(value.size == (variable.value as List<*>).size) {
            "List size comparison failed: expected ${value.size} but got ${(variable.value as List<*>).size}"
        }
        (variable.value as List<*>).forEachIndexed { index, currentVariable ->
            if (currentVariable is Variable<*>)
                assert(compare(currentVariable, value[index])) {
                    "Value is '${currentVariable.value}' but expected '${value[index]}'"
                }
        }
    } else {
        assert(compare(variable, value)) {
            "Value is '${variable?.value}' but expected '$value'"
        }
    }

}

fun scriptAssertThrowable(code: String, value: Any?, numberOfExecution: Int = 1) {
    val compiler = DefaultExecutionEnvironment(code)

    val (grammarErrors, _, compilationSuccess) = compiler.compile()

    if (!compilationSuccess) {
        return when (value) {
            is SyntaxErrorEnum -> {
                assert(grammarErrors.size == 1) {
                    "List has more error than expected: ${grammarErrors.size} but got $grammarErrors"
                }
                assert(grammarErrors.all { it.type == value }) {
                    "value is $value, expected $grammarErrors"
                }
            }
            is List<*> -> {
                assert(value.size == grammarErrors.size) {
                    "List size comparison failed: expected ${value.size} but got ${(grammarErrors.size)}"
                }
                assert(grammarErrors.map { it.type }
                    .containsAll(value)) { { "value is $value, expected $grammarErrors" } }
            }
            else -> throw Error("Compilation: Unsupported value $value")
        }
    }

    for (i in 0 until numberOfExecution - 1) {
        compiler.execute()
    }

    val (error, executionTime, _, success) = compiler.execute()

    if (!success) {
        return when (value) {
            is String -> assert(error?.type == value) { "value is $value, expected ${error?.type}" }
            is DeepScriptError -> assert(error?.type == value.javaClass.simpleName) { "value is $value, expected ${error?.type}" }
            is KClass<*> -> assert(error?.type == value.java.simpleName) { "value is $value, expected ${error?.type}" }
            else -> throw Error("Execution: Unsupported value $value")
        }
    }

    throw Error("No Execution error thrown, Executed in (${executionTime}ms)")
}

