package com.mylaensys.isis.viewer.dhtmlx.desktop.component;


import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/* Used to wrap a form without container in <form></form> */
@XmlRootElement
public class ContainerForm extends AbstractComponent {
    @XmlElement(name="form")
    private Form form;

    public ContainerForm() {
    }

    public ContainerForm(Form form) {
        this.form = form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
