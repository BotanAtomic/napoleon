package io.deepn.script.variables.function

import io.deepn.script.Visitor
import io.deepn.script.error.SyntaxError
import io.deepn.script.error.TypeError
import io.deepn.script.generated.DeepScriptParser
import io.deepn.script.scope.Scope
import io.deepn.script.variables.FunctionArguments
import io.deepn.script.variables.FunctionParameters
import io.deepn.script.variables.Variable
import org.antlr.v4.runtime.ParserRuleContext


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
    private val parentScope: Scope,
    private val parameters: FunctionParameters,
    private val block: ParserRuleContext
) : Variable<Any>(Any()) {

    init {
        checkParameters(parameters)
    }

    override fun type() = "function"

    override fun call(arguments: FunctionArguments): Variable<*> {
        checkArguments(arguments, parameters)

        val toInject = HashMap<String, Variable<*>>()

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
                throw TypeError("${this.functionName}() missing ${missingArguments.size} required positional argument(s):" +
                        " ${missingArguments.keys.joinToString { "'${it}'" }}")
        }

        return Visitor(block, Scope(parentScope, toInject)).visit(block)
    }

    override fun valueToString(): String {
        return "local function '$functionName'"
    }

}
