package io.deepn.flow.utils

import io.deepn.flow.Status
import io.deepn.flow.Visitor
import io.deepn.flow.error.NameError
import io.deepn.flow.generated.FlowParser
import io.deepn.flow.scope.VariableMap
import io.deepn.flow.variables.FunctionArguments
import io.deepn.flow.variables.Null
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.Void
import io.deepn.flow.variables.function.LibraryVariable
import io.deepn.flow.variables.memory.IndexedVariable
import io.deepn.flow.variables.primitive.StringVariable
import org.antlr.v4.runtime.ParserRuleContext


fun Visitor.resolveBaseVariable(
    name: String?,
    expression: FlowParser.ExpressionContext?,
    returnMemoryAddress: Boolean
): Variable<*> {
    val baseVariable = if (name != null) scope.resolve(name, returnMemoryAddress)
    else visit(expression)

    if (!returnMemoryAddress && name != null && baseVariable is Void) throw NameError("name '${name}' is not defined")

    return baseVariable
}

fun Visitor.variableCalls(
    baseVariable: Variable<*>,
    argumentsContext: List<FlowParser.ArgsContext>
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
    variableSuffix: List<FlowParser.VarSuffixContext>
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
    variableSuffix: List<FlowParser.VarSuffixContext>?,
    arguments: List<FlowParser.ArgsContext>?,
): Variable<*> {
    if (variableSuffix == null || variableSuffix.isEmpty())
        return baseVariable

    val variable = resolveVariables(baseVariable, variableSuffix).lastOrNull()?.detach() ?: Null

    if (arguments == null || arguments.isEmpty())
        return variable

    return variableCalls(variable, arguments).lastOrNull() ?: Null
}

fun Visitor.executeContext(variables:  VariableMap, context: ParserRuleContext): Variable<*> {
    val newVisitor = Visitor(context, scope.createChildren(variables), stackTrace, strategyHandler)
    stackTrace.stack(newVisitor)
    val result = newVisitor.visit(context)
    stackTrace.pop()
    return result
}
