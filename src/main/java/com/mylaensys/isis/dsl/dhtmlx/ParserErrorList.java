package com.mylaensys.isis.dsl.dhtmlx;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "errors")
public class ParserErrorList {
    @XmlElement(name="error")
    private List<ParserError> errors;

    public ParserErrorList() {

    }
    public ParserErrorList(List<ParserError> errors) {
        this.errors = errors;
    }

}
