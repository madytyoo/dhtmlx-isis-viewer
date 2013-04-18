// Generated from /home/maurizio/IdeaProjects/dhtmlx-isis-viewer/dhtmlx.g4 by ANTLR 4.0

    package com.mylaensys.isis.dsl.dhtmlx;
    import  java.util.*;


import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class dhtmlxLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__43=1, T__42=2, T__41=3, T__40=4, T__39=5, T__38=6, T__37=7, T__36=8, 
		T__35=9, T__34=10, T__33=11, T__32=12, T__31=13, T__30=14, T__29=15, T__28=16, 
		T__27=17, T__26=18, T__25=19, T__24=20, T__23=21, T__22=22, T__21=23, 
		T__20=24, T__19=25, T__18=26, T__17=27, T__16=28, T__15=29, T__14=30, 
		T__13=31, T__12=32, T__11=33, T__10=34, T__9=35, T__8=36, T__7=37, T__6=38, 
		T__5=39, T__4=40, T__3=41, T__2=42, T__1=43, T__0=44, WS=45, LBRACKET=46, 
		RBRACKET=47, LPAREN=48, RPAREN=49, INTEGER=50, SEMI=51, ROW=52, COL=53, 
		DOT=54, LINK=55, LT=56, GT=57, SEPARATOR=58, SPACER=59, SETTINGS=60, HEX=61, 
		ESCAPE=62, UNI=63, LETTER=64, DIGIT=65, STRING=66, COMMENT=67, ID=68;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'1..N'", "'b'", "'left'", "'self'", "'3E'", "'inputHeight'", "'inputWidth'", 
		"'id'", "'''", "'{'", "'@Top'", "'2U'", "'center'", "'3U'", "'label-top'", 
		"'size'", "'labelAlign'", "'3W'", "'}'", "'label-right'", "'labelWidth'", 
		"'auto'", "'bottom'", "'a'", "'active'", "'c'", "'prompt'", "'height'", 
		"'@Bottom'", "'3T'", "'labelHeight'", "'2E'", "'3L'", "'top'", "'right'", 
		"'position'", "'1C'", "'3J'", "'label-left'", "'text'", "'width'", "'Tab'", 
		"'..'", "'mode'", "WS", "'['", "']'", "'('", "')'", "INTEGER", "':'", 
		"';'", "','", "'.'", "'->'", "'<'", "'>'", "'|'", "'||'", "'default'", 
		"HEX", "ESCAPE", "UNI", "LETTER", "DIGIT", "STRING", "COMMENT", "ID"
	};
	public static final String[] ruleNames = {
		"T__43", "T__42", "T__41", "T__40", "T__39", "T__38", "T__37", "T__36", 
		"T__35", "T__34", "T__33", "T__32", "T__31", "T__30", "T__29", "T__28", 
		"T__27", "T__26", "T__25", "T__24", "T__23", "T__22", "T__21", "T__20", 
		"T__19", "T__18", "T__17", "T__16", "T__15", "T__14", "T__13", "T__12", 
		"T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", 
		"T__2", "T__1", "T__0", "WS", "LBRACKET", "RBRACKET", "LPAREN", "RPAREN", 
		"INTEGER", "SEMI", "ROW", "COL", "DOT", "LINK", "LT", "GT", "SEPARATOR", 
		"SPACER", "SETTINGS", "HEX", "ESCAPE", "UNI", "LETTER", "DIGIT", "STRING", 
		"COMMENT", "ID"
	};


	    ParserHelper helper;


	public dhtmlxLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "dhtmlx.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 44: WS_action((RuleContext)_localctx, actionIndex); break;

		case 66: COMMENT_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1: _channel = HIDDEN;  break;
		}
	}

	public static final String _serializedATN =
		"\2\4F\u01ec\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t"+
		"\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27"+
		"\t\27\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36"+
		"\t\36\4\37\t\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4"+
		"(\t(\4)\t)\4*\t*\4+\t+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62"+
		"\t\62\4\63\t\63\4\64\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4"+
		":\t:\4;\t;\4<\t<\4=\t=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\t"+
		"E\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5"+
		"\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3"+
		"\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17"+
		"\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21"+
		"\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27"+
		"\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3!\3!\3"+
		"!\3\"\3\"\3\"\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3%\3%\3"+
		"%\3&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3"+
		")\3*\3*\3*\3*\3*\3*\3+\3+\3+\3+\3,\3,\3,\3-\3-\3-\3-\3-\3.\6.\u0184\n"+
		".\r.\16.\u0185\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\63"+
		"\7\63\u0195\n\63\f\63\16\63\u0198\13\63\5\63\u019a\n\63\3\64\3\64\3\65"+
		"\3\65\3\66\3\66\3\67\3\67\38\38\38\39\39\3:\3:\3;\3;\3<\3<\3<\3=\3=\3"+
		"=\3=\3=\3=\3=\3=\3>\3>\3?\3?\3?\5?\u01bd\n?\3@\3@\3@\3@\3@\3@\3@\3A\3"+
		"A\3B\3B\3C\3C\3C\7C\u01cd\nC\fC\16C\u01d0\13C\3C\3C\3D\3D\3D\3D\7D\u01d8"+
		"\nD\fD\16D\u01db\13D\3D\3D\3D\3D\3D\3E\5E\u01e3\nE\3E\3E\3E\7E\u01e8\n"+
		"E\fE\16E\u01eb\13E\3\u01d9F\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t"+
		"\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1"+
		"#\23\1%\24\1\'\25\1)\26\1+\27\1-\30\1/\31\1\61\32\1\63\33\1\65\34\1\67"+
		"\35\19\36\1;\37\1= \1?!\1A\"\1C#\1E$\1G%\1I&\1K\'\1M(\1O)\1Q*\1S+\1U,"+
		"\1W-\1Y.\1[/\2]\60\1_\61\1a\62\1c\63\1e\64\1g\65\1i\66\1k\67\1m8\1o9\1"+
		"q:\1s;\1u<\1w=\1y>\1{?\1}@\1\177A\1\u0081B\1\u0083C\1\u0085D\1\u0087E"+
		"\3\u0089F\1\3\2\7\5\13\f\17\17\"\"\5\62;CHch\n$$))^^ddhhppttvv\6&&C\\"+
		"aac|\4$$^^\u01f5\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2"+
		"!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3"+
		"\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2"+
		"\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E"+
		"\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2"+
		"\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2"+
		"\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k"+
		"\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2"+
		"\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2"+
		"\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\3\u008b"+
		"\3\2\2\2\5\u0090\3\2\2\2\7\u0092\3\2\2\2\t\u0097\3\2\2\2\13\u009c\3\2"+
		"\2\2\r\u009f\3\2\2\2\17\u00ab\3\2\2\2\21\u00b6\3\2\2\2\23\u00b9\3\2\2"+
		"\2\25\u00bb\3\2\2\2\27\u00bd\3\2\2\2\31\u00c2\3\2\2\2\33\u00c5\3\2\2\2"+
		"\35\u00cc\3\2\2\2\37\u00cf\3\2\2\2!\u00d9\3\2\2\2#\u00de\3\2\2\2%\u00e9"+
		"\3\2\2\2\'\u00ec\3\2\2\2)\u00ee\3\2\2\2+\u00fa\3\2\2\2-\u0105\3\2\2\2"+
		"/\u010a\3\2\2\2\61\u0111\3\2\2\2\63\u0113\3\2\2\2\65\u011a\3\2\2\2\67"+
		"\u011c\3\2\2\29\u0123\3\2\2\2;\u012a\3\2\2\2=\u0132\3\2\2\2?\u0135\3\2"+
		"\2\2A\u0141\3\2\2\2C\u0144\3\2\2\2E\u0147\3\2\2\2G\u014b\3\2\2\2I\u0151"+
		"\3\2\2\2K\u015a\3\2\2\2M\u015d\3\2\2\2O\u0160\3\2\2\2Q\u016b\3\2\2\2S"+
		"\u0170\3\2\2\2U\u0176\3\2\2\2W\u017a\3\2\2\2Y\u017d\3\2\2\2[\u0183\3\2"+
		"\2\2]\u0189\3\2\2\2_\u018b\3\2\2\2a\u018d\3\2\2\2c\u018f\3\2\2\2e\u0199"+
		"\3\2\2\2g\u019b\3\2\2\2i\u019d\3\2\2\2k\u019f\3\2\2\2m\u01a1\3\2\2\2o"+
		"\u01a3\3\2\2\2q\u01a6\3\2\2\2s\u01a8\3\2\2\2u\u01aa\3\2\2\2w\u01ac\3\2"+
		"\2\2y\u01af\3\2\2\2{\u01b7\3\2\2\2}\u01bc\3\2\2\2\177\u01be\3\2\2\2\u0081"+
		"\u01c5\3\2\2\2\u0083\u01c7\3\2\2\2\u0085\u01c9\3\2\2\2\u0087\u01d3\3\2"+
		"\2\2\u0089\u01e2\3\2\2\2\u008b\u008c\7\63\2\2\u008c\u008d\7\60\2\2\u008d"+
		"\u008e\7\60\2\2\u008e\u008f\7P\2\2\u008f\4\3\2\2\2\u0090\u0091\7d\2\2"+
		"\u0091\6\3\2\2\2\u0092\u0093\7n\2\2\u0093\u0094\7g\2\2\u0094\u0095\7h"+
		"\2\2\u0095\u0096\7v\2\2\u0096\b\3\2\2\2\u0097\u0098\7u\2\2\u0098\u0099"+
		"\7g\2\2\u0099\u009a\7n\2\2\u009a\u009b\7h\2\2\u009b\n\3\2\2\2\u009c\u009d"+
		"\7\65\2\2\u009d\u009e\7G\2\2\u009e\f\3\2\2\2\u009f\u00a0\7k\2\2\u00a0"+
		"\u00a1\7p\2\2\u00a1\u00a2\7r\2\2\u00a2\u00a3\7w\2\2\u00a3\u00a4\7v\2\2"+
		"\u00a4\u00a5\7J\2\2\u00a5\u00a6\7g\2\2\u00a6\u00a7\7k\2\2\u00a7\u00a8"+
		"\7i\2\2\u00a8\u00a9\7j\2\2\u00a9\u00aa\7v\2\2\u00aa\16\3\2\2\2\u00ab\u00ac"+
		"\7k\2\2\u00ac\u00ad\7p\2\2\u00ad\u00ae\7r\2\2\u00ae\u00af\7w\2\2\u00af"+
		"\u00b0\7v\2\2\u00b0\u00b1\7Y\2\2\u00b1\u00b2\7k\2\2\u00b2\u00b3\7f\2\2"+
		"\u00b3\u00b4\7v\2\2\u00b4\u00b5\7j\2\2\u00b5\20\3\2\2\2\u00b6\u00b7\7"+
		"k\2\2\u00b7\u00b8\7f\2\2\u00b8\22\3\2\2\2\u00b9\u00ba\7)\2\2\u00ba\24"+
		"\3\2\2\2\u00bb\u00bc\7}\2\2\u00bc\26\3\2\2\2\u00bd\u00be\7B\2\2\u00be"+
		"\u00bf\7V\2\2\u00bf\u00c0\7q\2\2\u00c0\u00c1\7r\2\2\u00c1\30\3\2\2\2\u00c2"+
		"\u00c3\7\64\2\2\u00c3\u00c4\7W\2\2\u00c4\32\3\2\2\2\u00c5\u00c6\7e\2\2"+
		"\u00c6\u00c7\7g\2\2\u00c7\u00c8\7p\2\2\u00c8\u00c9\7v\2\2\u00c9\u00ca"+
		"\7g\2\2\u00ca\u00cb\7t\2\2\u00cb\34\3\2\2\2\u00cc\u00cd\7\65\2\2\u00cd"+
		"\u00ce\7W\2\2\u00ce\36\3\2\2\2\u00cf\u00d0\7n\2\2\u00d0\u00d1\7c\2\2\u00d1"+
		"\u00d2\7d\2\2\u00d2\u00d3\7g\2\2\u00d3\u00d4\7n\2\2\u00d4\u00d5\7/\2\2"+
		"\u00d5\u00d6\7v\2\2\u00d6\u00d7\7q\2\2\u00d7\u00d8\7r\2\2\u00d8 \3\2\2"+
		"\2\u00d9\u00da\7u\2\2\u00da\u00db\7k\2\2\u00db\u00dc\7|\2\2\u00dc\u00dd"+
		"\7g\2\2\u00dd\"\3\2\2\2\u00de\u00df\7n\2\2\u00df\u00e0\7c\2\2\u00e0\u00e1"+
		"\7d\2\2\u00e1\u00e2\7g\2\2\u00e2\u00e3\7n\2\2\u00e3\u00e4\7C\2\2\u00e4"+
		"\u00e5\7n\2\2\u00e5\u00e6\7k\2\2\u00e6\u00e7\7i\2\2\u00e7\u00e8\7p\2\2"+
		"\u00e8$\3\2\2\2\u00e9\u00ea\7\65\2\2\u00ea\u00eb\7Y\2\2\u00eb&\3\2\2\2"+
		"\u00ec\u00ed\7\177\2\2\u00ed(\3\2\2\2\u00ee\u00ef\7n\2\2\u00ef\u00f0\7"+
		"c\2\2\u00f0\u00f1\7d\2\2\u00f1\u00f2\7g\2\2\u00f2\u00f3\7n\2\2\u00f3\u00f4"+
		"\7/\2\2\u00f4\u00f5\7t\2\2\u00f5\u00f6\7k\2\2\u00f6\u00f7\7i\2\2\u00f7"+
		"\u00f8\7j\2\2\u00f8\u00f9\7v\2\2\u00f9*\3\2\2\2\u00fa\u00fb\7n\2\2\u00fb"+
		"\u00fc\7c\2\2\u00fc\u00fd\7d\2\2\u00fd\u00fe\7g\2\2\u00fe\u00ff\7n\2\2"+
		"\u00ff\u0100\7Y\2\2\u0100\u0101\7k\2\2\u0101\u0102\7f\2\2\u0102\u0103"+
		"\7v\2\2\u0103\u0104\7j\2\2\u0104,\3\2\2\2\u0105\u0106\7c\2\2\u0106\u0107"+
		"\7w\2\2\u0107\u0108\7v\2\2\u0108\u0109\7q\2\2\u0109.\3\2\2\2\u010a\u010b"+
		"\7d\2\2\u010b\u010c\7q\2\2\u010c\u010d\7v\2\2\u010d\u010e\7v\2\2\u010e"+
		"\u010f\7q\2\2\u010f\u0110\7o\2\2\u0110\60\3\2\2\2\u0111\u0112\7c\2\2\u0112"+
		"\62\3\2\2\2\u0113\u0114\7c\2\2\u0114\u0115\7e\2\2\u0115\u0116\7v\2\2\u0116"+
		"\u0117\7k\2\2\u0117\u0118\7x\2\2\u0118\u0119\7g\2\2\u0119\64\3\2\2\2\u011a"+
		"\u011b\7e\2\2\u011b\66\3\2\2\2\u011c\u011d\7r\2\2\u011d\u011e\7t\2\2\u011e"+
		"\u011f\7q\2\2\u011f\u0120\7o\2\2\u0120\u0121\7r\2\2\u0121\u0122\7v\2\2"+
		"\u01228\3\2\2\2\u0123\u0124\7j\2\2\u0124\u0125\7g\2\2\u0125\u0126\7k\2"+
		"\2\u0126\u0127\7i\2\2\u0127\u0128\7j\2\2\u0128\u0129\7v\2\2\u0129:\3\2"+
		"\2\2\u012a\u012b\7B\2\2\u012b\u012c\7D\2\2\u012c\u012d\7q\2\2\u012d\u012e"+
		"\7v\2\2\u012e\u012f\7v\2\2\u012f\u0130\7q\2\2\u0130\u0131\7o\2\2\u0131"+
		"<\3\2\2\2\u0132\u0133\7\65\2\2\u0133\u0134\7V\2\2\u0134>\3\2\2\2\u0135"+
		"\u0136\7n\2\2\u0136\u0137\7c\2\2\u0137\u0138\7d\2\2\u0138\u0139\7g\2\2"+
		"\u0139\u013a\7n\2\2\u013a\u013b\7J\2\2\u013b\u013c\7g\2\2\u013c\u013d"+
		"\7k\2\2\u013d\u013e\7i\2\2\u013e\u013f\7j\2\2\u013f\u0140\7v\2\2\u0140"+
		"@\3\2\2\2\u0141\u0142\7\64\2\2\u0142\u0143\7G\2\2\u0143B\3\2\2\2\u0144"+
		"\u0145\7\65\2\2\u0145\u0146\7N\2\2\u0146D\3\2\2\2\u0147\u0148\7v\2\2\u0148"+
		"\u0149\7q\2\2\u0149\u014a\7r\2\2\u014aF\3\2\2\2\u014b\u014c\7t\2\2\u014c"+
		"\u014d\7k\2\2\u014d\u014e\7i\2\2\u014e\u014f\7j\2\2\u014f\u0150\7v\2\2"+
		"\u0150H\3\2\2\2\u0151\u0152\7r\2\2\u0152\u0153\7q\2\2\u0153\u0154\7u\2"+
		"\2\u0154\u0155\7k\2\2\u0155\u0156\7v\2\2\u0156\u0157\7k\2\2\u0157\u0158"+
		"\7q\2\2\u0158\u0159\7p\2\2\u0159J\3\2\2\2\u015a\u015b\7\63\2\2\u015b\u015c"+
		"\7E\2\2\u015cL\3\2\2\2\u015d\u015e\7\65\2\2\u015e\u015f\7L\2\2\u015fN"+
		"\3\2\2\2\u0160\u0161\7n\2\2\u0161\u0162\7c\2\2\u0162\u0163\7d\2\2\u0163"+
		"\u0164\7g\2\2\u0164\u0165\7n\2\2\u0165\u0166\7/\2\2\u0166\u0167\7n\2\2"+
		"\u0167\u0168\7g\2\2\u0168\u0169\7h\2\2\u0169\u016a\7v\2\2\u016aP\3\2\2"+
		"\2\u016b\u016c\7v\2\2\u016c\u016d\7g\2\2\u016d\u016e\7z\2\2\u016e\u016f"+
		"\7v\2\2\u016fR\3\2\2\2\u0170\u0171\7y\2\2\u0171\u0172\7k\2\2\u0172\u0173"+
		"\7f\2\2\u0173\u0174\7v\2\2\u0174\u0175\7j\2\2\u0175T\3\2\2\2\u0176\u0177"+
		"\7V\2\2\u0177\u0178\7c\2\2\u0178\u0179\7d\2\2\u0179V\3\2\2\2\u017a\u017b"+
		"\7\60\2\2\u017b\u017c\7\60\2\2\u017cX\3\2\2\2\u017d\u017e\7o\2\2\u017e"+
		"\u017f\7q\2\2\u017f\u0180\7f\2\2\u0180\u0181\7g\2\2\u0181Z\3\2\2\2\u0182"+
		"\u0184\t\2\2\2\u0183\u0182\3\2\2\2\u0184\u0185\3\2\2\2\u0185\u0183\3\2"+
		"\2\2\u0185\u0186\3\2\2\2\u0186\u0187\3\2\2\2\u0187\u0188\b.\2\2\u0188"+
		"\\\3\2\2\2\u0189\u018a\7]\2\2\u018a^\3\2\2\2\u018b\u018c\7_\2\2\u018c"+
		"`\3\2\2\2\u018d\u018e\7*\2\2\u018eb\3\2\2\2\u018f\u0190\7+\2\2\u0190d"+
		"\3\2\2\2\u0191\u019a\7\62\2\2\u0192\u0196\4\63;\2\u0193\u0195\4\62;\2"+
		"\u0194\u0193\3\2\2\2\u0195\u0198\3\2\2\2\u0196\u0194\3\2\2\2\u0196\u0197"+
		"\3\2\2\2\u0197\u019a\3\2\2\2\u0198\u0196\3\2\2\2\u0199\u0191\3\2\2\2\u0199"+
		"\u0192\3\2\2\2\u019af\3\2\2\2\u019b\u019c\7<\2\2\u019ch\3\2\2\2\u019d"+
		"\u019e\7=\2\2\u019ej\3\2\2\2\u019f\u01a0\7.\2\2\u01a0l\3\2\2\2\u01a1\u01a2"+
		"\7\60\2\2\u01a2n\3\2\2\2\u01a3\u01a4\7/\2\2\u01a4\u01a5\7@\2\2\u01a5p"+
		"\3\2\2\2\u01a6\u01a7\7>\2\2\u01a7r\3\2\2\2\u01a8\u01a9\7@\2\2\u01a9t\3"+
		"\2\2\2\u01aa\u01ab\7~\2\2\u01abv\3\2\2\2\u01ac\u01ad\7~\2\2\u01ad\u01ae"+
		"\7~\2\2\u01aex\3\2\2\2\u01af\u01b0\7f\2\2\u01b0\u01b1\7g\2\2\u01b1\u01b2"+
		"\7h\2\2\u01b2\u01b3\7c\2\2\u01b3\u01b4\7w\2\2\u01b4\u01b5\7n\2\2\u01b5"+
		"\u01b6\7v\2\2\u01b6z\3\2\2\2\u01b7\u01b8\t\3\2\2\u01b8|\3\2\2\2\u01b9"+
		"\u01ba\7^\2\2\u01ba\u01bd\t\4\2\2\u01bb\u01bd\5\177@\2\u01bc\u01b9\3\2"+
		"\2\2\u01bc\u01bb\3\2\2\2\u01bd~\3\2\2\2\u01be\u01bf\7^\2\2\u01bf\u01c0"+
		"\7w\2\2\u01c0\u01c1\5{>\2\u01c1\u01c2\5{>\2\u01c2\u01c3\5{>\2\u01c3\u01c4"+
		"\5{>\2\u01c4\u0080\3\2\2\2\u01c5\u01c6\t\5\2\2\u01c6\u0082\3\2\2\2\u01c7"+
		"\u01c8\4\62;\2\u01c8\u0084\3\2\2\2\u01c9\u01ce\7$\2\2\u01ca\u01cd\5}?"+
		"\2\u01cb\u01cd\n\6\2\2\u01cc\u01ca\3\2\2\2\u01cc\u01cb\3\2\2\2\u01cd\u01d0"+
		"\3\2\2\2\u01ce\u01cc\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01d1\3\2\2\2\u01d0"+
		"\u01ce\3\2\2\2\u01d1\u01d2\7$\2\2\u01d2\u0086\3\2\2\2\u01d3\u01d4\7\61"+
		"\2\2\u01d4\u01d5\7,\2\2\u01d5\u01d9\3\2\2\2\u01d6\u01d8\13\2\2\2\u01d7"+
		"\u01d6\3\2\2\2\u01d8\u01db\3\2\2\2\u01d9\u01da\3\2\2\2\u01d9\u01d7\3\2"+
		"\2\2\u01da\u01dc\3\2\2\2\u01db\u01d9\3\2\2\2\u01dc\u01dd\7,\2\2\u01dd"+
		"\u01de\7\61\2\2\u01de\u01df\3\2\2\2\u01df\u01e0\bD\3\2\u01e0\u0088\3\2"+
		"\2\2\u01e1\u01e3\7a\2\2\u01e2\u01e1\3\2\2\2\u01e2\u01e3\3\2\2\2\u01e3"+
		"\u01e4\3\2\2\2\u01e4\u01e9\5\u0081A\2\u01e5\u01e8\5\u0081A\2\u01e6\u01e8"+
		"\5\u0083B\2\u01e7\u01e5\3\2\2\2\u01e7\u01e6\3\2\2\2\u01e8\u01eb\3\2\2"+
		"\2\u01e9\u01e7\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea\u008a\3\2\2\2\u01eb\u01e9"+
		"\3\2\2\2\r\2\u0185\u0196\u0199\u01bc\u01cc\u01ce\u01d9\u01e2\u01e7\u01e9";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}