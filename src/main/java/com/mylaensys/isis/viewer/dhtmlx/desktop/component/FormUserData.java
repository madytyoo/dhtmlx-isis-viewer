package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "userdata")
public class FormUserData {
    private String name;
    private String value;

    public FormUserData() {
    }

    public FormUserData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    @XmlValue
    public String getValue() {
        return value;
    }
}
