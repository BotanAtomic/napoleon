// Generated from DeepScript.g4 by ANTLR 4.9.3
package io.deepn.script.generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DeepScriptParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, NAME=58, NORMALSTRING=59, 
		CHARSTRING=60, LONGSTRING=61, INT=62, HEX=63, FLOAT=64, COMMENT=65, LINE_COMMENT=66, 
		WS=67, NEWLINE=68;
	public static final int
		RULE_chunk = 0, RULE_block = 1, RULE_statementGroup = 2, RULE_statementOrExpression = 3, 
		RULE_statement = 4, RULE_condition = 5, RULE_elseifCondition = 6, RULE_elseCondition = 7, 
		RULE_whileLoop = 8, RULE_repeatLoop = 9, RULE_forLoop = 10, RULE_foreachLoop = 11, 
		RULE_returnStatement = 12, RULE_breakStatement = 13, RULE_variableAssignment = 14, 
		RULE_variableOperatorAssignment = 15, RULE_deleteVar = 16, RULE_functionDeclaration = 17, 
		RULE_nameList = 18, RULE_functionNameField = 19, RULE_namedExpressionList = 20, 
		RULE_namedExpression = 21, RULE_expressionList = 22, RULE_expression = 23, 
		RULE_jsonPair = 24, RULE_jsonObject = 25, RULE_prefixexp = 26, RULE_functionCall = 27, 
		RULE_varOrExp = 28, RULE_var_ = 29, RULE_varSuffix = 30, RULE_args = 31, 
		RULE_funcbody = 32, RULE_tableconstructor = 33, RULE_fieldlist = 34, RULE_staticVariable = 35, 
		RULE_operatorOr = 36, RULE_operatorAnd = 37, RULE_operatorAssignment = 38, 
		RULE_operatorComparison = 39, RULE_operatorAddSub = 40, RULE_operatorMulDivMod = 41, 
		RULE_operatorBitwise = 42, RULE_operatorUnary = 43, RULE_operatorPower = 44, 
		RULE_number = 45, RULE_string = 46, RULE_bool = 47;
	private static String[] makeRuleNames() {
		return new String[] {
			"chunk", "block", "statementGroup", "statementOrExpression", "statement", 
			"condition", "elseifCondition", "elseCondition", "whileLoop", "repeatLoop", 
			"forLoop", "foreachLoop", "returnStatement", "breakStatement", "variableAssignment", 
			"variableOperatorAssignment", "deleteVar", "functionDeclaration", "nameList", 
			"functionNameField", "namedExpressionList", "namedExpression", "expressionList", 
			"expression", "jsonPair", "jsonObject", "prefixexp", "functionCall", 
			"varOrExp", "var_", "varSuffix", "args", "funcbody", "tableconstructor", 
			"fieldlist", "staticVariable", "operatorOr", "operatorAnd", "operatorAssignment", 
			"operatorComparison", "operatorAddSub", "operatorMulDivMod", "operatorBitwise", 
			"operatorUnary", "operatorPower", "number", "string", "bool"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'then'", "'end'", "'elseif'", "'else'", "'while'", "'do'", 
			"'repeat'", "'until'", "'for'", "'='", "','", "'in'", "'return'", "'break'", 
			"'delete'", "'function'", "'null'", "'->'", "'['", "']'", "':'", "'{'", 
			"'}'", "'('", "')'", "'.'", "'static'", "'or'", "'and'", "'+='", "'-='", 
			"'/='", "'//='", "'*='", "'<'", "'>'", "'<='", "'>='", "'!='", "'=='", 
			"'+'", "'-'", "'*'", "'/'", "'%'", "'//'", "'&'", "'|'", "'^'", "'<<'", 
			"'>>'", "'not'", "'~'", "'**'", "'true'", "'false'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "NAME", "NORMALSTRING", 
			"CHARSTRING", "LONGSTRING", "INT", "HEX", "FLOAT", "COMMENT", "LINE_COMMENT", 
			"WS", "NEWLINE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "DeepScript.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DeepScriptParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ChunkContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode EOF() { return getToken(DeepScriptParser.EOF, 0); }
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public ChunkContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_chunk; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitChunk(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChunkContext chunk() throws RecognitionException {
		ChunkContext _localctx = new ChunkContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_chunk);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(96);
					match(NEWLINE);
					}
					} 
				}
				setState(101);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(102);
			block();
			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE) {
				{
				{
				setState(103);
				match(NEWLINE);
				}
				}
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(109);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public StatementGroupContext statementGroup() {
			return getRuleContext(StatementGroupContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_block);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 1)) & ~0x3f) == 0 && ((1L << (_la - 1)) & ((1L << (T__0 - 1)) | (1L << (T__5 - 1)) | (1L << (T__7 - 1)) | (1L << (T__9 - 1)) | (1L << (T__14 - 1)) | (1L << (T__15 - 1)) | (1L << (T__16 - 1)) | (1L << (T__17 - 1)) | (1L << (T__19 - 1)) | (1L << (T__22 - 1)) | (1L << (T__24 - 1)) | (1L << (T__27 - 1)) | (1L << (T__42 - 1)) | (1L << (T__52 - 1)) | (1L << (T__53 - 1)) | (1L << (T__55 - 1)) | (1L << (T__56 - 1)) | (1L << (NAME - 1)) | (1L << (NORMALSTRING - 1)) | (1L << (CHARSTRING - 1)) | (1L << (LONGSTRING - 1)) | (1L << (INT - 1)) | (1L << (HEX - 1)) | (1L << (FLOAT - 1)))) != 0)) {
				{
				setState(111);
				statementGroup();
				}
			}

			setState(117);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(114);
					match(NEWLINE);
					}
					} 
				}
				setState(119);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementGroupContext extends ParserRuleContext {
		public List<StatementOrExpressionContext> statementOrExpression() {
			return getRuleContexts(StatementOrExpressionContext.class);
		}
		public StatementOrExpressionContext statementOrExpression(int i) {
			return getRuleContext(StatementOrExpressionContext.class,i);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public StatementGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementGroup; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitStatementGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementGroupContext statementGroup() throws RecognitionException {
		StatementGroupContext _localctx = new StatementGroupContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statementGroup);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			statementOrExpression();
			setState(129);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(122); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(121);
						match(NEWLINE);
						}
						}
						setState(124); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==NEWLINE );
					setState(126);
					statementOrExpression();
					}
					} 
				}
				setState(131);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementOrExpressionContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementOrExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitStatementOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementOrExpressionContext statementOrExpression() throws RecognitionException {
		StatementOrExpressionContext _localctx = new StatementOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_statementOrExpression);
		try {
			setState(134);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(132);
				statement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(133);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public VariableAssignmentContext variableAssignment() {
			return getRuleContext(VariableAssignmentContext.class,0);
		}
		public VariableOperatorAssignmentContext variableOperatorAssignment() {
			return getRuleContext(VariableOperatorAssignmentContext.class,0);
		}
		public DeleteVarContext deleteVar() {
			return getRuleContext(DeleteVarContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public WhileLoopContext whileLoop() {
			return getRuleContext(WhileLoopContext.class,0);
		}
		public RepeatLoopContext repeatLoop() {
			return getRuleContext(RepeatLoopContext.class,0);
		}
		public ForLoopContext forLoop() {
			return getRuleContext(ForLoopContext.class,0);
		}
		public ForeachLoopContext foreachLoop() {
			return getRuleContext(ForeachLoopContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_statement);
		try {
			setState(146);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(136);
				variableAssignment();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(137);
				variableOperatorAssignment();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(138);
				deleteVar();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(139);
				breakStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(140);
				whileLoop();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(141);
				repeatLoop();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(142);
				forLoop();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(143);
				foreachLoop();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(144);
				condition();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(145);
				functionDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public List<ElseifConditionContext> elseifCondition() {
			return getRuleContexts(ElseifConditionContext.class);
		}
		public ElseifConditionContext elseifCondition(int i) {
			return getRuleContext(ElseifConditionContext.class,i);
		}
		public ElseConditionContext elseCondition() {
			return getRuleContext(ElseConditionContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_condition);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			match(T__0);
			setState(149);
			expression(0);
			setState(150);
			match(T__1);
			setState(154);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(151);
					match(NEWLINE);
					}
					} 
				}
				setState(156);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(157);
			block();
			setState(161);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(158);
				elseifCondition();
				}
				}
				setState(163);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(164);
				elseCondition();
				}
			}

			setState(167);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElseifConditionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public ElseifConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseifCondition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitElseifCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseifConditionContext elseifCondition() throws RecognitionException {
		ElseifConditionContext _localctx = new ElseifConditionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_elseifCondition);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			match(T__3);
			setState(170);
			expression(0);
			setState(171);
			match(T__1);
			setState(175);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(172);
					match(NEWLINE);
					}
					} 
				}
				setState(177);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			setState(178);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElseConditionContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public ElseConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseCondition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitElseCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseConditionContext elseCondition() throws RecognitionException {
		ElseConditionContext _localctx = new ElseConditionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_elseCondition);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			match(T__4);
			setState(184);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(181);
					match(NEWLINE);
					}
					} 
				}
				setState(186);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			setState(187);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileLoopContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public WhileLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileLoop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitWhileLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileLoopContext whileLoop() throws RecognitionException {
		WhileLoopContext _localctx = new WhileLoopContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_whileLoop);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			match(T__5);
			setState(190);
			expression(0);
			setState(191);
			match(T__6);
			setState(195);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(192);
					match(NEWLINE);
					}
					} 
				}
				setState(197);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			setState(198);
			block();
			setState(199);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RepeatLoopContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public RepeatLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_repeatLoop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitRepeatLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RepeatLoopContext repeatLoop() throws RecognitionException {
		RepeatLoopContext _localctx = new RepeatLoopContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_repeatLoop);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(T__7);
			setState(205);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(202);
					match(NEWLINE);
					}
					} 
				}
				setState(207);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			setState(208);
			block();
			setState(209);
			match(T__8);
			setState(210);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForLoopContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(DeepScriptParser.NAME, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public ForLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forLoop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitForLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForLoopContext forLoop() throws RecognitionException {
		ForLoopContext _localctx = new ForLoopContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_forLoop);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			match(T__9);
			setState(213);
			match(NAME);
			setState(214);
			match(T__10);
			setState(215);
			expression(0);
			setState(216);
			match(T__11);
			setState(217);
			expression(0);
			setState(220);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(218);
				match(T__11);
				setState(219);
				expression(0);
				}
			}

			setState(222);
			match(T__6);
			setState(226);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(223);
					match(NEWLINE);
					}
					} 
				}
				setState(228);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			}
			setState(229);
			block();
			setState(230);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForeachLoopContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(DeepScriptParser.NAME, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public ForeachLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_foreachLoop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitForeachLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForeachLoopContext foreachLoop() throws RecognitionException {
		ForeachLoopContext _localctx = new ForeachLoopContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_foreachLoop);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			match(T__9);
			setState(233);
			match(NAME);
			setState(234);
			match(T__12);
			setState(235);
			expression(0);
			setState(236);
			match(T__6);
			setState(240);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(237);
					match(NEWLINE);
					}
					} 
				}
				setState(242);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			setState(243);
			block();
			setState(244);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			match(T__13);
			setState(248);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 17)) & ~0x3f) == 0 && ((1L << (_la - 17)) & ((1L << (T__16 - 17)) | (1L << (T__17 - 17)) | (1L << (T__19 - 17)) | (1L << (T__22 - 17)) | (1L << (T__24 - 17)) | (1L << (T__42 - 17)) | (1L << (T__52 - 17)) | (1L << (T__53 - 17)) | (1L << (T__55 - 17)) | (1L << (T__56 - 17)) | (1L << (NAME - 17)) | (1L << (NORMALSTRING - 17)) | (1L << (CHARSTRING - 17)) | (1L << (LONGSTRING - 17)) | (1L << (INT - 17)) | (1L << (HEX - 17)) | (1L << (FLOAT - 17)))) != 0)) {
				{
				setState(247);
				expressionList();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BreakStatementContext extends ParserRuleContext {
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			match(T__14);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableAssignmentContext extends ParserRuleContext {
		public Var_Context var_() {
			return getRuleContext(Var_Context.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StaticVariableContext staticVariable() {
			return getRuleContext(StaticVariableContext.class,0);
		}
		public VariableAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableAssignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitVariableAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableAssignmentContext variableAssignment() throws RecognitionException {
		VariableAssignmentContext _localctx = new VariableAssignmentContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_variableAssignment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__27) {
				{
				setState(252);
				staticVariable();
				}
			}

			setState(255);
			var_();
			setState(256);
			match(T__10);
			setState(257);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableOperatorAssignmentContext extends ParserRuleContext {
		public Var_Context var_() {
			return getRuleContext(Var_Context.class,0);
		}
		public OperatorAssignmentContext operatorAssignment() {
			return getRuleContext(OperatorAssignmentContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableOperatorAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableOperatorAssignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitVariableOperatorAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableOperatorAssignmentContext variableOperatorAssignment() throws RecognitionException {
		VariableOperatorAssignmentContext _localctx = new VariableOperatorAssignmentContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_variableOperatorAssignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			var_();
			setState(260);
			operatorAssignment();
			setState(261);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeleteVarContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(DeepScriptParser.NAME, 0); }
		public List<VarSuffixContext> varSuffix() {
			return getRuleContexts(VarSuffixContext.class);
		}
		public VarSuffixContext varSuffix(int i) {
			return getRuleContext(VarSuffixContext.class,i);
		}
		public DeleteVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteVar; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitDeleteVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeleteVarContext deleteVar() throws RecognitionException {
		DeleteVarContext _localctx = new DeleteVarContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_deleteVar);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(263);
			match(T__15);
			{
			setState(264);
			match(NAME);
			setState(266); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(265);
				varSuffix();
				}
				}
				setState(268); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__19) | (1L << T__24) | (1L << T__26))) != 0) );
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(DeepScriptParser.NAME, 0); }
		public FuncbodyContext funcbody() {
			return getRuleContext(FuncbodyContext.class,0);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_functionDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			match(T__16);
			setState(271);
			match(NAME);
			setState(272);
			funcbody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameListContext extends ParserRuleContext {
		public List<FunctionNameFieldContext> functionNameField() {
			return getRuleContexts(FunctionNameFieldContext.class);
		}
		public FunctionNameFieldContext functionNameField(int i) {
			return getRuleContext(FunctionNameFieldContext.class,i);
		}
		public NameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nameList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitNameList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameListContext nameList() throws RecognitionException {
		NameListContext _localctx = new NameListContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_nameList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			functionNameField();
			setState(279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(275);
				match(T__11);
				setState(276);
				functionNameField();
				}
				}
				setState(281);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionNameFieldContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(DeepScriptParser.NAME, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FunctionNameFieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionNameField; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitFunctionNameField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionNameFieldContext functionNameField() throws RecognitionException {
		FunctionNameFieldContext _localctx = new FunctionNameFieldContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_functionNameField);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			match(NAME);
			setState(285);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(283);
				match(T__10);
				setState(284);
				expression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamedExpressionListContext extends ParserRuleContext {
		public List<NamedExpressionContext> namedExpression() {
			return getRuleContexts(NamedExpressionContext.class);
		}
		public NamedExpressionContext namedExpression(int i) {
			return getRuleContext(NamedExpressionContext.class,i);
		}
		public NamedExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedExpressionList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitNamedExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedExpressionListContext namedExpressionList() throws RecognitionException {
		NamedExpressionListContext _localctx = new NamedExpressionListContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_namedExpressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			namedExpression();
			setState(292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(288);
				match(T__11);
				setState(289);
				namedExpression();
				}
				}
				setState(294);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamedExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode NAME() { return getToken(DeepScriptParser.NAME, 0); }
		public NamedExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitNamedExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedExpressionContext namedExpression() throws RecognitionException {
		NamedExpressionContext _localctx = new NamedExpressionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_namedExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(297);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(295);
				match(NAME);
				setState(296);
				match(T__10);
				}
				break;
			}
			setState(299);
			expression(0);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(301);
			expression(0);
			setState(306);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(302);
				match(T__11);
				setState(303);
				expression(0);
				}
				}
				setState(308);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TableconstructorExpressionContext extends ExpressionContext {
		public TableconstructorContext tableconstructor() {
			return getRuleContext(TableconstructorContext.class,0);
		}
		public TableconstructorExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitTableconstructorExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LambdaExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<TerminalNode> NAME() { return getTokens(DeepScriptParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(DeepScriptParser.NAME, i);
		}
		public LambdaExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitLambdaExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorUnaryExpressionContext extends ExpressionContext {
		public OperatorUnaryContext operatorUnary() {
			return getRuleContext(OperatorUnaryContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public OperatorUnaryExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorAndExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OperatorAndContext operatorAnd() {
			return getRuleContext(OperatorAndContext.class,0);
		}
		public OperatorAndExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolExpressionContext extends ExpressionContext {
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public BoolExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitBoolExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NumberExpressionContext extends ExpressionContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public NumberExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitNumberExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorPowerExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OperatorPowerContext operatorPower() {
			return getRuleContext(OperatorPowerContext.class,0);
		}
		public OperatorPowerExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorPowerExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorAddSubExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OperatorAddSubContext operatorAddSub() {
			return getRuleContext(OperatorAddSubContext.class,0);
		}
		public OperatorAddSubExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorAddSubExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JsonExpressionContext extends ExpressionContext {
		public JsonObjectContext jsonObject() {
			return getRuleContext(JsonObjectContext.class,0);
		}
		public JsonExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitJsonExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorOrExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OperatorOrContext operatorOr() {
			return getRuleContext(OperatorOrContext.class,0);
		}
		public OperatorOrExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorMulDivModExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OperatorMulDivModContext operatorMulDivMod() {
			return getRuleContext(OperatorMulDivModContext.class,0);
		}
		public OperatorMulDivModExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorMulDivModExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrefixexpExpressionContext extends ExpressionContext {
		public PrefixexpContext prefixexp() {
			return getRuleContext(PrefixexpContext.class,0);
		}
		public PrefixexpExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitPrefixexpExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorComparisonExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OperatorComparisonContext operatorComparison() {
			return getRuleContext(OperatorComparisonContext.class,0);
		}
		public OperatorComparisonExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorComparisonExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringExpressionContext extends ExpressionContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public StringExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitStringExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorBitwiseExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OperatorBitwiseContext operatorBitwise() {
			return getRuleContext(OperatorBitwiseContext.class,0);
		}
		public OperatorBitwiseExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorBitwiseExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NullExpressionContext extends ExpressionContext {
		public NullExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitNullExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FcContext extends ExpressionContext {
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public FcContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitFc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(334);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				_localctx = new StringExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(310);
				string();
				}
				break;
			case 2:
				{
				_localctx = new NumberExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(311);
				number();
				}
				break;
			case 3:
				{
				_localctx = new BoolExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(312);
				bool();
				}
				break;
			case 4:
				{
				_localctx = new PrefixexpExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(313);
				prefixexp();
				}
				break;
			case 5:
				{
				_localctx = new FcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(314);
				functionCall();
				}
				break;
			case 6:
				{
				_localctx = new TableconstructorExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(315);
				tableconstructor();
				}
				break;
			case 7:
				{
				_localctx = new OperatorUnaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(316);
				operatorUnary();
				setState(317);
				expression(10);
				}
				break;
			case 8:
				{
				_localctx = new NullExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(319);
				match(T__17);
				}
				break;
			case 9:
				{
				_localctx = new JsonExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(320);
				jsonObject();
				}
				break;
			case 10:
				{
				_localctx = new LambdaExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(321);
				match(T__16);
				setState(330);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NAME) {
					{
					setState(322);
					match(NAME);
					setState(327);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__11) {
						{
						{
						setState(323);
						match(T__11);
						setState(324);
						match(NAME);
						}
						}
						setState(329);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(332);
				match(T__18);
				setState(333);
				expression(1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(366);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(364);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
					case 1:
						{
						_localctx = new OperatorPowerExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(336);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(337);
						operatorPower();
						setState(338);
						expression(11);
						}
						break;
					case 2:
						{
						_localctx = new OperatorMulDivModExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(340);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(341);
						operatorMulDivMod();
						setState(342);
						expression(10);
						}
						break;
					case 3:
						{
						_localctx = new OperatorAddSubExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(344);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(345);
						operatorAddSub();
						setState(346);
						expression(9);
						}
						break;
					case 4:
						{
						_localctx = new OperatorComparisonExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(348);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(349);
						operatorComparison();
						setState(350);
						expression(8);
						}
						break;
					case 5:
						{
						_localctx = new OperatorAndExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(352);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(353);
						operatorAnd();
						setState(354);
						expression(7);
						}
						break;
					case 6:
						{
						_localctx = new OperatorOrExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(356);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(357);
						operatorOr();
						setState(358);
						expression(6);
						}
						break;
					case 7:
						{
						_localctx = new OperatorBitwiseExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(360);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(361);
						operatorBitwise();
						setState(362);
						expression(5);
						}
						break;
					}
					} 
				}
				setState(368);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class JsonPairContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode NAME() { return getToken(DeepScriptParser.NAME, 0); }
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public JsonPairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonPair; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitJsonPair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonPairContext jsonPair() throws RecognitionException {
		JsonPairContext _localctx = new JsonPairContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_jsonPair);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(372);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE) {
				{
				{
				setState(369);
				match(NEWLINE);
				}
				}
				setState(374);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(381);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__19:
				{
				{
				setState(375);
				match(T__19);
				setState(376);
				expression(0);
				setState(377);
				match(T__20);
				}
				}
				break;
			case NAME:
				{
				setState(379);
				match(NAME);
				}
				break;
			case NORMALSTRING:
			case CHARSTRING:
			case LONGSTRING:
				{
				setState(380);
				string();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(383);
			match(T__21);
			setState(384);
			expression(0);
			setState(388);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(385);
					match(NEWLINE);
					}
					} 
				}
				setState(390);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JsonObjectContext extends ParserRuleContext {
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public List<JsonPairContext> jsonPair() {
			return getRuleContexts(JsonPairContext.class);
		}
		public JsonPairContext jsonPair(int i) {
			return getRuleContext(JsonPairContext.class,i);
		}
		public JsonObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonObject; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitJsonObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonObjectContext jsonObject() throws RecognitionException {
		JsonObjectContext _localctx = new JsonObjectContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_jsonObject);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(391);
			match(T__22);
			setState(395);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(392);
					match(NEWLINE);
					}
					} 
				}
				setState(397);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			}
			setState(406);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				setState(398);
				jsonPair();
				setState(403);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(399);
					match(T__11);
					setState(400);
					jsonPair();
					}
					}
					setState(405);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
			setState(411);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE) {
				{
				{
				setState(408);
				match(NEWLINE);
				}
				}
				setState(413);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(414);
			match(T__23);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrefixexpContext extends ParserRuleContext {
		public VarOrExpContext varOrExp() {
			return getRuleContext(VarOrExpContext.class,0);
		}
		public List<ArgsContext> args() {
			return getRuleContexts(ArgsContext.class);
		}
		public ArgsContext args(int i) {
			return getRuleContext(ArgsContext.class,i);
		}
		public PrefixexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixexp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitPrefixexp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixexpContext prefixexp() throws RecognitionException {
		PrefixexpContext _localctx = new PrefixexpContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_prefixexp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(416);
			varOrExp();
			setState(420);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(417);
					args();
					}
					} 
				}
				setState(422);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionCallContext extends ParserRuleContext {
		public VarOrExpContext varOrExp() {
			return getRuleContext(VarOrExpContext.class,0);
		}
		public List<ArgsContext> args() {
			return getRuleContexts(ArgsContext.class);
		}
		public ArgsContext args(int i) {
			return getRuleContext(ArgsContext.class,i);
		}
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_functionCall);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(423);
			varOrExp();
			setState(425); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(424);
					args();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(427); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarOrExpContext extends ParserRuleContext {
		public Var_Context var_() {
			return getRuleContext(Var_Context.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VarOrExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varOrExp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitVarOrExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarOrExpContext varOrExp() throws RecognitionException {
		VarOrExpContext _localctx = new VarOrExpContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_varOrExp);
		try {
			setState(434);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(429);
				var_();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(430);
				match(T__24);
				setState(431);
				expression(0);
				setState(432);
				match(T__25);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_Context extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(DeepScriptParser.NAME, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<VarSuffixContext> varSuffix() {
			return getRuleContexts(VarSuffixContext.class);
		}
		public VarSuffixContext varSuffix(int i) {
			return getRuleContext(VarSuffixContext.class,i);
		}
		public Var_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitVar_(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_Context var_() throws RecognitionException {
		Var_Context _localctx = new Var_Context(_ctx, getState());
		enterRule(_localctx, 58, RULE_var_);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(442);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				{
				setState(436);
				match(NAME);
				}
				break;
			case T__24:
				{
				setState(437);
				match(T__24);
				setState(438);
				expression(0);
				setState(439);
				match(T__25);
				setState(440);
				varSuffix();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(447);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(444);
					varSuffix();
					}
					} 
				}
				setState(449);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarSuffixContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode NAME() { return getToken(DeepScriptParser.NAME, 0); }
		public List<ArgsContext> args() {
			return getRuleContexts(ArgsContext.class);
		}
		public ArgsContext args(int i) {
			return getRuleContext(ArgsContext.class,i);
		}
		public VarSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varSuffix; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitVarSuffix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarSuffixContext varSuffix() throws RecognitionException {
		VarSuffixContext _localctx = new VarSuffixContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_varSuffix);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(453);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__24) {
				{
				{
				setState(450);
				args();
				}
				}
				setState(455);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(462);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__19:
				{
				setState(456);
				match(T__19);
				setState(457);
				expression(0);
				setState(458);
				match(T__20);
				}
				break;
			case T__26:
				{
				setState(460);
				match(T__26);
				setState(461);
				match(NAME);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgsContext extends ParserRuleContext {
		public NamedExpressionListContext namedExpressionList() {
			return getRuleContext(NamedExpressionListContext.class,0);
		}
		public ArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgsContext args() throws RecognitionException {
		ArgsContext _localctx = new ArgsContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_args);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(464);
			match(T__24);
			setState(466);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 17)) & ~0x3f) == 0 && ((1L << (_la - 17)) & ((1L << (T__16 - 17)) | (1L << (T__17 - 17)) | (1L << (T__19 - 17)) | (1L << (T__22 - 17)) | (1L << (T__24 - 17)) | (1L << (T__42 - 17)) | (1L << (T__52 - 17)) | (1L << (T__53 - 17)) | (1L << (T__55 - 17)) | (1L << (T__56 - 17)) | (1L << (NAME - 17)) | (1L << (NORMALSTRING - 17)) | (1L << (CHARSTRING - 17)) | (1L << (LONGSTRING - 17)) | (1L << (INT - 17)) | (1L << (HEX - 17)) | (1L << (FLOAT - 17)))) != 0)) {
				{
				setState(465);
				namedExpressionList();
				}
			}

			setState(468);
			match(T__25);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncbodyContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public NameListContext nameList() {
			return getRuleContext(NameListContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(DeepScriptParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(DeepScriptParser.NEWLINE, i);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public FuncbodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcbody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitFuncbody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncbodyContext funcbody() throws RecognitionException {
		FuncbodyContext _localctx = new FuncbodyContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_funcbody);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(470);
			match(T__24);
			setState(472);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(471);
				nameList();
				}
			}

			setState(474);
			match(T__25);
			setState(478);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(475);
					match(NEWLINE);
					}
					} 
				}
				setState(480);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			}
			setState(481);
			block();
			setState(483);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__13) {
				{
				setState(482);
				returnStatement();
				}
			}

			setState(488);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE) {
				{
				{
				setState(485);
				match(NEWLINE);
				}
				}
				setState(490);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(491);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TableconstructorContext extends ParserRuleContext {
		public FieldlistContext fieldlist() {
			return getRuleContext(FieldlistContext.class,0);
		}
		public TableconstructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableconstructor; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitTableconstructor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableconstructorContext tableconstructor() throws RecognitionException {
		TableconstructorContext _localctx = new TableconstructorContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_tableconstructor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(493);
			match(T__19);
			setState(495);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 17)) & ~0x3f) == 0 && ((1L << (_la - 17)) & ((1L << (T__16 - 17)) | (1L << (T__17 - 17)) | (1L << (T__19 - 17)) | (1L << (T__22 - 17)) | (1L << (T__24 - 17)) | (1L << (T__42 - 17)) | (1L << (T__52 - 17)) | (1L << (T__53 - 17)) | (1L << (T__55 - 17)) | (1L << (T__56 - 17)) | (1L << (NAME - 17)) | (1L << (NORMALSTRING - 17)) | (1L << (CHARSTRING - 17)) | (1L << (LONGSTRING - 17)) | (1L << (INT - 17)) | (1L << (HEX - 17)) | (1L << (FLOAT - 17)))) != 0)) {
				{
				setState(494);
				fieldlist();
				}
			}

			setState(497);
			match(T__20);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldlistContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public FieldlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldlist; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitFieldlist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldlistContext fieldlist() throws RecognitionException {
		FieldlistContext _localctx = new FieldlistContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_fieldlist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(499);
			expression(0);
			setState(504);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(500);
				match(T__11);
				setState(501);
				expression(0);
				}
				}
				setState(506);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StaticVariableContext extends ParserRuleContext {
		public StaticVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_staticVariable; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitStaticVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StaticVariableContext staticVariable() throws RecognitionException {
		StaticVariableContext _localctx = new StaticVariableContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_staticVariable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(507);
			match(T__27);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorOrContext extends ParserRuleContext {
		public OperatorOrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorOr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorOr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorOrContext operatorOr() throws RecognitionException {
		OperatorOrContext _localctx = new OperatorOrContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_operatorOr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(509);
			match(T__28);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorAndContext extends ParserRuleContext {
		public OperatorAndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorAnd; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorAndContext operatorAnd() throws RecognitionException {
		OperatorAndContext _localctx = new OperatorAndContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_operatorAnd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(511);
			match(T__29);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorAssignmentContext extends ParserRuleContext {
		public OperatorAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorAssignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorAssignmentContext operatorAssignment() throws RecognitionException {
		OperatorAssignmentContext _localctx = new OperatorAssignmentContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_operatorAssignment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(513);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorComparisonContext extends ParserRuleContext {
		public OperatorComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorComparison; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorComparison(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorComparisonContext operatorComparison() throws RecognitionException {
		OperatorComparisonContext _localctx = new OperatorComparisonContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_operatorComparison);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(515);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorAddSubContext extends ParserRuleContext {
		public OperatorAddSubContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorAddSub; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorAddSub(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorAddSubContext operatorAddSub() throws RecognitionException {
		OperatorAddSubContext _localctx = new OperatorAddSubContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_operatorAddSub);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(517);
			_la = _input.LA(1);
			if ( !(_la==T__41 || _la==T__42) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorMulDivModContext extends ParserRuleContext {
		public OperatorMulDivModContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorMulDivMod; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorMulDivMod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorMulDivModContext operatorMulDivMod() throws RecognitionException {
		OperatorMulDivModContext _localctx = new OperatorMulDivModContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_operatorMulDivMod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(519);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorBitwiseContext extends ParserRuleContext {
		public OperatorBitwiseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorBitwise; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorBitwise(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorBitwiseContext operatorBitwise() throws RecognitionException {
		OperatorBitwiseContext _localctx = new OperatorBitwiseContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_operatorBitwise);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(521);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorUnaryContext extends ParserRuleContext {
		public OperatorUnaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorUnary; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorUnary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorUnaryContext operatorUnary() throws RecognitionException {
		OperatorUnaryContext _localctx = new OperatorUnaryContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_operatorUnary);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(523);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__42) | (1L << T__52) | (1L << T__53))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorPowerContext extends ParserRuleContext {
		public OperatorPowerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorPower; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitOperatorPower(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorPowerContext operatorPower() throws RecognitionException {
		OperatorPowerContext _localctx = new OperatorPowerContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_operatorPower);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(525);
			match(T__54);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(DeepScriptParser.INT, 0); }
		public TerminalNode HEX() { return getToken(DeepScriptParser.HEX, 0); }
		public TerminalNode FLOAT() { return getToken(DeepScriptParser.FLOAT, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(527);
			_la = _input.LA(1);
			if ( !(((((_la - 62)) & ~0x3f) == 0 && ((1L << (_la - 62)) & ((1L << (INT - 62)) | (1L << (HEX - 62)) | (1L << (FLOAT - 62)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public TerminalNode NORMALSTRING() { return getToken(DeepScriptParser.NORMALSTRING, 0); }
		public TerminalNode CHARSTRING() { return getToken(DeepScriptParser.CHARSTRING, 0); }
		public TerminalNode LONGSTRING() { return getToken(DeepScriptParser.LONGSTRING, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_string);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(529);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NORMALSTRING) | (1L << CHARSTRING) | (1L << LONGSTRING))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolContext extends ParserRuleContext {
		public BoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DeepScriptVisitor ) return ((DeepScriptVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolContext bool() throws RecognitionException {
		BoolContext _localctx = new BoolContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_bool);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(531);
			_la = _input.LA(1);
			if ( !(_la==T__55 || _la==T__56) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 23:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 11);
		case 1:
			return precpred(_ctx, 9);
		case 2:
			return precpred(_ctx, 8);
		case 3:
			return precpred(_ctx, 7);
		case 4:
			return precpred(_ctx, 6);
		case 5:
			return precpred(_ctx, 5);
		case 6:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3F\u0218\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\3\2\7\2d\n\2\f\2\16\2g\13\2"+
		"\3\2\3\2\7\2k\n\2\f\2\16\2n\13\2\3\2\3\2\3\3\5\3s\n\3\3\3\7\3v\n\3\f\3"+
		"\16\3y\13\3\3\4\3\4\6\4}\n\4\r\4\16\4~\3\4\7\4\u0082\n\4\f\4\16\4\u0085"+
		"\13\4\3\5\3\5\5\5\u0089\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5"+
		"\6\u0095\n\6\3\7\3\7\3\7\3\7\7\7\u009b\n\7\f\7\16\7\u009e\13\7\3\7\3\7"+
		"\7\7\u00a2\n\7\f\7\16\7\u00a5\13\7\3\7\5\7\u00a8\n\7\3\7\3\7\3\b\3\b\3"+
		"\b\3\b\7\b\u00b0\n\b\f\b\16\b\u00b3\13\b\3\b\3\b\3\t\3\t\7\t\u00b9\n\t"+
		"\f\t\16\t\u00bc\13\t\3\t\3\t\3\n\3\n\3\n\3\n\7\n\u00c4\n\n\f\n\16\n\u00c7"+
		"\13\n\3\n\3\n\3\n\3\13\3\13\7\13\u00ce\n\13\f\13\16\13\u00d1\13\13\3\13"+
		"\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00df\n\f\3\f\3\f"+
		"\7\f\u00e3\n\f\f\f\16\f\u00e6\13\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\7\r\u00f1\n\r\f\r\16\r\u00f4\13\r\3\r\3\r\3\r\3\16\3\16\5\16\u00fb\n"+
		"\16\3\17\3\17\3\20\5\20\u0100\n\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\22\6\22\u010d\n\22\r\22\16\22\u010e\3\23\3\23\3\23\3"+
		"\23\3\24\3\24\3\24\7\24\u0118\n\24\f\24\16\24\u011b\13\24\3\25\3\25\3"+
		"\25\5\25\u0120\n\25\3\26\3\26\3\26\7\26\u0125\n\26\f\26\16\26\u0128\13"+
		"\26\3\27\3\27\5\27\u012c\n\27\3\27\3\27\3\30\3\30\3\30\7\30\u0133\n\30"+
		"\f\30\16\30\u0136\13\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u0148\n\31\f\31\16\31\u014b\13"+
		"\31\5\31\u014d\n\31\3\31\3\31\5\31\u0151\n\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u016f\n\31\f\31\16"+
		"\31\u0172\13\31\3\32\7\32\u0175\n\32\f\32\16\32\u0178\13\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\5\32\u0180\n\32\3\32\3\32\3\32\7\32\u0185\n\32\f"+
		"\32\16\32\u0188\13\32\3\33\3\33\7\33\u018c\n\33\f\33\16\33\u018f\13\33"+
		"\3\33\3\33\3\33\7\33\u0194\n\33\f\33\16\33\u0197\13\33\5\33\u0199\n\33"+
		"\3\33\7\33\u019c\n\33\f\33\16\33\u019f\13\33\3\33\3\33\3\34\3\34\7\34"+
		"\u01a5\n\34\f\34\16\34\u01a8\13\34\3\35\3\35\6\35\u01ac\n\35\r\35\16\35"+
		"\u01ad\3\36\3\36\3\36\3\36\3\36\5\36\u01b5\n\36\3\37\3\37\3\37\3\37\3"+
		"\37\3\37\5\37\u01bd\n\37\3\37\7\37\u01c0\n\37\f\37\16\37\u01c3\13\37\3"+
		" \7 \u01c6\n \f \16 \u01c9\13 \3 \3 \3 \3 \3 \3 \5 \u01d1\n \3!\3!\5!"+
		"\u01d5\n!\3!\3!\3\"\3\"\5\"\u01db\n\"\3\"\3\"\7\"\u01df\n\"\f\"\16\"\u01e2"+
		"\13\"\3\"\3\"\5\"\u01e6\n\"\3\"\7\"\u01e9\n\"\f\"\16\"\u01ec\13\"\3\""+
		"\3\"\3#\3#\5#\u01f2\n#\3#\3#\3$\3$\3$\7$\u01f9\n$\f$\16$\u01fc\13$\3%"+
		"\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60"+
		"\3\60\3\61\3\61\3\61\2\3\60\62\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`\2\13\3\2!%\3\2&+\3\2,-\3\2"+
		".\61\3\2\62\66\4\2--\678\3\2@B\3\2=?\3\2:;\2\u0231\2e\3\2\2\2\4r\3\2\2"+
		"\2\6z\3\2\2\2\b\u0088\3\2\2\2\n\u0094\3\2\2\2\f\u0096\3\2\2\2\16\u00ab"+
		"\3\2\2\2\20\u00b6\3\2\2\2\22\u00bf\3\2\2\2\24\u00cb\3\2\2\2\26\u00d6\3"+
		"\2\2\2\30\u00ea\3\2\2\2\32\u00f8\3\2\2\2\34\u00fc\3\2\2\2\36\u00ff\3\2"+
		"\2\2 \u0105\3\2\2\2\"\u0109\3\2\2\2$\u0110\3\2\2\2&\u0114\3\2\2\2(\u011c"+
		"\3\2\2\2*\u0121\3\2\2\2,\u012b\3\2\2\2.\u012f\3\2\2\2\60\u0150\3\2\2\2"+
		"\62\u0176\3\2\2\2\64\u0189\3\2\2\2\66\u01a2\3\2\2\28\u01a9\3\2\2\2:\u01b4"+
		"\3\2\2\2<\u01bc\3\2\2\2>\u01c7\3\2\2\2@\u01d2\3\2\2\2B\u01d8\3\2\2\2D"+
		"\u01ef\3\2\2\2F\u01f5\3\2\2\2H\u01fd\3\2\2\2J\u01ff\3\2\2\2L\u0201\3\2"+
		"\2\2N\u0203\3\2\2\2P\u0205\3\2\2\2R\u0207\3\2\2\2T\u0209\3\2\2\2V\u020b"+
		"\3\2\2\2X\u020d\3\2\2\2Z\u020f\3\2\2\2\\\u0211\3\2\2\2^\u0213\3\2\2\2"+
		"`\u0215\3\2\2\2bd\7F\2\2cb\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2fh\3\2"+
		"\2\2ge\3\2\2\2hl\5\4\3\2ik\7F\2\2ji\3\2\2\2kn\3\2\2\2lj\3\2\2\2lm\3\2"+
		"\2\2mo\3\2\2\2nl\3\2\2\2op\7\2\2\3p\3\3\2\2\2qs\5\6\4\2rq\3\2\2\2rs\3"+
		"\2\2\2sw\3\2\2\2tv\7F\2\2ut\3\2\2\2vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2x\5\3"+
		"\2\2\2yw\3\2\2\2z\u0083\5\b\5\2{}\7F\2\2|{\3\2\2\2}~\3\2\2\2~|\3\2\2\2"+
		"~\177\3\2\2\2\177\u0080\3\2\2\2\u0080\u0082\5\b\5\2\u0081|\3\2\2\2\u0082"+
		"\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\7\3\2\2\2"+
		"\u0085\u0083\3\2\2\2\u0086\u0089\5\n\6\2\u0087\u0089\5\60\31\2\u0088\u0086"+
		"\3\2\2\2\u0088\u0087\3\2\2\2\u0089\t\3\2\2\2\u008a\u0095\5\36\20\2\u008b"+
		"\u0095\5 \21\2\u008c\u0095\5\"\22\2\u008d\u0095\5\34\17\2\u008e\u0095"+
		"\5\22\n\2\u008f\u0095\5\24\13\2\u0090\u0095\5\26\f\2\u0091\u0095\5\30"+
		"\r\2\u0092\u0095\5\f\7\2\u0093\u0095\5$\23\2\u0094\u008a\3\2\2\2\u0094"+
		"\u008b\3\2\2\2\u0094\u008c\3\2\2\2\u0094\u008d\3\2\2\2\u0094\u008e\3\2"+
		"\2\2\u0094\u008f\3\2\2\2\u0094\u0090\3\2\2\2\u0094\u0091\3\2\2\2\u0094"+
		"\u0092\3\2\2\2\u0094\u0093\3\2\2\2\u0095\13\3\2\2\2\u0096\u0097\7\3\2"+
		"\2\u0097\u0098\5\60\31\2\u0098\u009c\7\4\2\2\u0099\u009b\7F\2\2\u009a"+
		"\u0099\3\2\2\2\u009b\u009e\3\2\2\2\u009c\u009a\3\2\2\2\u009c\u009d\3\2"+
		"\2\2\u009d\u009f\3\2\2\2\u009e\u009c\3\2\2\2\u009f\u00a3\5\4\3\2\u00a0"+
		"\u00a2\5\16\b\2\u00a1\u00a0\3\2\2\2\u00a2\u00a5\3\2\2\2\u00a3\u00a1\3"+
		"\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a7\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a6"+
		"\u00a8\5\20\t\2\u00a7\u00a6\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a9\3"+
		"\2\2\2\u00a9\u00aa\7\5\2\2\u00aa\r\3\2\2\2\u00ab\u00ac\7\6\2\2\u00ac\u00ad"+
		"\5\60\31\2\u00ad\u00b1\7\4\2\2\u00ae\u00b0\7F\2\2\u00af\u00ae\3\2\2\2"+
		"\u00b0\u00b3\3\2\2\2\u00b1\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b4"+
		"\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b4\u00b5\5\4\3\2\u00b5\17\3\2\2\2\u00b6"+
		"\u00ba\7\7\2\2\u00b7\u00b9\7F\2\2\u00b8\u00b7\3\2\2\2\u00b9\u00bc\3\2"+
		"\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bd\3\2\2\2\u00bc"+
		"\u00ba\3\2\2\2\u00bd\u00be\5\4\3\2\u00be\21\3\2\2\2\u00bf\u00c0\7\b\2"+
		"\2\u00c0\u00c1\5\60\31\2\u00c1\u00c5\7\t\2\2\u00c2\u00c4\7F\2\2\u00c3"+
		"\u00c2\3\2\2\2\u00c4\u00c7\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c5\u00c6\3\2"+
		"\2\2\u00c6\u00c8\3\2\2\2\u00c7\u00c5\3\2\2\2\u00c8\u00c9\5\4\3\2\u00c9"+
		"\u00ca\7\5\2\2\u00ca\23\3\2\2\2\u00cb\u00cf\7\n\2\2\u00cc\u00ce\7F\2\2"+
		"\u00cd\u00cc\3\2\2\2\u00ce\u00d1\3\2\2\2\u00cf\u00cd\3\2\2\2\u00cf\u00d0"+
		"\3\2\2\2\u00d0\u00d2\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d2\u00d3\5\4\3\2\u00d3"+
		"\u00d4\7\13\2\2\u00d4\u00d5\5\60\31\2\u00d5\25\3\2\2\2\u00d6\u00d7\7\f"+
		"\2\2\u00d7\u00d8\7<\2\2\u00d8\u00d9\7\r\2\2\u00d9\u00da\5\60\31\2\u00da"+
		"\u00db\7\16\2\2\u00db\u00de\5\60\31\2\u00dc\u00dd\7\16\2\2\u00dd\u00df"+
		"\5\60\31\2\u00de\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df\u00e0\3\2\2\2"+
		"\u00e0\u00e4\7\t\2\2\u00e1\u00e3\7F\2\2\u00e2\u00e1\3\2\2\2\u00e3\u00e6"+
		"\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e7\3\2\2\2\u00e6"+
		"\u00e4\3\2\2\2\u00e7\u00e8\5\4\3\2\u00e8\u00e9\7\5\2\2\u00e9\27\3\2\2"+
		"\2\u00ea\u00eb\7\f\2\2\u00eb\u00ec\7<\2\2\u00ec\u00ed\7\17\2\2\u00ed\u00ee"+
		"\5\60\31\2\u00ee\u00f2\7\t\2\2\u00ef\u00f1\7F\2\2\u00f0\u00ef\3\2\2\2"+
		"\u00f1\u00f4\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f5"+
		"\3\2\2\2\u00f4\u00f2\3\2\2\2\u00f5\u00f6\5\4\3\2\u00f6\u00f7\7\5\2\2\u00f7"+
		"\31\3\2\2\2\u00f8\u00fa\7\20\2\2\u00f9\u00fb\5.\30\2\u00fa\u00f9\3\2\2"+
		"\2\u00fa\u00fb\3\2\2\2\u00fb\33\3\2\2\2\u00fc\u00fd\7\21\2\2\u00fd\35"+
		"\3\2\2\2\u00fe\u0100\5H%\2\u00ff\u00fe\3\2\2\2\u00ff\u0100\3\2\2\2\u0100"+
		"\u0101\3\2\2\2\u0101\u0102\5<\37\2\u0102\u0103\7\r\2\2\u0103\u0104\5\60"+
		"\31\2\u0104\37\3\2\2\2\u0105\u0106\5<\37\2\u0106\u0107\5N(\2\u0107\u0108"+
		"\5\60\31\2\u0108!\3\2\2\2\u0109\u010a\7\22\2\2\u010a\u010c\7<\2\2\u010b"+
		"\u010d\5> \2\u010c\u010b\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u010c\3\2\2"+
		"\2\u010e\u010f\3\2\2\2\u010f#\3\2\2\2\u0110\u0111\7\23\2\2\u0111\u0112"+
		"\7<\2\2\u0112\u0113\5B\"\2\u0113%\3\2\2\2\u0114\u0119\5(\25\2\u0115\u0116"+
		"\7\16\2\2\u0116\u0118\5(\25\2\u0117\u0115\3\2\2\2\u0118\u011b\3\2\2\2"+
		"\u0119\u0117\3\2\2\2\u0119\u011a\3\2\2\2\u011a\'\3\2\2\2\u011b\u0119\3"+
		"\2\2\2\u011c\u011f\7<\2\2\u011d\u011e\7\r\2\2\u011e\u0120\5\60\31\2\u011f"+
		"\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120)\3\2\2\2\u0121\u0126\5,\27\2"+
		"\u0122\u0123\7\16\2\2\u0123\u0125\5,\27\2\u0124\u0122\3\2\2\2\u0125\u0128"+
		"\3\2\2\2\u0126\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127+\3\2\2\2\u0128"+
		"\u0126\3\2\2\2\u0129\u012a\7<\2\2\u012a\u012c\7\r\2\2\u012b\u0129\3\2"+
		"\2\2\u012b\u012c\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u012e\5\60\31\2\u012e"+
		"-\3\2\2\2\u012f\u0134\5\60\31\2\u0130\u0131\7\16\2\2\u0131\u0133\5\60"+
		"\31\2\u0132\u0130\3\2\2\2\u0133\u0136\3\2\2\2\u0134\u0132\3\2\2\2\u0134"+
		"\u0135\3\2\2\2\u0135/\3\2\2\2\u0136\u0134\3\2\2\2\u0137\u0138\b\31\1\2"+
		"\u0138\u0151\5^\60\2\u0139\u0151\5\\/\2\u013a\u0151\5`\61\2\u013b\u0151"+
		"\5\66\34\2\u013c\u0151\58\35\2\u013d\u0151\5D#\2\u013e\u013f\5X-\2\u013f"+
		"\u0140\5\60\31\f\u0140\u0151\3\2\2\2\u0141\u0151\7\24\2\2\u0142\u0151"+
		"\5\64\33\2\u0143\u014c\7\23\2\2\u0144\u0149\7<\2\2\u0145\u0146\7\16\2"+
		"\2\u0146\u0148\7<\2\2\u0147\u0145\3\2\2\2\u0148\u014b\3\2\2\2\u0149\u0147"+
		"\3\2\2\2\u0149\u014a\3\2\2\2\u014a\u014d\3\2\2\2\u014b\u0149\3\2\2\2\u014c"+
		"\u0144\3\2\2\2\u014c\u014d\3\2\2\2\u014d\u014e\3\2\2\2\u014e\u014f\7\25"+
		"\2\2\u014f\u0151\5\60\31\3\u0150\u0137\3\2\2\2\u0150\u0139\3\2\2\2\u0150"+
		"\u013a\3\2\2\2\u0150\u013b\3\2\2\2\u0150\u013c\3\2\2\2\u0150\u013d\3\2"+
		"\2\2\u0150\u013e\3\2\2\2\u0150\u0141\3\2\2\2\u0150\u0142\3\2\2\2\u0150"+
		"\u0143\3\2\2\2\u0151\u0170\3\2\2\2\u0152\u0153\f\r\2\2\u0153\u0154\5Z"+
		".\2\u0154\u0155\5\60\31\r\u0155\u016f\3\2\2\2\u0156\u0157\f\13\2\2\u0157"+
		"\u0158\5T+\2\u0158\u0159\5\60\31\f\u0159\u016f\3\2\2\2\u015a\u015b\f\n"+
		"\2\2\u015b\u015c\5R*\2\u015c\u015d\5\60\31\13\u015d\u016f\3\2\2\2\u015e"+
		"\u015f\f\t\2\2\u015f\u0160\5P)\2\u0160\u0161\5\60\31\n\u0161\u016f\3\2"+
		"\2\2\u0162\u0163\f\b\2\2\u0163\u0164\5L\'\2\u0164\u0165\5\60\31\t\u0165"+
		"\u016f\3\2\2\2\u0166\u0167\f\7\2\2\u0167\u0168\5J&\2\u0168\u0169\5\60"+
		"\31\b\u0169\u016f\3\2\2\2\u016a\u016b\f\6\2\2\u016b\u016c\5V,\2\u016c"+
		"\u016d\5\60\31\7\u016d\u016f\3\2\2\2\u016e\u0152\3\2\2\2\u016e\u0156\3"+
		"\2\2\2\u016e\u015a\3\2\2\2\u016e\u015e\3\2\2\2\u016e\u0162\3\2\2\2\u016e"+
		"\u0166\3\2\2\2\u016e\u016a\3\2\2\2\u016f\u0172\3\2\2\2\u0170\u016e\3\2"+
		"\2\2\u0170\u0171\3\2\2\2\u0171\61\3\2\2\2\u0172\u0170\3\2\2\2\u0173\u0175"+
		"\7F\2\2\u0174\u0173\3\2\2\2\u0175\u0178\3\2\2\2\u0176\u0174\3\2\2\2\u0176"+
		"\u0177\3\2\2\2\u0177\u017f\3\2\2\2\u0178\u0176\3\2\2\2\u0179\u017a\7\26"+
		"\2\2\u017a\u017b\5\60\31\2\u017b\u017c\7\27\2\2\u017c\u0180\3\2\2\2\u017d"+
		"\u0180\7<\2\2\u017e\u0180\5^\60\2\u017f\u0179\3\2\2\2\u017f\u017d\3\2"+
		"\2\2\u017f\u017e\3\2\2\2\u0180\u0181\3\2\2\2\u0181\u0182\7\30\2\2\u0182"+
		"\u0186\5\60\31\2\u0183\u0185\7F\2\2\u0184\u0183\3\2\2\2\u0185\u0188\3"+
		"\2\2\2\u0186\u0184\3\2\2\2\u0186\u0187\3\2\2\2\u0187\63\3\2\2\2\u0188"+
		"\u0186\3\2\2\2\u0189\u018d\7\31\2\2\u018a\u018c\7F\2\2\u018b\u018a\3\2"+
		"\2\2\u018c\u018f\3\2\2\2\u018d\u018b\3\2\2\2\u018d\u018e\3\2\2\2\u018e"+
		"\u0198\3\2\2\2\u018f\u018d\3\2\2\2\u0190\u0195\5\62\32\2\u0191\u0192\7"+
		"\16\2\2\u0192\u0194\5\62\32\2\u0193\u0191\3\2\2\2\u0194\u0197\3\2\2\2"+
		"\u0195\u0193\3\2\2\2\u0195\u0196\3\2\2\2\u0196\u0199\3\2\2\2\u0197\u0195"+
		"\3\2\2\2\u0198\u0190\3\2\2\2\u0198\u0199\3\2\2\2\u0199\u019d\3\2\2\2\u019a"+
		"\u019c\7F\2\2\u019b\u019a\3\2\2\2\u019c\u019f\3\2\2\2\u019d\u019b\3\2"+
		"\2\2\u019d\u019e\3\2\2\2\u019e\u01a0\3\2\2\2\u019f\u019d\3\2\2\2\u01a0"+
		"\u01a1\7\32\2\2\u01a1\65\3\2\2\2\u01a2\u01a6\5:\36\2\u01a3\u01a5\5@!\2"+
		"\u01a4\u01a3\3\2\2\2\u01a5\u01a8\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a6\u01a7"+
		"\3\2\2\2\u01a7\67\3\2\2\2\u01a8\u01a6\3\2\2\2\u01a9\u01ab\5:\36\2\u01aa"+
		"\u01ac\5@!\2\u01ab\u01aa\3\2\2\2\u01ac\u01ad\3\2\2\2\u01ad\u01ab\3\2\2"+
		"\2\u01ad\u01ae\3\2\2\2\u01ae9\3\2\2\2\u01af\u01b5\5<\37\2\u01b0\u01b1"+
		"\7\33\2\2\u01b1\u01b2\5\60\31\2\u01b2\u01b3\7\34\2\2\u01b3\u01b5\3\2\2"+
		"\2\u01b4\u01af\3\2\2\2\u01b4\u01b0\3\2\2\2\u01b5;\3\2\2\2\u01b6\u01bd"+
		"\7<\2\2\u01b7\u01b8\7\33\2\2\u01b8\u01b9\5\60\31\2\u01b9\u01ba\7\34\2"+
		"\2\u01ba\u01bb\5> \2\u01bb\u01bd\3\2\2\2\u01bc\u01b6\3\2\2\2\u01bc\u01b7"+
		"\3\2\2\2\u01bd\u01c1\3\2\2\2\u01be\u01c0\5> \2\u01bf\u01be\3\2\2\2\u01c0"+
		"\u01c3\3\2\2\2\u01c1\u01bf\3\2\2\2\u01c1\u01c2\3\2\2\2\u01c2=\3\2\2\2"+
		"\u01c3\u01c1\3\2\2\2\u01c4\u01c6\5@!\2\u01c5\u01c4\3\2\2\2\u01c6\u01c9"+
		"\3\2\2\2\u01c7\u01c5\3\2\2\2\u01c7\u01c8\3\2\2\2\u01c8\u01d0\3\2\2\2\u01c9"+
		"\u01c7\3\2\2\2\u01ca\u01cb\7\26\2\2\u01cb\u01cc\5\60\31\2\u01cc\u01cd"+
		"\7\27\2\2\u01cd\u01d1\3\2\2\2\u01ce\u01cf\7\35\2\2\u01cf\u01d1\7<\2\2"+
		"\u01d0\u01ca\3\2\2\2\u01d0\u01ce\3\2\2\2\u01d1?\3\2\2\2\u01d2\u01d4\7"+
		"\33\2\2\u01d3\u01d5\5*\26\2\u01d4\u01d3\3\2\2\2\u01d4\u01d5\3\2\2\2\u01d5"+
		"\u01d6\3\2\2\2\u01d6\u01d7\7\34\2\2\u01d7A\3\2\2\2\u01d8\u01da\7\33\2"+
		"\2\u01d9\u01db\5&\24\2\u01da\u01d9\3\2\2\2\u01da\u01db\3\2\2\2\u01db\u01dc"+
		"\3\2\2\2\u01dc\u01e0\7\34\2\2\u01dd\u01df\7F\2\2\u01de\u01dd\3\2\2\2\u01df"+
		"\u01e2\3\2\2\2\u01e0\u01de\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1\u01e3\3\2"+
		"\2\2\u01e2\u01e0\3\2\2\2\u01e3\u01e5\5\4\3\2\u01e4\u01e6\5\32\16\2\u01e5"+
		"\u01e4\3\2\2\2\u01e5\u01e6\3\2\2\2\u01e6\u01ea\3\2\2\2\u01e7\u01e9\7F"+
		"\2\2\u01e8\u01e7\3\2\2\2\u01e9\u01ec\3\2\2\2\u01ea\u01e8\3\2\2\2\u01ea"+
		"\u01eb\3\2\2\2\u01eb\u01ed\3\2\2\2\u01ec\u01ea\3\2\2\2\u01ed\u01ee\7\5"+
		"\2\2\u01eeC\3\2\2\2\u01ef\u01f1\7\26\2\2\u01f0\u01f2\5F$\2\u01f1\u01f0"+
		"\3\2\2\2\u01f1\u01f2\3\2\2\2\u01f2\u01f3\3\2\2\2\u01f3\u01f4\7\27\2\2"+
		"\u01f4E\3\2\2\2\u01f5\u01fa\5\60\31\2\u01f6\u01f7\7\16\2\2\u01f7\u01f9"+
		"\5\60\31\2\u01f8\u01f6\3\2\2\2\u01f9\u01fc\3\2\2\2\u01fa\u01f8\3\2\2\2"+
		"\u01fa\u01fb\3\2\2\2\u01fbG\3\2\2\2\u01fc\u01fa\3\2\2\2\u01fd\u01fe\7"+
		"\36\2\2\u01feI\3\2\2\2\u01ff\u0200\7\37\2\2\u0200K\3\2\2\2\u0201\u0202"+
		"\7 \2\2\u0202M\3\2\2\2\u0203\u0204\t\2\2\2\u0204O\3\2\2\2\u0205\u0206"+
		"\t\3\2\2\u0206Q\3\2\2\2\u0207\u0208\t\4\2\2\u0208S\3\2\2\2\u0209\u020a"+
		"\t\5\2\2\u020aU\3\2\2\2\u020b\u020c\t\6\2\2\u020cW\3\2\2\2\u020d\u020e"+
		"\t\7\2\2\u020eY\3\2\2\2\u020f\u0210\79\2\2\u0210[\3\2\2\2\u0211\u0212"+
		"\t\b\2\2\u0212]\3\2\2\2\u0213\u0214\t\t\2\2\u0214_\3\2\2\2\u0215\u0216"+
		"\t\n\2\2\u0216a\3\2\2\2\66elrw~\u0083\u0088\u0094\u009c\u00a3\u00a7\u00b1"+
		"\u00ba\u00c5\u00cf\u00de\u00e4\u00f2\u00fa\u00ff\u010e\u0119\u011f\u0126"+
		"\u012b\u0134\u0149\u014c\u0150\u016e\u0170\u0176\u017f\u0186\u018d\u0195"+
		"\u0198\u019d\u01a6\u01ad\u01b4\u01bc\u01c1\u01c7\u01d0\u01d4\u01da\u01e0"+
		"\u01e5\u01ea\u01f1\u01fa";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}