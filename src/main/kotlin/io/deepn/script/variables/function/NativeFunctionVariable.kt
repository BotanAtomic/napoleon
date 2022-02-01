package io.deepn.script.variables.function

import io.deepn.script.DeepScriptEnvironment
import io.deepn.script.error.ArgumentTypeError
import io.deepn.script.error.SyntaxError
import io.deepn.script.error.TypeError
import io.deepn.script.error.UnknownError
import io.deepn.script.stdlib.Environment
import io.deepn.script.stdlib.NativeFunction
import io.deepn.script.variables.FunctionArguments
import io.deepn.script.variables.Null
import io.deepn.script.variables.Variable
import io.deepn.script.variables.error.ErrorVariable
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.extensionReceiverParameter
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.valueParameters

fun FunctionArguments.findByName(name: String): Variable<*>? = this?.find { it.first == name }?.second

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
        if (key == null && hasKeywordArgument)
            throw SyntaxError("positional argument follows keyword argument")
        hasKeywordArgument = key != null
    }

    if (parameters.size < arguments.size && !this.hasVarargs)
        throw TypeError("$functionName() take ${parameters.size} argument(s) but ${arguments.size} were given")

    arguments.filter { it.first != null }.forEach { (key, _) ->
        if (parameters.firstOrNull { it.name == key } == null)
            throw TypeError("$functionName() got an unexpected keyword argument '$key'")
    }

    if (arguments.filter { it.first != null }.groupingBy { it.first }.eachCount().any { it.value > 1 })
        throw SyntaxError("keyword argument repeated")
}

class NativeFunctionVariable(
    nativeFunction: NativeFunction,
    private val environment: DeepScriptEnvironment,
    private val source: Variable<*>? = null,
) : Variable<NativeFunction>(nativeFunction) {

    val hasVarargs = nativeFunction.function.valueParameters.any { it.isVararg }

    override fun type() = "native_function"

    override fun call(arguments: FunctionArguments): Variable<*> {
        val function = value.function
        val functionName = value.name

        val toInject = HashMap<KParameter, Any?>()
        function.instanceParameter?.let { toInject[it] = value.instance }
        function.extensionReceiverParameter?.let { toInject[it] = source }

        val parameters = function.valueParameters

        checkArguments(arguments, parameters)

        var indexOffset = 0
        function.instanceParameter?.let { indexOffset += 1 }

        parameters.find { it.hasAnnotation<Environment>() }?.let { toInject[it] = environment }

        if (arguments != null) {
            function.valueParameters.filter { !it.hasAnnotation<Environment>() }.forEach {
                val realIndex = it.index - indexOffset
                if (!it.isVararg) {
                    toInject[it] = arguments.findByName(it.name ?: "") ?: arguments.findByIndex(realIndex)
                } else {
                    val varargVariables = arguments
                        .takeWhile { (first, _) -> first == null }
                        .map { (_, second) -> second }
                        .toTypedArray()

                    indexOffset -= varargVariables.size
                    toInject[it] = varargVariables
                }
                if (it.isOptional && toInject[it] == null)
                    toInject.remove(it)
            }
        }

        toInject.forEach { (parameter, value) ->
            if (parameter.type.classifier is KClass<*>) {
                if (!(parameter.type.classifier as KClass<*>).isInstance(value))
                    throw ArgumentTypeError("$functionName() parameter '${parameter.name}' required '${parameter}' ")
            }
        }

        val executionResult = kotlin.runCatching { function.callBy(toInject) }
        if (executionResult.isSuccess) {
            val result = executionResult.getOrNull()
            if (result is ErrorVariable)
                throw result.value

            if (result is Variable<*>)
                return result
        } else {
            executionResult.exceptionOrNull()?.printStackTrace()
            throw UnknownError("failed to execute ${functionName}()")
        }
        return Null
    }

    override fun valueToString(): String {
        return "native function '${value.function.name}'"
    }

    fun createExtension(source: Variable<*>?) = NativeFunctionVariable(value, environment, source)
}

