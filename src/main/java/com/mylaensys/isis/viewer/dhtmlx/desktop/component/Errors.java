package com.mylaensys.isis.viewer.dhtmlx.desktop.component;


import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "errors")
public class Errors extends AbstractComponent {
    @XmlElement(name="error")
    private List<ErrorMessage> errors;

    public Errors() {
        this.errors = new ArrayList<ErrorMessage>();
    }


    public void add(String message) {
        this.errors.add( new ErrorMessage(message) );
    }
}
