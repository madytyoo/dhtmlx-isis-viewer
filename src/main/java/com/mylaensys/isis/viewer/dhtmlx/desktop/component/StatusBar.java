package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;
import com.mylaensys.isis.viewer.dhtmlx.Component;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "statusbar")
public class StatusBar extends AbstractComponent implements Component {
    /**
     * Default constructor required by JAXB.
     */
    public StatusBar() {
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("statusbar");
        return buffer.toString();
    }
}
