package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * DHTMLX Context Menu Item
 */
@XmlRootElement(name = "item")
public class ContextMenuItem {
    /** id attribute.*/
    @XmlAttribute
    private String id;
    /** text attribute.*/
    @XmlAttribute
    private String text;
    /** img attribute.*/
    @XmlAttribute
    private String img;
    /** list of Context Menu Item.*/
    @XmlElement(name="item")
    private List<ContextMenuItem> items;


    /**
     * Default constructor required by JAXB.
     */
    public ContextMenuItem() {
        this.items = new ArrayList<ContextMenuItem>();
    }

    /**
     * Creates a Context Menu Item.
     * @param itemId unique key of the item.
     * @param itemText menu item text
     */
    public ContextMenuItem(String itemId, String itemText) {
        this.id = itemId;
        this.text = itemText;
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
     * Returns the text.
     * @return the text of an item.
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the list of items.
     * @return the list of items.
     */
    public List<ContextMenuItem> getItems() {
        return items;
    }

    /**
     * Add an item to the context menu
     * @param id unique key.
     * @param text of item.
     * @return a instance of a ContextMenuItem
     */
    public ContextMenuItem addItem(String id,String text) {
        ContextMenuItem item = new ContextMenuItem(id,text);
        this.items.add( item );
        return item;
    }
}
