// Generated from DeepScript.g4 by ANTLR 4.9.3
package io.deepn.script.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DeepScriptParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DeepScriptVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#chunk}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChunk(DeepScriptParser.ChunkContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(DeepScriptParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(DeepScriptParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#statementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementGroup(DeepScriptParser.StatementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#statementOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementOrExpression(DeepScriptParser.StatementOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(DeepScriptParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#variableAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAssignment(DeepScriptParser.VariableAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#variableOperatorAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableOperatorAssignment(DeepScriptParser.VariableOperatorAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#deleteVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteVar(DeepScriptParser.DeleteVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(DeepScriptParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#whileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(DeepScriptParser.WhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#forLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForLoop(DeepScriptParser.ForLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#foreachLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForeachLoop(DeepScriptParser.ForeachLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(DeepScriptParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#elseifCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseifCondition(DeepScriptParser.ElseifConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#elseCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseCondition(DeepScriptParser.ElseConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(DeepScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#funcbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncbody(DeepScriptParser.FuncbodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#nameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameList(DeepScriptParser.NameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#functionNameField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionNameField(DeepScriptParser.FunctionNameFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(DeepScriptParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#callablePrimitives}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallablePrimitives(DeepScriptParser.CallablePrimitivesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableconstructorExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableconstructorExpression(DeepScriptParser.TableconstructorExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lambdaExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpression(DeepScriptParser.LambdaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorUnaryExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorUnaryExpression(DeepScriptParser.OperatorUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorAndExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAndExpression(DeepScriptParser.OperatorAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpression(DeepScriptParser.BoolExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberExpression(DeepScriptParser.NumberExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorPowerExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorPowerExpression(DeepScriptParser.OperatorPowerExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorAddSubExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAddSubExpression(DeepScriptParser.OperatorAddSubExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jsonExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonExpression(DeepScriptParser.JsonExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorOrExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorOrExpression(DeepScriptParser.OperatorOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorMulDivModExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorMulDivModExpression(DeepScriptParser.OperatorMulDivModExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prefixexpExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixexpExpression(DeepScriptParser.PrefixexpExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primitiveCall}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveCall(DeepScriptParser.PrimitiveCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorComparisonExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorComparisonExpression(DeepScriptParser.OperatorComparisonExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringExpression(DeepScriptParser.StringExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorBitwiseExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorBitwiseExpression(DeepScriptParser.OperatorBitwiseExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullExpression(DeepScriptParser.NullExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fc}
	 * labeled alternative in {@link DeepScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFc(DeepScriptParser.FcContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#jsonPair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonPair(DeepScriptParser.JsonPairContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#jsonObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonObject(DeepScriptParser.JsonObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#prefixexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixexp(DeepScriptParser.PrefixexpContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(DeepScriptParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#varOrExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarOrExp(DeepScriptParser.VarOrExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#var_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_(DeepScriptParser.Var_Context ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#varSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarSuffix(DeepScriptParser.VarSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(DeepScriptParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#namedExpressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedExpressionList(DeepScriptParser.NamedExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#namedExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedExpression(DeepScriptParser.NamedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#tableconstructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableconstructor(DeepScriptParser.TableconstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#fieldlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldlist(DeepScriptParser.FieldlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#staticVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStaticVariable(DeepScriptParser.StaticVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#operatorOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorOr(DeepScriptParser.OperatorOrContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#operatorAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAnd(DeepScriptParser.OperatorAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#operatorAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAssignment(DeepScriptParser.OperatorAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#operatorComparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorComparison(DeepScriptParser.OperatorComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#operatorAddSub}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAddSub(DeepScriptParser.OperatorAddSubContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#operatorMulDivMod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorMulDivMod(DeepScriptParser.OperatorMulDivModContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#operatorBitwise}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorBitwise(DeepScriptParser.OperatorBitwiseContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#operatorUnary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorUnary(DeepScriptParser.OperatorUnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#operatorPower}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorPower(DeepScriptParser.OperatorPowerContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(DeepScriptParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(DeepScriptParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link DeepScriptParser#bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(DeepScriptParser.BoolContext ctx);
}