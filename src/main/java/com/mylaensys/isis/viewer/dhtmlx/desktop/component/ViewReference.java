package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;
import com.mylaensys.isis.viewer.dhtmlx.Component;
import com.mylaensys.isis.viewer.dhtmlx.desktop.ComponentRenderException;
import com.mylaensys.isis.viewer.dhtmlx.servlet.utils.Utils;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.runtime.system.context.IsisContext;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "view")
public class ViewReference extends AbstractComponent{
    private String id;
    private String text;

    public ViewReference() {
    }


    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @XmlAttribute
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Component acceptInstance(ObjectAdapter instance) throws ComponentRenderException {
        String oid = Utils.computeOid( instance.getOid().enString(IsisContext.getOidMarshaller()) );
        this.id = "dhx/objects/object/" + instance.getSpecification().getFullIdentifier() + "/" + oid;
        this.text = instance.titleString();

        return this;
    }
}
