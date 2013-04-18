package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "widget")
public class Widget {
    @XmlAttribute
    private String dhx;
    @XmlAttribute
    private String title;
    @XmlAttribute
    private String url;
    @XmlAttribute
    private String object;
    @XmlAttribute
    private String action;
    @XmlAttribute
    private String type;



    public Widget() {
    }

    public Widget(String dhx, String title, String object, String action,String type) {
        this.dhx = dhx;
        this.title = title;
        this.object = object;
        this.action = action;
        this.type = type;
        this.url = "_c=" + dhx + "&_object=" + object + "&_action=" + action + "&_type=" + type;
    }

    public Widget(String dhx, String title, String object, String action,String type,String param) {
        this.dhx = dhx;
        this.title = title;
        this.object = object;
        this.action = action;
        this.type = type;
        this.url = "_c=" + dhx + "&_object=" + object + "&_action=" + action + "&_type=" + type + param;
    }

    public String getDhx() {
        return dhx;
    }

    public String getObject() {
        return object;
    }

    public String getAction() {
        return action;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
