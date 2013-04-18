package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "option")
public class FormItemOption {
    @XmlAttribute
    private String label;
    @XmlAttribute
    private String value;
    @XmlAttribute
    private Boolean selected;

    public FormItemOption() {
    }

    public FormItemOption(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public FormItemOption(String label, String value, Boolean selected) {
        this.label = label;
        this.value = value;
        this.selected = selected;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public Boolean getSelected() {
        return selected;
    }
}
