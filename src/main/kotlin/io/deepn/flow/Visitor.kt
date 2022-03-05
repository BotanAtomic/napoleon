package io.deepn.flow

import io.deepn.flow.error.NameError
import io.deepn.flow.error.StackTrace
import io.deepn.flow.error.SyntaxError
import io.deepn.flow.error.TypeError
import io.deepn.flow.generated.FlowBaseVisitor
import io.deepn.flow.generated.FlowParser
import io.deepn.flow.scope.Scope
import io.deepn.flow.strategy.StrategyHandler
import io.deepn.flow.utils.*
import io.deepn.flow.variables.FunctionParameters
import io.deepn.flow.variables.Null
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.Void
import io.deepn.flow.variables.function.LocalFunctionVariable
import io.deepn.flow.variables.internal.PairVariable
import io.deepn.flow.variables.memory.IndexedVariable
import io.deepn.flow.variables.memory.MemoryAddressVariable
import io.deepn.flow.variables.primitive.*
import io.deepn.flow.variables.primitive.api.NumberVariable
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.RuleNode
import org.apache.commons.text.StringEscapeUtils

enum class Status {
    NORMAL, BREAK, RETURN
}

class Visitor(
    initialContext: ParserRuleContext,
    val scope: Scope,
    val stackTrace: StackTrace,
    val strategyHandler: StrategyHandler,
) : FlowBaseVisitor<Variable<*>>() {

    var status = Status.NORMAL

    var currentContext: ParserRuleContext = initialContext

    var toReturn: Variable<*> = Void

    override fun shouldVisitNextChild(node: RuleNode?, result: Variable<*>?): Boolean {
        if (node?.parent is ParserRuleContext) currentContext = node.parent as ParserRuleContext
        return status == Status.NORMAL
    }

    override fun visitChunk(context: FlowParser.ChunkContext) = visitBlock(context.block())

    override fun visitBlock(context: FlowParser.BlockContext): Variable<*> {
        var returnVariable: Variable<*> = Void

        context.statementGroup()?.statementOrExpression()?.forEach {
            if (status == Status.NORMAL) {
                returnVariable = if (it.statement() != null)
                    visitStatement(it.statement())
                else
                    visit(it.expression())
            }
        }

        if (context.returnStatement() != null && status == Status.NORMAL)
            visitReturnStatement(context.returnStatement())

        if (toReturn != Void)
            return toReturn

        return returnVariable
    }


    override fun visitReturnStatement(context: FlowParser.ReturnStatementContext): Void {
        val returnedValue = visitExpressionList(context.expressionList())
        status = Status.RETURN
        toReturn = if (returnedValue.value.size == 1) returnedValue.value[0] else returnedValue
        return Void
    }


    override fun visitVariableAssignment(context: FlowParser.VariableAssignmentContext): Void {
        val baseVariable = resolveBaseVariable(context.var_().NAME()?.text, context.var_().expression(), true)

        val value = visit(context.expression())

        val parents = resolveVariables(baseVariable, context.var_().varSuffix())

        val isStatic = context.staticVariable() != null

        if (isStatic && (context.var_().NAME() == null || context.var_().varSuffix().isNotEmpty())) {
            throw NameError("static variable can only be assigned to global scope")
        }

        when (val lastValue = parents.lastOrNull() ?: baseVariable) {
            is MemoryAddressVariable -> lastValue.assign(value, isStatic)
            is IndexedVariable -> lastValue.replace(value)
            else -> scope.assign(context.var_().NAME().text, value, isStatic)
        }

        return Void
    }

    override fun visitVariableOperatorAssignment(context: FlowParser.VariableOperatorAssignmentContext): Void {
        val baseVariable = resolveBaseVariable(context.var_().NAME()?.text, context.var_().expression(), false)

        val value = visit(context.expression())

        val parents = resolveVariables(baseVariable, context.var_().varSuffix())

        val lastValue = parents.lastOrNull() ?: baseVariable

        val newValue = when (context.operatorAssignment().text) {
            "+=" -> lastValue.detach().add(value)
            "-=" -> lastValue.detach().subtract(value)
            "*=" -> lastValue.detach().multiply(value)
            "/=" -> lastValue.detach().divide(value)
            "//=" -> lastValue.detach().floorDivide(value)
            else -> Null
        }

        if (lastValue is IndexedVariable) lastValue.replace(newValue)
        else scope.assign(context.var_().NAME().text, newValue)

        return Void
    }

    override fun visitDeleteVar(context: FlowParser.DeleteVarContext): Void {
        val baseVariable = resolveBaseVariable(context.NAME().text, null, false)

        val variables = resolveVariables(baseVariable, context.varSuffix())
        if (variables.size >= 2) {
            val parent = variables[variables.size - 2].detach()
            val index = variables[variables.size - 1]

            if (index is IndexedVariable) parent.deleteIndex(index.index)
        }

        return Void
    }

    override fun visitBreakStatement(context: FlowParser.BreakStatementContext): Void {
        status = Status.BREAK
        return Void
    }

    override fun visitWhileLoop(context: FlowParser.WhileLoopContext): Void {
        enterLoop {
            while (visit(context.expression()).toBoolean().value) {
                visitBlock(context.block())

                if (status == Status.BREAK)
                    break
            }
        }
        return Void
    }

    override fun visitForLoop(context: FlowParser.ForLoopContext): Void {
        val key = context.NAME().text

        val initialValue = visit(context.expression(0))
        val limitValue = visit(context.expression(1))
        val stepValue = if (context.expression().size > 2) visit(context.expression(2)) else IntegerVariable(1)

        if (initialValue !is NumberVariable) throw TypeError("'for' initial value must be a number")
        if (limitValue !is NumberVariable) throw TypeError("'for' limit must be a number")
        if (stepValue !is NumberVariable) throw TypeError("'for' step must be a number")

        if (stepValue.isZero() || limitValue.eq(initialValue).value) return Void
        if (limitValue.gt(initialValue).value && stepValue.isNegative()) return Void
        if (limitValue.lt(initialValue).value && stepValue.isPositive()) return Void

        enterLoop {
            createForLoop(initialValue, limitValue, stepValue) {
                scope.assign(key, it)
                visitBlock(context.block())
                status == Status.NORMAL
            }
        }

        scope.remove(key)
        return Void
    }

    override fun visitForeachLoop(context: FlowParser.ForeachLoopContext): Void {
        val iterator = visit(context.expression()).toIterator()
        val key = context.NAME().text

        if (scope.resolve(key, false) != Void) throw NameError("'$key' is already defined")

        enterLoop {
            for (value in iterator) {
                scope.assign(key, value)
                visitBlock(context.block())
                if (status == Status.BREAK)
                    break
            }
        }

        scope.remove(key)
        return Void
    }

    override fun visitCondition(context: FlowParser.ConditionContext): Void {
        var conditionExecuted = false

        fun executeCondition(expression: ParserRuleContext, block: FlowParser.BlockContext) {
            if (!conditionExecuted && visit(expression).toBoolean().value) {
                visitBlock(block)
                conditionExecuted = true
            }
        }

        executeCondition(context.expression(), context.block())
        context.elseifCondition()?.forEach { executeCondition(it.expression(), it.block()) }

        if (!conditionExecuted && context.elseCondition() != null) visitBlock(context.elseCondition().block())

        return Void
    }

    override fun visitFunctionDeclaration(context: FlowParser.FunctionDeclarationContext): Void {
        val parameters = FunctionParameters()

        context.funcbody().nameList()?.functionNameField()?.forEach { nameField ->
            val key = nameField.NAME().text
            if (parameters.contains(key)) throw SyntaxError("duplicate argument '${key}' in function definition")
            parameters[nameField.NAME().text] = (nameField.expression() != null).toProducer {
                visit(nameField.expression())
            }
        }
        scope.assign(
            context.NAME().text, LocalFunctionVariable(context.NAME().text, parameters) {
                executeContext(it, context.funcbody().block())
            }
        )
        return Void
    }

    override fun visitNumber(context: FlowParser.NumberContext) = when {
        context.INT() != null -> IntegerVariable(context.INT().text)
        context.FLOAT() != null -> FloatVariable(context.FLOAT().text)
        context.HEX() != null -> IntegerVariable(context.HEX().text.substring(2).toLong(16))
        else -> Null
    }

    override fun visitString(context: FlowParser.StringContext) = StringVariable(
        StringEscapeUtils.unescapeJava(
            when {
                context.NORMALSTRING() != null -> context.text.substringBetween("\"")
                context.CHARSTRING() != null -> context.text.substringBetween("\'")
                context.LONGSTRING() != null -> context.text.substringBetween("\"\"\"")
                else -> context.text
            }
        )
    )

    override fun visitStringExpression(context: FlowParser.StringExpressionContext): Variable<*> {
        return parseCallableExpression(visitString(context.string()), context.varSuffix(), context.args())
    }

    override fun visitBool(context: FlowParser.BoolContext) = BooleanVariable(context.text.equals("true"))

    override fun visitOperatorPowerExpression(context: FlowParser.OperatorPowerExpressionContext) =
        visit(context.expression(0))
            .power(visit(context.expression(1)))

    override fun visitOperatorUnaryExpression(context: FlowParser.OperatorUnaryExpressionContext) =
        visit(context.expression()).let {
            when (context.operatorUnary().text) {
                "~" -> it.bitwiseInv()
                "not" -> it.not()
                "-" -> it.multiply(IntegerVariable(-1))
                else -> Null
            }
        }

    override fun visitOperatorAddSubExpression(context: FlowParser.OperatorAddSubExpressionContext): Variable<*> {
        val left = visit(context.expression(0))
        val right = visit(context.expression(1))
        return when (context.operatorAddSub().text) {
            "+" -> left.add(right)
            "-" -> left.subtract(right)
            else -> Null
        }
    }

    override fun visitOperatorAndExpression(context: FlowParser.OperatorAndExpressionContext) =
        visit(context.expression(0)).and(visit(context.expression(1)))

    override fun visitOperatorOrExpression(context: FlowParser.OperatorOrExpressionContext) =
        visit(context.expression(0)).or(visit(context.expression(1)))

    override fun visitOperatorMulDivModExpression(context: FlowParser.OperatorMulDivModExpressionContext): Variable<*> {
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

    override fun visitOperatorBitwiseExpression(context: FlowParser.OperatorBitwiseExpressionContext): Variable<*> {
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

    override fun visitOperatorComparisonExpression(context: FlowParser.OperatorComparisonExpressionContext): Variable<*> {
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

    override fun visitTableconstructor(context: FlowParser.TableconstructorContext) = ListVariable().apply {
        context.fieldlist()?.expression()?.forEach { insert(visit(it)) }
    }

    override fun visitTableconstructorExpression(context: FlowParser.TableconstructorExpressionContext): Variable<*> {
        return parseCallableExpression(
            visitTableconstructor(context.tableconstructor()),
            context.varSuffix(),
            context.args()
        )
    }

    override fun visitNullExpression(context: FlowParser.NullExpressionContext) = Null

    override fun visitPrefixexp(context: FlowParser.PrefixexpContext): Variable<*> {
        val baseVariable = visitVarOrExp(context.varOrExp())
        return if (context.args().isEmpty()) baseVariable
        else variableCalls(baseVariable, context.args()).lastOrNull() ?: baseVariable
    }

    override fun visitVarOrExp(context: FlowParser.VarOrExpContext): Variable<*> =
        if (context.var_() != null) visitVar_(context.var_()) else visit(context.expression())

    override fun visitVar_(context: FlowParser.Var_Context): Variable<*> {
        val baseVariable = resolveBaseVariable(context.NAME()?.text, context.expression(), false)

        return if (context.varSuffix().isEmpty()) baseVariable
        else resolveVariables(baseVariable, context.varSuffix()).last().detach()
    }

    override fun visitExpressionList(context: FlowParser.ExpressionListContext?) =
        ListVariable().apply { context?.expression()?.forEach { this.insert(visit(it)) } }


    override fun visitFunctionCall(context: FlowParser.FunctionCallContext): Variable<*> {
        val baseVariable = visitVarOrExp(context.varOrExp())
        return variableCalls(baseVariable, context.args()).lastOrNull() ?: baseVariable
    }

    override fun visitJsonObject(context: FlowParser.JsonObjectContext): ObjectVariable {
        val json = ObjectVariable()
        context.jsonPair().forEach {
            val pairValue = visitJsonPair(it)
            json.setIndex(pairValue.key(), pairValue.value())
        }
        return json
    }

    override fun visitJsonExpression(context: FlowParser.JsonExpressionContext): Variable<*> {
        return parseCallableExpression(visitJsonObject(context.jsonObject()), context.varSuffix(), context.args())
    }

    override fun visitJsonPair(context: FlowParser.JsonPairContext): PairVariable<StringVariable, Variable<*>> {
        val name: StringVariable = when {
            context.string() != null -> visitString(context.string())
            context.NAME() != null -> StringVariable(context.NAME().text)
            else -> StringVariable(visit(context.expression().first()).valueToString())
        }

        val value = visit(context.expression().last())

        return PairVariable(name to value)
    }


    override fun visitLambdaExpression(context: FlowParser.LambdaExpressionContext): Variable<*> {
        val parameters = FunctionParameters()

        context.NAME()?.forEach { nameField ->
            val key = nameField.text
            if (parameters.contains(key)) throw SyntaxError("duplicate argument '${key}' in function definition")
            parameters[key] = null
        }
        return LocalFunctionVariable("lambda", parameters) {
            executeContext(it, context.expression())
        }
    }

    override fun visitStrategyFunctionExpression(context: FlowParser.StrategyFunctionExpressionContext): Variable<*> {
        return strategyHandler.call(context.NAME().text, context.mendatoryNamedExpressionList()
            ?.mendatoryNamedExpression()?.associate { it.NAME().text to visit(it.expression()) } ?: emptyMap())
    }


}
