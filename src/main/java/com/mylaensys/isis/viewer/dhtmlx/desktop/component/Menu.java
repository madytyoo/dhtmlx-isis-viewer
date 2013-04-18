package com.mylaensys.isis.viewer.dhtmlx.desktop.component;


import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;
import com.mylaensys.isis.viewer.dhtmlx.Component;
import com.mylaensys.isis.viewer.dhtmlx.desktop.ComponentRenderException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * DHTMLX Menu
 */
@XmlRootElement(name = "toolbar")
public class Menu extends AbstractComponent implements Component {
    @XmlElement(name="item")
    private List<ToolbarItem> items;

    public Menu() {
        this.items = new ArrayList<ToolbarItem>();
    }

    public ToolbarItem addSelect( String id, String text, String img ) {
        ToolbarItem item = new ToolbarItem( id, "buttonSelect", text, img );
        this.items.add( item );
        return item;
    }

    public List<ToolbarItem> getItems() {
        return items;
    }


    public ToolbarItem get(String id) throws ComponentRenderException {
        for(ToolbarItem item : this.items) {
            if( item.getId().compareTo( id ) == 0 ) {
                return item;
            }
        }
        throw new ComponentRenderException("Invalid item : " + id);
    }
}
