package com.mylaensys.isis.viewer.dhtmlx.desktop.component;


import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;
import com.mylaensys.isis.viewer.dhtmlx.Component;
import com.mylaensys.isis.viewer.dhtmlx.desktop.ComponentRenderException;
import com.mylaensys.isis.viewer.dhtmlx.servlet.utils.Utils;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.ActionType;
import org.apache.isis.core.metamodel.spec.feature.ObjectAction;
import org.apache.isis.core.metamodel.spec.feature.ObjectActionContainer;
import org.apache.isis.core.runtime.system.context.IsisContext;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DHTMLX Context Menu
 */
@XmlRootElement(name = "menu")
public class ContextMenu extends AbstractComponent implements Component {
    /** id attribute.*/
    @XmlAttribute
    private String id;
    /** list of menu items.*/
    @XmlElement(name="item")
    private List<ContextMenuItem> items;

    /**
     * Default constructor required by JAXB.
     */
    public ContextMenu() {
        this.id = "0";
        this.items = new ArrayList<ContextMenuItem>();
    }

    /**
     * Creates a context menu.
     * @param menuId unique menu Id
     */
    public ContextMenu(String menuId) {
        this.id = menuId;
        this.items = new ArrayList<ContextMenuItem>();
    }

    /**
     * Returns the id.
     * @return the unique key of the item.
     */
    public String getId() {
        return id;
    }


    /**
     * Add an item to the context menu
     * @param id unique key
     * @param text menu text
     * @return a instance of a ContextMenuItem
     */
    public ContextMenuItem addItem(String id,String text) {
        ContextMenuItem item = new ContextMenuItem(id,text);
        this.items.add( item );
        return item;
    }

    /**
     * Returns the list of Context Menu Items.
     * @return the list of items.
     */
    public List<ContextMenuItem> getItems() {
        return items;
    }

    /**
     * Return the specified Menu Item
     * @param id unique key
     * @return an instance of a ContextMenuItem
     * @throws ComponentRenderException if the id does not exists
     */
    public ContextMenuItem get(String id) throws ComponentRenderException {
        for(ContextMenuItem item : items ) {
            if( item.getId().equalsIgnoreCase( id ) ) {
                return item;
            }
        }
        throw new ComponentRenderException("Invalid item : " + id);
    }

    @Override
    public Component acceptInstance(ObjectAdapter instance) throws ComponentRenderException {
        final List<ObjectAction> actions = instance.getSpecification().getObjectActions(Arrays.asList(ActionType.USER), ObjectActionContainer.Contributed.EXCLUDED);

        for(ObjectAction action : actions) {
            String oid = Utils.computeOid(instance.getOid().enString(IsisContext.getOidMarshaller()));
            String boundedObject = instance.getSpecification().getFullIdentifier();

            String menuId = "dhx/objects/object/" +  boundedObject  + ":" + action.getId();
            if( action.getParameterCount() == 0 ) {
                this.addItem(menuId, action.getName());
            }
        }
        return this;
    }
}
