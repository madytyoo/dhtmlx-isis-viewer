package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 */
@XmlRootElement(name = "cell")
public class GridRowCell {
    @XmlValue
    private String text;

    public GridRowCell() {
    }

    public GridRowCell(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
