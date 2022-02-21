package io.deepn.script

import io.deepn.script.error.NameError
import io.deepn.script.error.StackTrace
import io.deepn.script.error.SyntaxError
import io.deepn.script.error.TypeError
import io.deepn.script.generated.DeepScriptBaseVisitor
import io.deepn.script.generated.DeepScriptParser
import io.deepn.script.scope.Scope
import io.deepn.script.utils.*
import io.deepn.script.variables.FunctionParameters
import io.deepn.script.variables.Null
import io.deepn.script.variables.Variable
import io.deepn.script.variables.Void
import io.deepn.script.variables.function.LocalFunctionVariable
import io.deepn.script.variables.internal.PairVariable
import io.deepn.script.variables.memory.IndexedVariable
import io.deepn.script.variables.memory.MemoryAddressVariable
import io.deepn.script.variables.primitive.*
import io.deepn.script.variables.primitive.api.NumberVariable
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.RuleNode
import org.apache.commons.text.StringEscapeUtils

enum class Status {
    NORMAL, BREAK, RETURN
}

class Visitor(
    initialContext: ParserRuleContext,
    val scope: Scope,
    private val stackTrace: StackTrace,
) : DeepScriptBaseVisitor<Variable<*>>() {

    var status = Status.NORMAL

    var currentContext: ParserRuleContext = initialContext

    var toReturn: Variable<*> = Void

    override fun shouldVisitNextChild(node: RuleNode?, result: Variable<*>?): Boolean {
        if (node?.parent is ParserRuleContext) currentContext = node.parent as ParserRuleContext
        return status == Status.NORMAL
    }

    override fun visitChunk(context: DeepScriptParser.ChunkContext) = visitBlock(context.block())

    override fun visitBlock(context: DeepScriptParser.BlockContext): Variable<*> {
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


    override fun visitReturnStatement(context: DeepScriptParser.ReturnStatementContext): Void {
        val returnedValue = visitExpressionList(context.expressionList())
        status = Status.RETURN
        toReturn = if (returnedValue.value.size == 1) returnedValue.value[0] else returnedValue
        return Void
    }


    override fun visitVariableAssignment(context: DeepScriptParser.VariableAssignmentContext): Void {
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

    override fun visitVariableOperatorAssignment(context: DeepScriptParser.VariableOperatorAssignmentContext): Void {
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

    override fun visitDeleteVar(context: DeepScriptParser.DeleteVarContext): Void {
        val baseVariable = resolveBaseVariable(context.NAME().text, null, false)

        val variables = resolveVariables(baseVariable, context.varSuffix())
        if (variables.size >= 2) {
            val parent = variables[variables.size - 2].detach()
            val index = variables[variables.size - 1]

            if (index is IndexedVariable) parent.deleteIndex(index.index)
        }

        return Void
    }

    override fun visitBreakStatement(context: DeepScriptParser.BreakStatementContext): Void {
        status = Status.BREAK
        return Void
    }

    override fun visitWhileLoop(context: DeepScriptParser.WhileLoopContext): Void {
        enterLoop {
            while (visit(context.expression()).toBoolean().value) {
                visitBlock(context.block())

                if (status == Status.BREAK)
                    break
            }
        }
        return Void
    }

    override fun visitForLoop(context: DeepScriptParser.ForLoopContext): Void {
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

    override fun visitForeachLoop(context: DeepScriptParser.ForeachLoopContext): Void {
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

    override fun visitCondition(context: DeepScriptParser.ConditionContext): Void {
        var conditionExecuted = false

        fun executeCondition(expression: ParserRuleContext, block: DeepScriptParser.BlockContext) {
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

    override fun visitFunctionDeclaration(context: DeepScriptParser.FunctionDeclarationContext): Void {
        val parameters = FunctionParameters()

        context.funcbody().nameList()?.functionNameField()?.forEach { nameField ->
            val key = nameField.NAME().text
            if (parameters.contains(key)) throw SyntaxError("duplicate argument '${key}' in function definition")
            parameters[nameField.NAME().text] = (nameField.expression() != null).toProducer {
                visit(nameField.expression())
            }
        }
        scope.assign(
            context.NAME().text, LocalFunctionVariable(
                context.NAME().text,
                scope,
                parameters,
                context.funcbody().block(),
                stackTrace
            )
        )
        return Void
    }

    override fun visitNumber(context: DeepScriptParser.NumberContext) = when {
        context.INT() != null -> IntegerVariable(context.INT().text)
        context.FLOAT() != null -> FloatVariable(context.FLOAT().text)
        context.HEX() != null -> IntegerVariable(context.HEX().text.substring(2).toLong(16))
        else -> Null
    }

    override fun visitString(context: DeepScriptParser.StringContext) = StringVariable(
        StringEscapeUtils.unescapeJava(
            when {
                context.NORMALSTRING() != null -> context.text.substringBetween("\"")
                context.CHARSTRING() != null -> context.text.substringBetween("\'")
                context.LONGSTRING() != null -> context.text.substringBetween("\"\"\"")
                else -> context.text
            }
        )
    )

    override fun visitBool(context: DeepScriptParser.BoolContext) = BooleanVariable(context.text.equals("true"))

    override fun visitOperatorPowerExpression(context: DeepScriptParser.OperatorPowerExpressionContext) =
        visit(context.expression(0))
            .power(visit(context.expression(1)))

    override fun visitOperatorUnaryExpression(context: DeepScriptParser.OperatorUnaryExpressionContext) =
        visit(context.expression()).let {
            when (context.operatorUnary().text) {
                "~" -> it.bitwiseInv()
                "not" -> it.not()
                "-" -> it.multiply(IntegerVariable(-1))
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

    override fun visitOperatorAndExpression(context: DeepScriptParser.OperatorAndExpressionContext) =
        visit(context.expression(0)).and(visit(context.expression(1)))

    override fun visitOperatorOrExpression(context: DeepScriptParser.OperatorOrExpressionContext) =
        visit(context.expression(0)).or(visit(context.expression(1)))

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

    override fun visitTableconstructor(context: DeepScriptParser.TableconstructorContext) = ListVariable().apply {
        context.fieldlist()?.expression()?.forEach { insert(visit(it)) }
    }

    override fun visitNullExpression(context: DeepScriptParser.NullExpressionContext) = Null

    override fun visitPrefixexp(context: DeepScriptParser.PrefixexpContext): Variable<*> {
        val baseVariable = visitVarOrExp(context.varOrExp())
        return if (context.args().isEmpty()) baseVariable
        else variableCalls(baseVariable, context.args()).lastOrNull() ?: baseVariable
    }

    override fun visitVarOrExp(context: DeepScriptParser.VarOrExpContext): Variable<*> =
        if (context.var_() != null) visitVar_(context.var_()) else visit(context.expression())

    override fun visitVar_(context: DeepScriptParser.Var_Context): Variable<*> {
        val baseVariable = resolveBaseVariable(context.NAME()?.text, context.expression(), false)

        return if (context.varSuffix().isEmpty()) baseVariable
        else resolveVariables(baseVariable, context.varSuffix()).last().detach()
    }

    override fun visitExpressionList(context: DeepScriptParser.ExpressionListContext?) =
        ListVariable().apply { context?.expression()?.forEach { this.insert(visit(it)) } }


    override fun visitFunctionCall(context: DeepScriptParser.FunctionCallContext): Variable<*> {
        val baseVariable = visitVarOrExp(context.varOrExp())
        return variableCalls(baseVariable, context.args()).lastOrNull() ?: baseVariable
    }

    override fun visitJsonObject(context: DeepScriptParser.JsonObjectContext): ObjectVariable {
        val json = ObjectVariable()
        context.jsonPair().forEach {
            val pairValue = visitJsonPair(it)
            json.setIndex(pairValue.key(), pairValue.value())
        }
        return json
    }

    override fun visitJsonPair(context: DeepScriptParser.JsonPairContext): PairVariable<StringVariable, Variable<*>> {
        val name: StringVariable = when {
            context.string() != null -> visitString(context.string())
            context.NAME() != null -> StringVariable(context.NAME().text)
            else -> StringVariable(visit(context.expression().first()).valueToString())
        }

        val value = visit(context.expression().last())

        return PairVariable(name to value)
    }


    override fun visitLambdaExpression(context: DeepScriptParser.LambdaExpressionContext): Variable<*> {
        val parameters = FunctionParameters()

        context.NAME()?.forEach { nameField ->
            val key = nameField.text
            if (parameters.contains(key)) throw SyntaxError("duplicate argument '${key}' in function definition")
            parameters[key] = null
        }
        return LocalFunctionVariable(
            "lambda",
            scope,
            parameters,
            context.expression(),
            stackTrace
        )
    }

    override fun visitExpressionCall(context: DeepScriptParser.ExpressionCallContext): Variable<*> {
        val baseExpression = visit(context.expression())
        val function = baseExpression.getExtensionFunction(StringVariable(context.NAME().text))
        return variableCalls(function, listOf(context.args())).lastOrNull() ?: Null
    }
}
