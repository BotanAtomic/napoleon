package io.deepn.script

import io.deepn.script.error.NameError
import io.deepn.script.error.SyntaxError
import io.deepn.script.error.TypeError
import io.deepn.script.generated.DeepScriptBaseVisitor
import io.deepn.script.generated.DeepScriptParser
import io.deepn.script.scope.Scope
import io.deepn.script.utils.*
import io.deepn.script.variables.FunctionArguments
import io.deepn.script.variables.FunctionParameters
import io.deepn.script.variables.Null
import io.deepn.script.variables.Variable
import io.deepn.script.variables.function.LibraryVariable
import io.deepn.script.variables.function.LocalFunctionVariable
import io.deepn.script.variables.memory.IndexedVariable
import io.deepn.script.variables.memory.MemoryAddressVariable
import io.deepn.script.variables.primitive.*
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.RuleNode
import org.apache.commons.text.StringEscapeUtils
import java.util.concurrent.atomic.AtomicReference


class Visitor(private val scope: Scope) : DeepScriptBaseVisitor<Variable<*>>() {

    val currentContext = AtomicReference<ParserRuleContext?>(null)

    private fun variableCalls(
        baseVariable: Variable<*>,
        argumentsContext: List<DeepScriptParser.ArgsContext>
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

    private fun resolveVariables(
        baseVariable: Variable<*>,
        variableSuffix: List<DeepScriptParser.VarSuffixContext>
    ): List<Variable<*>> {

        val variables = ArrayList<Variable<*>>()
        variables.add(baseVariable)

        var currentVariable = baseVariable

        variableSuffix.forEach { suffix ->
            variables.addAll(variableCalls(currentVariable, suffix.args()))

            currentVariable = (variables.lastOrNull() ?: currentVariable).detach()

            val index = if (suffix.NAME() != null)
                StringVariable(suffix.NAME().text)
            else
                visit(suffix.expression())

            val isFunctionCall = suffix.NAME() != null

            val nextValue = if (isFunctionCall && currentVariable !is LibraryVariable)
                currentVariable.getExtensionFunction(index as StringVariable)
            else
                currentVariable.getIndex(index)

            variables.add(IndexedVariable(currentVariable, nextValue, index))

            currentVariable = nextValue
        }

        return variables
    }

    override fun visitChunk(context: DeepScriptParser.ChunkContext): Variable<*> {
        return visitBlock(context.block())
    }

    override fun visitBlock(context: DeepScriptParser.BlockContext): Variable<*> {
        var returnVariable: Variable<*> = Null

        context.statementOrExpression().forEach {
            returnVariable = if (it.statement() != null) visitStatement(it.statement())
            else visit(it.expression())
        }

        if (context.returnStatement() != null)
            return visitReturnStatement(context.returnStatement())

        return returnVariable
    }

    override fun visitReturnStatement(context: DeepScriptParser.ReturnStatementContext): Variable<*> {
        val returnedValue: ListVariable = visitExpressionList(context.expressionList())

        if (returnedValue.value.size == 1)
            return returnedValue.value[0]

        return returnedValue
    }

    override fun shouldVisitNextChild(node: RuleNode?, result: Variable<*>?): Boolean {
        if (node?.parent is ParserRuleContext)
            currentContext.set(node.parent as ParserRuleContext)

        return true
    }

    override fun visitNumber(context: DeepScriptParser.NumberContext): Variable<*> {
        return when {
            context.INT() != null -> IntegerVariable(context.INT().text)
            context.FLOAT() != null -> FloatVariable(context.FLOAT().text)
            context.HEX() != null -> IntegerVariable(context.HEX().text.substring(2).toLong(16))
            else -> Null
        }
    }

    override fun visitString(context: DeepScriptParser.StringContext): Variable<*> {
        return StringVariable(
            StringEscapeUtils.unescapeJava(
                when {
                    context.NORMALSTRING() != null -> context.text.substringBetween("\"")
                    context.CHARSTRING() != null -> context.text.substringBetween("\'")
                    context.LONGSTRING() != null -> context.text.substringBetween("[[", "]]")
                    else -> context.text
                }
            )
        )
    }

    override fun visitBool(context: DeepScriptParser.BoolContext): Variable<*> {
        return BooleanVariable(context.text.equals("true"))
    }

    override fun visitOperatorPowerExpression(context: DeepScriptParser.OperatorPowerExpressionContext): Variable<*> {
        return visit(context.expression(0)).power(visit(context.expression(1)))
    }

    override fun visitOperatorUnaryExpression(context: DeepScriptParser.OperatorUnaryExpressionContext): Variable<*> {
        val expressionResult = visit(context.expression())
        return when (context.operatorUnary().text) {
            "~" -> expressionResult.bitwiseInv()
            "not" -> expressionResult.not()
            "-" -> expressionResult.multiply(IntegerVariable(-1))
            else -> Null
        }
    }

    override fun visitOperatorAddSubExpression(context: DeepScriptParser.OperatorAddSubExpressionContext): Variable<*> {
        val left = visit(context.expression(0))
        val right = visit(context.expression(1))
        return when (context.operatorAddSub().text) {
            "+" -> left.add(right)
            "-" -> left.subtract(right)
            else -> Null
        }
    }

    override fun visitOperatorAndExpression(context: DeepScriptParser.OperatorAndExpressionContext): Variable<*> {
        return visit(context.expression(0)).and(visit(context.expression(1)))
    }

    override fun visitOperatorOrExpression(context: DeepScriptParser.OperatorOrExpressionContext): Variable<*> {
        return visit(context.expression(0)).or(visit(context.expression(1)))
    }

    override fun visitOperatorMulDivModExpression(context: DeepScriptParser.OperatorMulDivModExpressionContext): Variable<*> {
        val left = visit(context.expression(0))
        val right = visit(context.expression(1))
        return when (context.operatorMulDivMod().text) {
            "*" -> left.multiply(right)
            "/" -> left.divide(right)
            "//" -> left.floorDivide(right)
            "%" -> left.modulo(right)
            else -> Null
        }
    }

    override fun visitOperatorBitwiseExpression(context: DeepScriptParser.OperatorBitwiseExpressionContext): Variable<*> {
        val left = visit(context.expression(0))
        val right = visit(context.expression(1))
        return when (context.operatorBitwise().text) {
            "&" -> left.bitwiseAnd(right)
            "|" -> left.bitwiseOr(right)
            "^" -> left.bitwiseXor(right)
            "<<" -> left.bitwiseLeftShift(right)
            ">>" -> left.bitwiseRightShift(right)
            else -> Null
        }
    }

    override fun visitOperatorComparisonExpression(context: DeepScriptParser.OperatorComparisonExpressionContext): Variable<*> {
        val left = visit(context.expression(0))
        val right = visit(context.expression(1))

        return when (context.operatorComparison().text) {
            "<" -> left.lt(right)
            "<=" -> left.lte(right)
            ">" -> left.gt(right)
            ">=" -> left.gte(right)
            "==" -> left.eq(right)
            "!=" -> left.eq(right).not()
            else -> Null
        }
    }

    override fun visitTableconstructor(context: DeepScriptParser.TableconstructorContext): Variable<*> {
        val listVariable = ListVariable()
        context.fieldlist()?.expression()?.forEach { listVariable.insert(visit(it)) }
        return listVariable
    }

    override fun visitNullExpression(context: DeepScriptParser.NullExpressionContext): Variable<*> {
        return Null
    }

    override fun visitVariableAssignment(context: DeepScriptParser.VariableAssignmentContext): Variable<*> {
        val baseVariable = if (context.var_().NAME() != null)
            scope.resolve(context.var_().NAME().text, true)
        else
            visit(context.var_().expression())

        val value = visit(context.expression())

        val parents = resolveVariables(baseVariable, context.var_().varSuffix())

        when (val lastValue = parents.lastOrNull() ?: baseVariable) {
            is MemoryAddressVariable -> lastValue.assign(value)
            is IndexedVariable -> lastValue.replace(value)
            else -> scope.assign(context.var_().NAME().text, value)
        }

        return Null
    }

    override fun visitPrefixexp(context: DeepScriptParser.PrefixexpContext): Variable<*> {
        val baseVariable = visitVarOrExp(context.varOrExp())
        return if (context.args().isEmpty())
            baseVariable
        else
            variableCalls(baseVariable, context.args()).lastOrNull() ?: baseVariable
    }

    override fun visitVarOrExp(context: DeepScriptParser.VarOrExpContext): Variable<*> {
        return if (context.var_() != null) visitVar_(context.var_()) else visit(context.expression())
    }

    override fun visitVar_(context: DeepScriptParser.Var_Context): Variable<*> {
        val baseVariable = if (context.NAME() != null)
            scope.resolve(context.NAME().text)
        else
            visit(context.expression())

        if (context.NAME() != null && baseVariable == Null)
            throw NameError("name '${context.NAME().text}' is not defined")

        return if (context.varSuffix().isEmpty())
            baseVariable
        else
            resolveVariables(baseVariable, context.varSuffix()).last().detach()
    }

    override fun visitExpressionList(context: DeepScriptParser.ExpressionListContext?): ListVariable {
        return ListVariable().apply { context?.expression()?.forEach { this.insert(visit(it)) } }
    }

    override fun visitWhileLoop(context: DeepScriptParser.WhileLoopContext): Variable<*> {
        while (visit(context.expression()).toBoolean().value)
            visitBlock(context.block())
        return Null
    }

    override fun visitRepeatLoop(context: DeepScriptParser.RepeatLoopContext): Variable<*> {
        while (true) {
            if (!visit(context.expression()).toBoolean().value)
                visitBlock(context.block())
            else break
        }
        return Null
    }

    override fun visitForLoop(context: DeepScriptParser.ForLoopContext): Variable<*> {
        val key = context.NAME().text


        val initialValue = visit(context.expression(0))
        val limitValue = visit(context.expression(1))
        val stepValue = if (context.expression().size > 1) visit(context.expression(2)) else IntegerVariable(1)

        if (!initialValue.isNumber())
            throw TypeError("'for' initial value must be a number")
        if (!limitValue.isNumber())
            throw TypeError("'for' limit must be a number")
        if (!stepValue.isNumber())
            throw TypeError("'for' step must be a number")

        if (stepValue.isZero() || limitValue.eq(initialValue).value) return Null
        if (limitValue.gt(initialValue).value && stepValue.isNegative()) return Null
        if (limitValue.lt(initialValue).value && stepValue.isPositive()) return Null

        createForLoop(initialValue, limitValue, stepValue) {
            scope.assign(key, it)
            visitBlock(context.block())
        }

        scope.remove(key)
        return Null
    }

    override fun visitForeachLoop(context: DeepScriptParser.ForeachLoopContext): Variable<*> {
        val iterator = visit(context.expression()).toIterator()
        val key = context.NAME().text

        for (value in iterator) {
            scope.assign(key, value)
            visitBlock(context.block())
        }
        scope.remove(key)
        return Null
    }

    override fun visitCondition(context: DeepScriptParser.ConditionContext): Variable<*> {
        var conditionExecuted = false

        fun executeCondition(expression: ParserRuleContext, block: DeepScriptParser.BlockContext) {
            if (!conditionExecuted && visit(expression).toBoolean().value) {
                visitBlock(block)
                conditionExecuted = true
            }
        }

        executeCondition(context.expression(), context.block())
        context.elseifCondition()?.forEach { executeCondition(it.expression(), it.block()) }

        if (!conditionExecuted && context.elseCondition() != null)
            visitBlock(context.elseCondition().block())

        return Null
    }

    override fun visitFunctionDeclaration(context: DeepScriptParser.FunctionDeclarationContext): Variable<*> {
        val parameters = FunctionParameters()

        context.funcbody().nameList()?.functionNameField()?.forEach { nameField ->
            val key = nameField.NAME().text
            if (parameters.contains(key))
                throw SyntaxError("duplicate argument '${key}' in function definition")
            parameters[nameField.NAME().text] = (nameField.expression() != null).toProducer {
                visit(nameField.expression())
            }
        }
        scope.assign(
            context.NAME().text, LocalFunctionVariable(
                context.NAME().text,
                scope,
                parameters,
                context.funcbody().block()
            )
        )
        return Null
    }

    override fun visitFunctionCall(context: DeepScriptParser.FunctionCallContext): Variable<*> {
        val baseVariable = visitVarOrExp(context.varOrExp())
        return variableCalls(baseVariable, context.args()).lastOrNull() ?: baseVariable
    }

}
