package esgi.napoleon.utils

import esgi.napoleon.Status
import esgi.napoleon.Visitor
import esgi.napoleon.error.NameError
import esgi.napoleon.generated.NapoleonParser
import esgi.napoleon.scope.VariableMap
import esgi.napoleon.variables.FunctionArguments
import esgi.napoleon.variables.Null
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.Void
import esgi.napoleon.variables.function.LibraryVariable
import esgi.napoleon.variables.memory.IndexedVariable
import esgi.napoleon.variables.primitive.StringVariable
import org.antlr.v4.runtime.ParserRuleContext


fun Visitor.resolveBaseVariable(
    name: String?,
    expression: NapoleonParser.ExpressionContext?,
    returnMemoryAddress: Boolean
): Variable<*> {
    val baseVariable = if (name != null) scope.resolve(name, returnMemoryAddress)
    else visit(expression)

    if (!returnMemoryAddress && name != null && baseVariable is Void) throw NameError("name '${name}' is not defined")

    return baseVariable
}

fun Visitor.variableCalls(
    baseVariable: Variable<*>,
    argumentsContext: List<NapoleonParser.ArgsContext>
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
    variableSuffix: List<NapoleonParser.VarSuffixContext>
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
    variableSuffix: List<NapoleonParser.VarSuffixContext>?,
    arguments: List<NapoleonParser.ArgsContext>?,
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
