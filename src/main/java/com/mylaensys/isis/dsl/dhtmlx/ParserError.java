package com.mylaensys.isis.dsl.dhtmlx;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ParserError {
    @XmlAttribute
    private int line;
    @XmlAttribute
    private int column;
    @XmlAttribute
    private String message;

    public ParserError() {
    }

    public ParserError(int line, int column, String message) {
        this.line = line;
        this.column = column;
        this.message = message;
    }
}
