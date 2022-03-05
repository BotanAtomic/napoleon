// Generated from Flow.g4 by ANTLR 4.9.3
package io.deepn.flow.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FlowParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FlowVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FlowParser#chunk}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChunk(FlowParser.ChunkContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(FlowParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(FlowParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#statementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementGroup(FlowParser.StatementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#statementOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementOrExpression(FlowParser.StatementOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(FlowParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#variableAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAssignment(FlowParser.VariableAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#variableOperatorAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableOperatorAssignment(FlowParser.VariableOperatorAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#deleteVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteVar(FlowParser.DeleteVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(FlowParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#whileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(FlowParser.WhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#forLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForLoop(FlowParser.ForLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#foreachLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForeachLoop(FlowParser.ForeachLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(FlowParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#elseifCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseifCondition(FlowParser.ElseifConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#elseCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseCondition(FlowParser.ElseConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(FlowParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#funcbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncbody(FlowParser.FuncbodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#nameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameList(FlowParser.NameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#functionNameField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionNameField(FlowParser.FunctionNameFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(FlowParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableconstructorExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableconstructorExpression(FlowParser.TableconstructorExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lambdaExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpression(FlowParser.LambdaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorUnaryExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorUnaryExpression(FlowParser.OperatorUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorAndExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAndExpression(FlowParser.OperatorAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpression(FlowParser.BoolExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberExpression(FlowParser.NumberExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorPowerExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorPowerExpression(FlowParser.OperatorPowerExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorAddSubExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAddSubExpression(FlowParser.OperatorAddSubExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jsonExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonExpression(FlowParser.JsonExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorOrExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorOrExpression(FlowParser.OperatorOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorMulDivModExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorMulDivModExpression(FlowParser.OperatorMulDivModExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prefixexpExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixexpExpression(FlowParser.PrefixexpExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorComparisonExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorComparisonExpression(FlowParser.OperatorComparisonExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringExpression(FlowParser.StringExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorBitwiseExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorBitwiseExpression(FlowParser.OperatorBitwiseExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullExpression(FlowParser.NullExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code strategyFunctionExpression}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStrategyFunctionExpression(FlowParser.StrategyFunctionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fc}
	 * labeled alternative in {@link FlowParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFc(FlowParser.FcContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#jsonPair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonPair(FlowParser.JsonPairContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#jsonObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonObject(FlowParser.JsonObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#prefixexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixexp(FlowParser.PrefixexpContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(FlowParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#varOrExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarOrExp(FlowParser.VarOrExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#var_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_(FlowParser.Var_Context ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#varSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarSuffix(FlowParser.VarSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#mendatoryNamedExpressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMendatoryNamedExpressionList(FlowParser.MendatoryNamedExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#mendatoryNamedExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMendatoryNamedExpression(FlowParser.MendatoryNamedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(FlowParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#namedExpressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedExpressionList(FlowParser.NamedExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#namedExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedExpression(FlowParser.NamedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#tableconstructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableconstructor(FlowParser.TableconstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#fieldlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldlist(FlowParser.FieldlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#staticVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStaticVariable(FlowParser.StaticVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#operatorOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorOr(FlowParser.OperatorOrContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#operatorAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAnd(FlowParser.OperatorAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#operatorAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAssignment(FlowParser.OperatorAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#operatorComparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorComparison(FlowParser.OperatorComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#operatorAddSub}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAddSub(FlowParser.OperatorAddSubContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#operatorMulDivMod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorMulDivMod(FlowParser.OperatorMulDivModContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#operatorBitwise}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorBitwise(FlowParser.OperatorBitwiseContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#operatorUnary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorUnary(FlowParser.OperatorUnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#operatorPower}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorPower(FlowParser.OperatorPowerContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(FlowParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(FlowParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlowParser#bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(FlowParser.BoolContext ctx);
}