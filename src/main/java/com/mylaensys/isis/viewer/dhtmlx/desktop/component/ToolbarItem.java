package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Toolbar button.
 */
@XmlRootElement(name = "item")
public class ToolbarItem {
    /** id attribute.*/
    private String id;
    /** type attribute.*/
    private String type;
    /** text attribute.*/
    private String text;
    /** img attribute.*/
    private String img;
    /** action attribute.*/
    private String action;
    /** disabled attribute.*/
    private String enabled;
    /** list of other toolbar items.*/
    @XmlElement(name = "item")
    private List<ToolbarItem> items;

    /**
     * Default constructor required by jax.
     */
    public ToolbarItem() { }

    /**
     * Creates a toolbar item.
     * @param itemId unique key.
     * @param itemType type of item.
     */
    public ToolbarItem(final String itemId, final String itemType) {
        this.id = itemId;
        this.type = itemType;
    }

    /**
     * Creates a toolbar item.
     * @param id unique key.
     * @param type type of item.
     * @param text for the item.
     * @param img image for the item.
     */
    public ToolbarItem(final String id, final String type, final String text, final String img) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.img = img;
        this.items = new ArrayList<ToolbarItem>();
    }

    /**
     * Creates a toolbar item.
     * @param id unique key.
     * @param type type of item.
     * @param text for the item.
     * @param action action for the item.
     */
    public ToolbarItem(final String id, final String type, final String text, final String img, final String action) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.img = img;
        this.action = action;
        this.items = new ArrayList<ToolbarItem>();
    }


    /**
     * Returns the id.
     * @return the unique key of the item.
     */
    @XmlAttribute
    public final String getId() {
        return id;
    }

    /**
     * Returns the type.
     * @return the type of an item.
     */
    @XmlAttribute
    public final String getType() {
        return type;
    }

    /**
     * Returns the text.
     * @return the text of an item.
     */
    @XmlAttribute
    public final String getText() {
        return text;
    }

    /**
     * Returns the image.
     * @return the image of an item.
     */
    @XmlAttribute
    public final String getImg() {
        return img;
    }



    @XmlAttribute
    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * return the status
     * @return
     */



    /**
      * Returns the action (if any).
      * @return the action of an item.
     */
    @XmlAttribute
    public final String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Returns the list of items.
     * @return the list of items.
     */
    public final List<ToolbarItem> getItems() {
        return items;
    }

    /**
     * Add a button to the toolbar.
     * @param id unique key.
     * @param text of item.
     * @param img image for the item.
     * @return a button
     */
    public final ToolbarItem addButton(final String id, final String text, final String img ) {
        ToolbarItem item = new ToolbarItem(id, "button", text, img);
        this.items.add(item);
        return item;
    }


}
