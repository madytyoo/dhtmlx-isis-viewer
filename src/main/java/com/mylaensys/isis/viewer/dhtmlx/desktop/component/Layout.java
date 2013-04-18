package com.mylaensys.isis.viewer.dhtmlx.desktop.component;


import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "layout")
public class Layout extends AbstractComponent {

    private String id;
    private String pattern;
    @XmlElement(name="cell")
    private List<LayoutCell> cells;

    /**
     * Default constructor required by JAXB.
     */
    public Layout() {
        this.cells = new ArrayList<LayoutCell>();
    }

    /**
     * Constructor
     * @param pattern
     */
    public Layout(String pattern) {
        this.pattern = pattern;
        this.cells = new ArrayList<LayoutCell>();
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @XmlAttribute
    public String getPattern() {
        return pattern;
    }

    public List<LayoutCell> getCells() {
        return cells;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append( "layout ").append( pattern );
        if( this.id != null ) {
            buffer.append( " id:" + this.id );
        }

        return buffer.toString();
    }

}
