import io.deepn.script.DeepScriptEnvironment
import io.deepn.script.variables.Variable

private fun compare(a: Any?, b: Any?): Boolean {
    if ((a is Int || b is Int) && (a is Long || b is Long)) {
        if (a is Int) return a.compareTo(b as Long) == 0
        else if (a is Long) return a.compareTo(b as Int) == 0
    }

    if(a != null && b != null && a::class == Any::class && b::class == Any::class) {
        return true
    }

    return a == b
}

fun scriptAssert(code: String, value: Any?) {
    val compiler = DeepScriptEnvironment(code)

    val (grammarErrors, compilationTime, compilationSuccess) = compiler.compile()

    if (!compilationSuccess)
        throw Error("Compilation failed (${compilationTime}ms) : $grammarErrors")

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
                assert(compare(currentVariable.value, value[index])) {
                    "Value is '${currentVariable.value}' but expected '${value[index]}'"
                }
        }
    } else {
        assert(compare(variable?.value, value)) {
            "Value is '${variable?.value}' but expected '$value'"
        }
    }

}
