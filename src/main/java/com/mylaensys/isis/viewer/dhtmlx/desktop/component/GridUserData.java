package com.mylaensys.isis.viewer.dhtmlx.desktop.component;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "userdata")
public class GridUserData {

    private String name;

    private String value;

    public GridUserData() {
    }

    public GridUserData(String name, String value) {
        this.name = name;
        this.value = value;
    }
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
