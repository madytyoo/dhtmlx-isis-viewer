package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;
import com.mylaensys.isis.viewer.dhtmlx.Component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "grid")
public class GridReference extends AbstractComponent {

    private String ref;
    private String columns;

    public GridReference() {
    }

    public GridReference(String columns) {
        this.columns = columns;
    }

    @XmlAttribute
    public String getRef() {
        String url = "";
        StringBuffer buffer = new StringBuffer( ref );
        buffer.append("dhx=" + Component.GRID);
        if(this.columns != null ) {
            buffer.append( "&columns=" ).append(this.columns);
        }

        return buffer.toString();
    }

    public void setRef(String ref) {
        this.ref = ref;
    }


    public void setColumns(String columns) {
        this.columns = columns;
    }
}
