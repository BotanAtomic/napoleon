package io.deepn.script.utils

import io.deepn.script.Status
import io.deepn.script.Visitor
import io.deepn.script.error.NameError
import io.deepn.script.generated.DeepScriptParser
import io.deepn.script.variables.FunctionArguments
import io.deepn.script.variables.Null
import io.deepn.script.variables.Variable
import io.deepn.script.variables.Void
import io.deepn.script.variables.function.LibraryVariable
import io.deepn.script.variables.memory.IndexedVariable
import io.deepn.script.variables.primitive.StringVariable


fun Visitor.resolveBaseVariable(
    name: String?,
    expression: DeepScriptParser.ExpressionContext?,
    returnMemoryAddress: Boolean
): Variable<*> {
    val baseVariable = if (name != null) scope.resolve(name, returnMemoryAddress)
    else visit(expression)

    if (!returnMemoryAddress && name != null && baseVariable is Void) throw NameError("name '${name}' is not defined")

    return baseVariable
}

fun Visitor.variableCalls(
    baseVariable: Variable<*>, argumentsContext: List<DeepScriptParser.ArgsContext>
): List<Variable<*>> {
    val variables = ArrayList<Variable<*>>()

    var currentVariable = baseVariable

    argumentsContext.forEach { context ->

        val arguments = FunctionArguments()

        context.namedExpressionList()?.namedExpression()?.forEach {
            arguments.add(it.NAME()?.text to visit(it.expression()))
        }
        currentVariable = currentVariable.call(arguments)
        variables.add(currentVariable)
    }
    return variables
}


fun Visitor.resolveVariables(
    baseVariable: Variable<*>,
    variableSuffix: List<DeepScriptParser.VarSuffixContext>
): List<Variable<*>> {

    val variables = ArrayList<Variable<*>>()
    variables.add(baseVariable)

    var currentVariable = baseVariable

    variableSuffix.forEach { suffix ->
        variables.addAll(variableCalls(currentVariable, suffix.args()))

        currentVariable = (variables.lastOrNull() ?: currentVariable).detach()

        val index = if (suffix.NAME() != null) StringVariable(suffix.NAME().text)
        else visit(suffix.expression())

        val isFunctionCall = suffix.NAME() != null

        val nextValue =
            if (isFunctionCall && currentVariable !is LibraryVariable) currentVariable.getExtensionFunction(index as StringVariable)
            else currentVariable.getIndex(index)

        variables.add(IndexedVariable(currentVariable, nextValue, index))

        currentVariable = nextValue
    }

    return variables
}

fun Visitor.enterLoop(loop: () -> Unit) {
    loop()

    if (status != Status.RETURN)
        status = Status.NORMAL
}

fun Visitor.parseCallableExpression(
    baseVariable: Variable<*>,
    variableSuffix: List<DeepScriptParser.VarSuffixContext>?,
    arguments: List<DeepScriptParser.ArgsContext>?,
): Variable<*> {
    if (variableSuffix == null || variableSuffix.isEmpty())
        return baseVariable

    val variable = resolveVariables(baseVariable, variableSuffix).lastOrNull()?.detach() ?: Null

    if (arguments == null || arguments.isEmpty())
        return variable

    return variableCalls(variable, arguments).lastOrNull() ?: Null
}
