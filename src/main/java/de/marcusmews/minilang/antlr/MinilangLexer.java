// Generated from c:/git/minilang/src/grammar/Minilang.g4 by ANTLR 4.13.1
package de.marcusmews.minilang.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class MinilangLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PLUS=1, MINUS=2, MULTIPLY=3, DIVIDE=4, POWER=5, ASSIGN=6, COMMA=7, LBRACE=8, 
		RBRACE=9, LPAREN=10, RPAREN=11, ARROW=12, VAR=13, OUT=14, PRINT=15, MAP=16, 
		REDUCE=17, NUMBER=18, IDENTIFIER=19, STRING=20, WS=21;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"PLUS", "MINUS", "MULTIPLY", "DIVIDE", "POWER", "ASSIGN", "COMMA", "LBRACE", 
			"RBRACE", "LPAREN", "RPAREN", "ARROW", "VAR", "OUT", "PRINT", "MAP", 
			"REDUCE", "NUMBER", "IDENTIFIER", "STRING", "WS"
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


	public MinilangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Minilang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0015\u0084\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0003\u0011_\b"+
		"\u0011\u0001\u0011\u0004\u0011b\b\u0011\u000b\u0011\f\u0011c\u0001\u0011"+
		"\u0001\u0011\u0004\u0011h\b\u0011\u000b\u0011\f\u0011i\u0003\u0011l\b"+
		"\u0011\u0001\u0012\u0001\u0012\u0005\u0012p\b\u0012\n\u0012\f\u0012s\t"+
		"\u0012\u0001\u0013\u0001\u0013\u0005\u0013w\b\u0013\n\u0013\f\u0013z\t"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0004\u0014\u007f\b\u0014\u000b"+
		"\u0014\f\u0014\u0080\u0001\u0014\u0001\u0014\u0000\u0000\u0015\u0001\u0001"+
		"\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f"+
		"\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f"+
		"\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015\u0001\u0000\u0005\u0001"+
		"\u000009\u0003\u0000AZ__az\u0004\u000009AZ__az\u0001\u0000\"\"\u0003\u0000"+
		"\t\n\r\r  \u008a\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001"+
		"\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001"+
		"\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000"+
		"\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000"+
		"\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000"+
		"\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000"+
		"\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000"+
		"\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000"+
		"\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000"+
		"%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001"+
		"\u0000\u0000\u0000\u0001+\u0001\u0000\u0000\u0000\u0003-\u0001\u0000\u0000"+
		"\u0000\u0005/\u0001\u0000\u0000\u0000\u00071\u0001\u0000\u0000\u0000\t"+
		"3\u0001\u0000\u0000\u0000\u000b5\u0001\u0000\u0000\u0000\r7\u0001\u0000"+
		"\u0000\u0000\u000f9\u0001\u0000\u0000\u0000\u0011;\u0001\u0000\u0000\u0000"+
		"\u0013=\u0001\u0000\u0000\u0000\u0015?\u0001\u0000\u0000\u0000\u0017A"+
		"\u0001\u0000\u0000\u0000\u0019D\u0001\u0000\u0000\u0000\u001bH\u0001\u0000"+
		"\u0000\u0000\u001dL\u0001\u0000\u0000\u0000\u001fR\u0001\u0000\u0000\u0000"+
		"!V\u0001\u0000\u0000\u0000#^\u0001\u0000\u0000\u0000%m\u0001\u0000\u0000"+
		"\u0000\'t\u0001\u0000\u0000\u0000)~\u0001\u0000\u0000\u0000+,\u0005+\u0000"+
		"\u0000,\u0002\u0001\u0000\u0000\u0000-.\u0005-\u0000\u0000.\u0004\u0001"+
		"\u0000\u0000\u0000/0\u0005*\u0000\u00000\u0006\u0001\u0000\u0000\u0000"+
		"12\u0005/\u0000\u00002\b\u0001\u0000\u0000\u000034\u0005^\u0000\u0000"+
		"4\n\u0001\u0000\u0000\u000056\u0005=\u0000\u00006\f\u0001\u0000\u0000"+
		"\u000078\u0005,\u0000\u00008\u000e\u0001\u0000\u0000\u00009:\u0005{\u0000"+
		"\u0000:\u0010\u0001\u0000\u0000\u0000;<\u0005}\u0000\u0000<\u0012\u0001"+
		"\u0000\u0000\u0000=>\u0005(\u0000\u0000>\u0014\u0001\u0000\u0000\u0000"+
		"?@\u0005)\u0000\u0000@\u0016\u0001\u0000\u0000\u0000AB\u0005-\u0000\u0000"+
		"BC\u0005>\u0000\u0000C\u0018\u0001\u0000\u0000\u0000DE\u0005v\u0000\u0000"+
		"EF\u0005a\u0000\u0000FG\u0005r\u0000\u0000G\u001a\u0001\u0000\u0000\u0000"+
		"HI\u0005o\u0000\u0000IJ\u0005u\u0000\u0000JK\u0005t\u0000\u0000K\u001c"+
		"\u0001\u0000\u0000\u0000LM\u0005p\u0000\u0000MN\u0005r\u0000\u0000NO\u0005"+
		"i\u0000\u0000OP\u0005n\u0000\u0000PQ\u0005t\u0000\u0000Q\u001e\u0001\u0000"+
		"\u0000\u0000RS\u0005m\u0000\u0000ST\u0005a\u0000\u0000TU\u0005p\u0000"+
		"\u0000U \u0001\u0000\u0000\u0000VW\u0005r\u0000\u0000WX\u0005e\u0000\u0000"+
		"XY\u0005d\u0000\u0000YZ\u0005u\u0000\u0000Z[\u0005c\u0000\u0000[\\\u0005"+
		"e\u0000\u0000\\\"\u0001\u0000\u0000\u0000]_\u0005-\u0000\u0000^]\u0001"+
		"\u0000\u0000\u0000^_\u0001\u0000\u0000\u0000_a\u0001\u0000\u0000\u0000"+
		"`b\u0007\u0000\u0000\u0000a`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000"+
		"\u0000ca\u0001\u0000\u0000\u0000cd\u0001\u0000\u0000\u0000dk\u0001\u0000"+
		"\u0000\u0000eg\u0005.\u0000\u0000fh\u0007\u0000\u0000\u0000gf\u0001\u0000"+
		"\u0000\u0000hi\u0001\u0000\u0000\u0000ig\u0001\u0000\u0000\u0000ij\u0001"+
		"\u0000\u0000\u0000jl\u0001\u0000\u0000\u0000ke\u0001\u0000\u0000\u0000"+
		"kl\u0001\u0000\u0000\u0000l$\u0001\u0000\u0000\u0000mq\u0007\u0001\u0000"+
		"\u0000np\u0007\u0002\u0000\u0000on\u0001\u0000\u0000\u0000ps\u0001\u0000"+
		"\u0000\u0000qo\u0001\u0000\u0000\u0000qr\u0001\u0000\u0000\u0000r&\u0001"+
		"\u0000\u0000\u0000sq\u0001\u0000\u0000\u0000tx\u0005\"\u0000\u0000uw\b"+
		"\u0003\u0000\u0000vu\u0001\u0000\u0000\u0000wz\u0001\u0000\u0000\u0000"+
		"xv\u0001\u0000\u0000\u0000xy\u0001\u0000\u0000\u0000y{\u0001\u0000\u0000"+
		"\u0000zx\u0001\u0000\u0000\u0000{|\u0005\"\u0000\u0000|(\u0001\u0000\u0000"+
		"\u0000}\u007f\u0007\u0004\u0000\u0000~}\u0001\u0000\u0000\u0000\u007f"+
		"\u0080\u0001\u0000\u0000\u0000\u0080~\u0001\u0000\u0000\u0000\u0080\u0081"+
		"\u0001\u0000\u0000\u0000\u0081\u0082\u0001\u0000\u0000\u0000\u0082\u0083"+
		"\u0006\u0014\u0000\u0000\u0083*\u0001\u0000\u0000\u0000\b\u0000^cikqx"+
		"\u0080\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}