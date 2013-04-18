package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "param")
public class GridHeadEventParam {
    @XmlValue
    private String value;

    public GridHeadEventParam() {
    }

    public GridHeadEventParam(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
