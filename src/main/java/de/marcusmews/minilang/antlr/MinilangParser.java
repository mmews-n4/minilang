// Generated from c:/git/minilang/src/grammar/Minilang.g4 by ANTLR 4.13.1
package de.marcusmews.minilang.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class MinilangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PLUS=1, MINUS=2, MULTIPLY=3, DIVIDE=4, POWER=5, ASSIGN=6, COMMA=7, LBRACE=8, 
		RBRACE=9, LPAREN=10, RPAREN=11, ARROW=12, VAR=13, OUT=14, PRINT=15, MAP=16, 
		REDUCE=17, NUMBER=18, IDENTIFIER=19, STRING=20, WS=21;
	public static final int
		RULE_program = 0, RULE_stmt = 1, RULE_varDecl = 2, RULE_outputStmt = 3, 
		RULE_printStmt = 4, RULE_expr = 5, RULE_sequence = 6, RULE_mapExpr = 7, 
		RULE_reduceExpr = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "stmt", "varDecl", "outputStmt", "printStmt", "expr", "sequence", 
			"mapExpr", "reduceExpr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'^'", "'='", "','", "'{'", "'}'", 
			"'('", "')'", "'->'", "'var'", "'out'", "'print'", "'map'", "'reduce'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "POWER", "ASSIGN", "COMMA", 
			"LBRACE", "RBRACE", "LPAREN", "RPAREN", "ARROW", "VAR", "OUT", "PRINT", 
			"MAP", "REDUCE", "NUMBER", "IDENTIFIER", "STRING", "WS"
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
	public String getGrammarFileName() { return "Minilang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MinilangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinilangVisitor ) return ((MinilangVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(19); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(18);
				stmt();
				}
				}
				setState(21); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 57344L) != 0) );
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

	@SuppressWarnings("CheckReturnValue")
	public static class StmtContext extends ParserRuleContext {
		public VarDeclContext varDecl() {
			return getRuleContext(VarDeclContext.class,0);
		}
		public OutputStmtContext outputStmt() {
			return getRuleContext(OutputStmtContext.class,0);
		}
		public PrintStmtContext printStmt() {
			return getRuleContext(PrintStmtContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).exitStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinilangVisitor ) return ((MinilangVisitor<? extends T>)visitor).visitStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stmt);
		try {
			setState(26);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VAR:
				enterOuterAlt(_localctx, 1);
				{
				setState(23);
				varDecl();
				}
				break;
			case OUT:
				enterOuterAlt(_localctx, 2);
				{
				setState(24);
				outputStmt();
				}
				break;
			case PRINT:
				enterOuterAlt(_localctx, 3);
				{
				setState(25);
				printStmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	@SuppressWarnings("CheckReturnValue")
	public static class VarDeclContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(MinilangParser.VAR, 0); }
		public TerminalNode IDENTIFIER() { return getToken(MinilangParser.IDENTIFIER, 0); }
		public TerminalNode ASSIGN() { return getToken(MinilangParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).enterVarDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).exitVarDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinilangVisitor ) return ((MinilangVisitor<? extends T>)visitor).visitVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_varDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			match(VAR);
			setState(29);
			match(IDENTIFIER);
			setState(30);
			match(ASSIGN);
			setState(31);
			expr(0);
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

	@SuppressWarnings("CheckReturnValue")
	public static class OutputStmtContext extends ParserRuleContext {
		public TerminalNode OUT() { return getToken(MinilangParser.OUT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public OutputStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_outputStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).enterOutputStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).exitOutputStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinilangVisitor ) return ((MinilangVisitor<? extends T>)visitor).visitOutputStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OutputStmtContext outputStmt() throws RecognitionException {
		OutputStmtContext _localctx = new OutputStmtContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_outputStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(OUT);
			setState(34);
			expr(0);
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

	@SuppressWarnings("CheckReturnValue")
	public static class PrintStmtContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(MinilangParser.PRINT, 0); }
		public TerminalNode STRING() { return getToken(MinilangParser.STRING, 0); }
		public PrintStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).enterPrintStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).exitPrintStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinilangVisitor ) return ((MinilangVisitor<? extends T>)visitor).visitPrintStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintStmtContext printStmt() throws RecognitionException {
		PrintStmtContext _localctx = new PrintStmtContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_printStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			match(PRINT);
			setState(37);
			match(STRING);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public Token op;
		public TerminalNode LPAREN() { return getToken(MinilangParser.LPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(MinilangParser.RPAREN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(MinilangParser.IDENTIFIER, 0); }
		public SequenceContext sequence() {
			return getRuleContext(SequenceContext.class,0);
		}
		public TerminalNode NUMBER() { return getToken(MinilangParser.NUMBER, 0); }
		public MapExprContext mapExpr() {
			return getRuleContext(MapExprContext.class,0);
		}
		public ReduceExprContext reduceExpr() {
			return getRuleContext(ReduceExprContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(MinilangParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MinilangParser.MINUS, 0); }
		public TerminalNode MULTIPLY() { return getToken(MinilangParser.MULTIPLY, 0); }
		public TerminalNode DIVIDE() { return getToken(MinilangParser.DIVIDE, 0); }
		public TerminalNode POWER() { return getToken(MinilangParser.POWER, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinilangVisitor ) return ((MinilangVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				setState(40);
				match(LPAREN);
				setState(41);
				expr(0);
				setState(42);
				match(RPAREN);
				}
				break;
			case IDENTIFIER:
				{
				setState(44);
				match(IDENTIFIER);
				}
				break;
			case LBRACE:
				{
				setState(45);
				sequence();
				}
				break;
			case NUMBER:
				{
				setState(46);
				match(NUMBER);
				}
				break;
			case MAP:
				{
				setState(47);
				mapExpr();
				}
				break;
			case REDUCE:
				{
				setState(48);
				reduceExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(68);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(66);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(51);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(52);
						((ExprContext)_localctx).op = match(PLUS);
						setState(53);
						expr(12);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(54);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(55);
						((ExprContext)_localctx).op = match(MINUS);
						setState(56);
						expr(11);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(57);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(58);
						((ExprContext)_localctx).op = match(MULTIPLY);
						setState(59);
						expr(10);
						}
						break;
					case 4:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(60);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(61);
						((ExprContext)_localctx).op = match(DIVIDE);
						setState(62);
						expr(9);
						}
						break;
					case 5:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(63);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(64);
						((ExprContext)_localctx).op = match(POWER);
						setState(65);
						expr(8);
						}
						break;
					}
					} 
				}
				setState(70);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class SequenceContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(MinilangParser.LBRACE, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(MinilangParser.COMMA, 0); }
		public TerminalNode RBRACE() { return getToken(MinilangParser.RBRACE, 0); }
		public SequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sequence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).enterSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).exitSequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinilangVisitor ) return ((MinilangVisitor<? extends T>)visitor).visitSequence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SequenceContext sequence() throws RecognitionException {
		SequenceContext _localctx = new SequenceContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_sequence);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(LBRACE);
			setState(72);
			expr(0);
			setState(73);
			match(COMMA);
			setState(74);
			expr(0);
			setState(75);
			match(RBRACE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class MapExprContext extends ParserRuleContext {
		public TerminalNode MAP() { return getToken(MinilangParser.MAP, 0); }
		public TerminalNode LPAREN() { return getToken(MinilangParser.LPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(MinilangParser.COMMA, 0); }
		public TerminalNode IDENTIFIER() { return getToken(MinilangParser.IDENTIFIER, 0); }
		public TerminalNode ARROW() { return getToken(MinilangParser.ARROW, 0); }
		public TerminalNode RPAREN() { return getToken(MinilangParser.RPAREN, 0); }
		public MapExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).enterMapExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).exitMapExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinilangVisitor ) return ((MinilangVisitor<? extends T>)visitor).visitMapExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapExprContext mapExpr() throws RecognitionException {
		MapExprContext _localctx = new MapExprContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_mapExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(MAP);
			setState(78);
			match(LPAREN);
			setState(79);
			expr(0);
			setState(80);
			match(COMMA);
			setState(81);
			match(IDENTIFIER);
			setState(82);
			match(ARROW);
			setState(83);
			expr(0);
			setState(84);
			match(RPAREN);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ReduceExprContext extends ParserRuleContext {
		public TerminalNode REDUCE() { return getToken(MinilangParser.REDUCE, 0); }
		public TerminalNode LPAREN() { return getToken(MinilangParser.LPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MinilangParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MinilangParser.COMMA, i);
		}
		public List<TerminalNode> IDENTIFIER() { return getTokens(MinilangParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(MinilangParser.IDENTIFIER, i);
		}
		public TerminalNode ARROW() { return getToken(MinilangParser.ARROW, 0); }
		public TerminalNode RPAREN() { return getToken(MinilangParser.RPAREN, 0); }
		public ReduceExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reduceExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).enterReduceExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinilangListener ) ((MinilangListener)listener).exitReduceExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinilangVisitor ) return ((MinilangVisitor<? extends T>)visitor).visitReduceExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReduceExprContext reduceExpr() throws RecognitionException {
		ReduceExprContext _localctx = new ReduceExprContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_reduceExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			match(REDUCE);
			setState(87);
			match(LPAREN);
			setState(88);
			expr(0);
			setState(89);
			match(COMMA);
			setState(90);
			expr(0);
			setState(91);
			match(COMMA);
			setState(92);
			match(IDENTIFIER);
			setState(93);
			match(IDENTIFIER);
			setState(94);
			match(ARROW);
			setState(95);
			expr(0);
			setState(96);
			match(RPAREN);
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
		case 5:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 11);
		case 1:
			return precpred(_ctx, 10);
		case 2:
			return precpred(_ctx, 9);
		case 3:
			return precpred(_ctx, 8);
		case 4:
			return precpred(_ctx, 7);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0015c\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0001\u0000\u0004\u0000\u0014\b\u0000\u000b\u0000\f\u0000\u0015"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u001b\b\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0003\u00052\b\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0005\u0005C\b\u0005\n\u0005\f\u0005F\t\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0000\u0001"+
		"\n\t\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0000\u0000f\u0000\u0013"+
		"\u0001\u0000\u0000\u0000\u0002\u001a\u0001\u0000\u0000\u0000\u0004\u001c"+
		"\u0001\u0000\u0000\u0000\u0006!\u0001\u0000\u0000\u0000\b$\u0001\u0000"+
		"\u0000\u0000\n1\u0001\u0000\u0000\u0000\fG\u0001\u0000\u0000\u0000\u000e"+
		"M\u0001\u0000\u0000\u0000\u0010V\u0001\u0000\u0000\u0000\u0012\u0014\u0003"+
		"\u0002\u0001\u0000\u0013\u0012\u0001\u0000\u0000\u0000\u0014\u0015\u0001"+
		"\u0000\u0000\u0000\u0015\u0013\u0001\u0000\u0000\u0000\u0015\u0016\u0001"+
		"\u0000\u0000\u0000\u0016\u0001\u0001\u0000\u0000\u0000\u0017\u001b\u0003"+
		"\u0004\u0002\u0000\u0018\u001b\u0003\u0006\u0003\u0000\u0019\u001b\u0003"+
		"\b\u0004\u0000\u001a\u0017\u0001\u0000\u0000\u0000\u001a\u0018\u0001\u0000"+
		"\u0000\u0000\u001a\u0019\u0001\u0000\u0000\u0000\u001b\u0003\u0001\u0000"+
		"\u0000\u0000\u001c\u001d\u0005\r\u0000\u0000\u001d\u001e\u0005\u0013\u0000"+
		"\u0000\u001e\u001f\u0005\u0006\u0000\u0000\u001f \u0003\n\u0005\u0000"+
		" \u0005\u0001\u0000\u0000\u0000!\"\u0005\u000e\u0000\u0000\"#\u0003\n"+
		"\u0005\u0000#\u0007\u0001\u0000\u0000\u0000$%\u0005\u000f\u0000\u0000"+
		"%&\u0005\u0014\u0000\u0000&\t\u0001\u0000\u0000\u0000\'(\u0006\u0005\uffff"+
		"\uffff\u0000()\u0005\n\u0000\u0000)*\u0003\n\u0005\u0000*+\u0005\u000b"+
		"\u0000\u0000+2\u0001\u0000\u0000\u0000,2\u0005\u0013\u0000\u0000-2\u0003"+
		"\f\u0006\u0000.2\u0005\u0012\u0000\u0000/2\u0003\u000e\u0007\u000002\u0003"+
		"\u0010\b\u00001\'\u0001\u0000\u0000\u00001,\u0001\u0000\u0000\u00001-"+
		"\u0001\u0000\u0000\u00001.\u0001\u0000\u0000\u00001/\u0001\u0000\u0000"+
		"\u000010\u0001\u0000\u0000\u00002D\u0001\u0000\u0000\u000034\n\u000b\u0000"+
		"\u000045\u0005\u0001\u0000\u00005C\u0003\n\u0005\f67\n\n\u0000\u00007"+
		"8\u0005\u0002\u0000\u00008C\u0003\n\u0005\u000b9:\n\t\u0000\u0000:;\u0005"+
		"\u0003\u0000\u0000;C\u0003\n\u0005\n<=\n\b\u0000\u0000=>\u0005\u0004\u0000"+
		"\u0000>C\u0003\n\u0005\t?@\n\u0007\u0000\u0000@A\u0005\u0005\u0000\u0000"+
		"AC\u0003\n\u0005\bB3\u0001\u0000\u0000\u0000B6\u0001\u0000\u0000\u0000"+
		"B9\u0001\u0000\u0000\u0000B<\u0001\u0000\u0000\u0000B?\u0001\u0000\u0000"+
		"\u0000CF\u0001\u0000\u0000\u0000DB\u0001\u0000\u0000\u0000DE\u0001\u0000"+
		"\u0000\u0000E\u000b\u0001\u0000\u0000\u0000FD\u0001\u0000\u0000\u0000"+
		"GH\u0005\b\u0000\u0000HI\u0003\n\u0005\u0000IJ\u0005\u0007\u0000\u0000"+
		"JK\u0003\n\u0005\u0000KL\u0005\t\u0000\u0000L\r\u0001\u0000\u0000\u0000"+
		"MN\u0005\u0010\u0000\u0000NO\u0005\n\u0000\u0000OP\u0003\n\u0005\u0000"+
		"PQ\u0005\u0007\u0000\u0000QR\u0005\u0013\u0000\u0000RS\u0005\f\u0000\u0000"+
		"ST\u0003\n\u0005\u0000TU\u0005\u000b\u0000\u0000U\u000f\u0001\u0000\u0000"+
		"\u0000VW\u0005\u0011\u0000\u0000WX\u0005\n\u0000\u0000XY\u0003\n\u0005"+
		"\u0000YZ\u0005\u0007\u0000\u0000Z[\u0003\n\u0005\u0000[\\\u0005\u0007"+
		"\u0000\u0000\\]\u0005\u0013\u0000\u0000]^\u0005\u0013\u0000\u0000^_\u0005"+
		"\f\u0000\u0000_`\u0003\n\u0005\u0000`a\u0005\u000b\u0000\u0000a\u0011"+
		"\u0001\u0000\u0000\u0000\u0005\u0015\u001a1BD";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}