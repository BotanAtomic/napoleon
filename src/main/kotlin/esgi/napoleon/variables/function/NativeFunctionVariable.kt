package esgi.napoleon.variables.function

import esgi.napoleon.error.ArgumentTypeError
import esgi.napoleon.error.SyntaxError
import esgi.napoleon.error.TypeError
import esgi.napoleon.error.UnknownError
import esgi.napoleon.stdlib.Cached
import esgi.napoleon.stdlib.Environment
import esgi.napoleon.stdlib.Filter
import esgi.napoleon.stdlib.NativeFunction
import esgi.napoleon.variables.FunctionArguments
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.Void
import esgi.napoleon.variables.classToType
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.extensionReceiverParameter
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.valueParameters

fun FunctionArguments.findByName(name: String?): Variable<*>? = this?.find { it.first == name }?.second

fun FunctionArguments.findByIndex(index: Int): Variable<*>? {
    if (index >= (this?.size ?: 0)) return null
    val argument = this?.get(index)

    if (argument?.first != null) return null

    return argument?.second
}

private fun NativeFunctionVariable.checkArguments(
    arguments: FunctionArguments,
    parameters: List<KParameter>,
) {
    if (arguments == null) return

    val functionName = this.value.name
    var hasKeywordArgument = false
    arguments.forEach { (key, _) ->
        if (key == null && hasKeywordArgument) throw SyntaxError("positional argument follows keyword argument")
        hasKeywordArgument = key != null
    }

    if (parameters.size < arguments.size && !this.hasVarargs) throw TypeError("$functionName() take ${parameters.size} argument(s) but ${arguments.size} were given")

    arguments.filter { it.first != null }.forEach { (key, _) ->
        if (parameters.firstOrNull { it.name == key } == null) throw TypeError("$functionName() got an unexpected keyword argument '$key'")
    }

    if (arguments.filter { it.first != null }.groupingBy { it.first }.eachCount()
            .any { it.value > 1 }
    ) throw SyntaxError("keyword argument repeated")
}

class NativeFunctionVariable(
    nativeFunction: NativeFunction,
    private val environment: esgi.napoleon.DefaultExecutionEnvironment,
    private val source: Variable<*>? = null,
) : Variable<NativeFunction>(nativeFunction) {

    val hasVarargs = nativeFunction.function.valueParameters.any { it.isVararg }

    private val useCache = nativeFunction.function.hasAnnotation<Cached>()

    private var cache: HashMap<List<Any?>?, Variable<*>>? = if (useCache) HashMap() else null

    override fun type() = "native_function"

    override fun call(arguments: FunctionArguments): Variable<*> {
        val function = value.function
        val functionName = value.name

        val parameters = function.valueParameters

        checkArguments(arguments, parameters)

        var indexOffset = 0

        val toInject = HashMap<KParameter, Any?>()
        function.instanceParameter?.let {
            toInject[it] = value.instance
            indexOffset += 1
        }
        function.extensionReceiverParameter?.let {
            toInject[it] = source
            indexOffset += 1
        }

        parameters.find { it.hasAnnotation<Environment>() }?.let { toInject[it] = environment }

        val serializedArguments = if (useCache)
            ArrayList<Any?>()
        else null

        if (arguments != null) {
            function.valueParameters.filter { !it.hasAnnotation<Environment>() }.forEach {
                val variable: Any? = if (!it.isVararg) {
                    arguments.findByName(it.name) ?: arguments.findByIndex(it.index - indexOffset)
                } else {
                    val varargVariables = arguments.takeWhile { (first, _) ->
                        first == null
                    }.map { (_, second) -> second }.toTypedArray()

                    indexOffset -= varargVariables.size
                    varargVariables
                }

                Filter.apply(it, variable)

                if (variable != null) {
                    toInject[it] = variable
                    if (variable is Variable<*>)
                        serializedArguments?.add(variable.value)
                }
            }
        }



        if (useCache) {
            val cachedValue = cache?.get(serializedArguments)
            if (cachedValue != null)
                return cachedValue
        }

        parameters.filter { !toInject.containsKey(it) && !it.isOptional }.let { missingArguments ->
            if (missingArguments.isNotEmpty()) throw TypeError(
                "$functionName() missing ${missingArguments.size} required positional argument(s):" + " ${missingArguments.joinToString { "'${it.name}'" }}"
            )
        }

        toInject.forEach { (parameter, value) ->
            if (parameter.type.classifier is KClass<*>) {
                if (!(parameter.type.classifier as KClass<*>).isInstance(value) && value is Variable<*>) throw ArgumentTypeError(
                    "$functionName() parameter '${parameter.name}' required '${classToType(parameter.type.classifier)}' but got '${value.type()}'"
                )
            }
        }

        val executionResult = kotlin.runCatching { function.callBy(toInject) }
        if (executionResult.isSuccess) {
            val result = executionResult.getOrNull()

            if (result is Variable<*>) {
                if (useCache)
                    cache?.set(serializedArguments, result)
                return result
            }
        } else {
            val exception = executionResult.exceptionOrNull()

            if (exception is InvocationTargetException)
                throw exception.targetException

            throw UnknownError("failed to execute ${functionName}()")
        }
        return Void
    }

    override fun valueToString(): String {
        return "native function '${value.function.name}'"
    }

    fun createExtension(source: Variable<*>?) = NativeFunctionVariable(value, environment, source)
}

