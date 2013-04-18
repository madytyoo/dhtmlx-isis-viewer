package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu Item
 */
@XmlRootElement(name = "item")
public class MenuItem {
    @XmlAttribute
    private String id;
    @XmlAttribute
    private String type;
    @XmlAttribute
    private String text;
    @XmlAttribute
    private String img;
    @XmlAttribute
    private String action;
    @XmlElement(name = "item")
    private List<MenuItem> items;

    public MenuItem() {

    }

    public MenuItem(String id, String type, String text, String img) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.img = img;
        this.items = new ArrayList<MenuItem>();
    }

    public MenuItem(String id, String type, String text, String img, String action) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.img = img;
        this.action = action;
        this.items = new ArrayList<MenuItem>();
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getImg() {
        return img;
    }

    public String getAction() {
        return action;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public MenuItem addButton(String id, String text, String img , String action) {
        MenuItem item = new MenuItem( id, "button", text, img , action );
        this.items.add(item);
        return item;
    }

}
