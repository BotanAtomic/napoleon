package io.deepn.script.variables.function

import io.deepn.script.DeepScriptEnvironment
import io.deepn.script.error.ArgumentTypeError
import io.deepn.script.error.SyntaxError
import io.deepn.script.error.TypeError
import io.deepn.script.error.UnknownError
import io.deepn.script.stdlib.Environment
import io.deepn.script.stdlib.NativeFunction
import io.deepn.script.variables.FunctionArguments
import io.deepn.script.variables.Void
import io.deepn.script.variables.Variable
import io.deepn.script.variables.classToType
import io.deepn.script.variables.error.ErrorVariable
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
    private val environment: DeepScriptEnvironment,
    private val source: Variable<*>? = null,
) : Variable<NativeFunction>(nativeFunction) {

    val hasVarargs = nativeFunction.function.valueParameters.any { it.isVararg }

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

        if (arguments != null) {
            function.valueParameters.filter { it.annotations.isEmpty() }.forEach {
                val variable: Any? = if (!it.isVararg) {
                    arguments.findByName(it.name) ?: arguments.findByIndex(it.index - indexOffset)
                } else {
                    val varargVariables =
                        arguments.takeWhile { (first, _) -> first == null }.map { (_, second) -> second }.toTypedArray()

                    indexOffset -= varargVariables.size
                    varargVariables
                }
                if (variable != null) toInject[it] = variable
            }
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
            if (result is ErrorVariable) throw result.value

            if (result is Variable<*>) return result
        } else {
            executionResult.exceptionOrNull()?.printStackTrace()
            throw UnknownError("failed to execute ${functionName}()")
        }
        return Void
    }

    override fun valueToString(): String {
        return "native function '${value.function.name}'"
    }

    fun createExtension(source: Variable<*>?) = NativeFunctionVariable(value, environment, source)
}

