package com.mylaensys.isis.dsl.dhtmlx;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class ParserErrorListener extends BaseErrorListener {
    private ParserHelper helper;

    public ParserErrorListener(ParserHelper helper) {
        this.helper = helper;
    }
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        if( msg.indexOf("<EOF>") == -1 ) {
            helper.getErrors().add(new ParserError(line,charPositionInLine,msg));
        }
    }


}
