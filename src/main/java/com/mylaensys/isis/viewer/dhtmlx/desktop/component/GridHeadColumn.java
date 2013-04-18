package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 */

@XmlRootElement(name = "column")
public class GridHeadColumn {
    @XmlAttribute
    private String width;
    @XmlAttribute
    private String type;
    @XmlAttribute
    private String align;
    @XmlAttribute
    private String sort;
    @XmlValue
    private String text;
    @XmlAttribute
    private String format;


    public GridHeadColumn() {
    }

    public GridHeadColumn(String text,String width, String type, String align, String sort, String format) {
        this.text = text;
        this.width = width;
        this.type = type;
        this.align = align;
        this.sort = sort;
        this.format = format;
    }

    public String getWidth() {
        return width;
    }

    public String getType() {
        return type;
    }

    public String getAlign() {
        return align;
    }

    public String getSort() {
        return sort;
    }

    public String getText() {
        return text;
    }

    public String getFormat() {
        return format;
    }
}
