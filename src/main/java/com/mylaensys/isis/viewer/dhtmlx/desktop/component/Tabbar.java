package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;
import com.mylaensys.isis.viewer.dhtmlx.Component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "tabbar")
public class Tabbar extends AbstractComponent {
    private String id;
    private String mode;
    private String active;
    @XmlElement(name="tab")
    private List<TabbarCell> tabs;

    /**
     * Default constructor required by JAXB.
     */
    public Tabbar() {
        this.tabs = new ArrayList<TabbarCell>();
    }


    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @XmlAttribute
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public List<TabbarCell> getTabs() {
        return tabs;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("tabbar ");
        if (this.id != null) {
            buffer.append(" id:" + this.id);
        }
        if (this.mode  != null) {
            buffer.append(" mode:" + this.mode);
        }

        return buffer.toString();
    }

}
