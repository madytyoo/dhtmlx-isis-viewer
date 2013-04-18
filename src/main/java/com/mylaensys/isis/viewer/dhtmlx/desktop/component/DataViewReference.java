package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;
import com.mylaensys.isis.viewer.dhtmlx.Component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dataview")
public class DataViewReference extends AbstractComponent {
     /** template */
    private String template;
    /** the item height. The default value - 115 */
    private String height;
    /** the item width. The default value - 210 */
    private String width;
    /** the item margin. The default value - 0 */
    private String margin;
    /** the item padding. The default value - 0 */
    private String padding;
    /** border of the item container. The default value - 1 */
    private String border;
    private String css;
    private String ref;

    public DataViewReference() {
    }

    public DataViewReference(String template) {
        this.template = template;
    }

    @XmlAttribute
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }


    public void setHeight(String height) {
        this.height = height;
    }


    public void setWidth(String width) {
        this.width = width;
    }


    public void setMargin(String margin) {
        this.margin = margin;
    }


    public void setPadding(String padding) {
        this.padding = padding;
    }

    public void setBorder(String border) {
        this.border = border;
    }


    public void setCss(String css) {
        this.css = css;
    }

    @XmlAttribute
    public String getRef() {
        StringBuffer buffer = new StringBuffer( ref );
        buffer.append("dhx=" + Component.DATAVIEW);
        // do not send the template, absolutely useless

        return buffer.toString();
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("dataview");
        if( this.template != null ) {
            buffer.append( " template:" + this.template );
        }
        return buffer.toString();
    }
}

