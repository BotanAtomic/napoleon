package io.deepn.flow.variables.function

import io.deepn.flow.error.SyntaxError
import io.deepn.flow.error.TypeError
import io.deepn.flow.scope.VariableMap
import io.deepn.flow.variables.FunctionArguments
import io.deepn.flow.variables.FunctionParameters
import io.deepn.flow.variables.Variable

private fun checkParameters(parameters: FunctionParameters) {
    if (parameters.isEmpty()) return
    var hasDefaultValue = false
    parameters.forEach { (_, value) ->
        if (value == null && hasDefaultValue)
            throw SyntaxError("non-default argument follows default argument")
        hasDefaultValue = value != null
    }
}

private fun LocalFunctionVariable.checkArguments(arguments: FunctionArguments, parameters: FunctionParameters) {
    if (arguments == null) return
    var hasKeywordArgument = false
    arguments.forEach { (key, _) ->
        if (key == null && hasKeywordArgument)
            throw SyntaxError("positional argument follows keyword argument")
        hasKeywordArgument = key != null
    }

    if (parameters.size < arguments.size)
        throw TypeError("${this.functionName}() take ${parameters.size} argument(s) but ${arguments.size} were given")

    arguments.mapIndexed { index, (key, _) ->
        val finalKey = key ?: parameters.keys.elementAt(index)
        if (!parameters.containsKey(finalKey))
            throw TypeError("${this.functionName}() got an unexpected keyword argument '$finalKey'")
        finalKey
    }
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }
        .firstNotNullOfOrNull {
            throw TypeError("${this.functionName}() got multiple values for argument '${it.key}'")
        }


    if (arguments.filter { it.first != null }.groupingBy { it.first }.eachCount().any { it.value > 1 })
        throw SyntaxError("keyword argument repeated")
}

class LocalFunctionVariable(
    val functionName: String,
    private val parameters: FunctionParameters,
    val onExecute: (VariableMap) -> Variable<*>
) : Variable<Any>(Any()) {

    init {
        checkParameters(parameters)
    }

    override fun type() = "function"

    override fun call(arguments: FunctionArguments): Variable<*> {
        checkArguments(arguments, parameters)

        val toInject = VariableMap()

        arguments?.forEachIndexed { index, (key, value) ->
            val finalKey = key ?: parameters.keys.elementAt(index)
            toInject[finalKey] = value
        }

        parameters.forEach { (key, valueProvider) ->
            if (!toInject.containsKey(key) && valueProvider != null)
                toInject[key] = valueProvider()
        }

        parameters.filter { (key, _) -> !toInject.containsKey(key) }.let { missingArguments ->
            if (missingArguments.isNotEmpty())
                throw TypeError(
                    "${this.functionName}() missing ${missingArguments.size} required positional argument(s):" +
                            " ${missingArguments.keys.joinToString { "'${it}'" }}"
                )
        }

        return onExecute(toInject)
    }

    override fun valueToString(): String {
        return "local function '$functionName'"
    }

}
