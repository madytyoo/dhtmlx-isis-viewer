package com.mylaensys.isis.viewer.dhtmlx.desktop.component;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cell")
public class ContainerCell {
    private String id;
    private String width;
    private String height;
    private String text;
    private Toolbar toolbar;
    @XmlElement(name="layout")
    private Layout layout;
    @XmlElement(name="tabbar")
    private Tabbar tabbar;
    @XmlElement(name="grid")
    private GridReference grid;
    @XmlElement(name="dataview")
    private DataViewReference dataview;
    @XmlElement(name="form")
    private Form form;

    /**
     * Default constructor required by JAXB.
     */
    public ContainerCell() { }

    /**
     * Constructor
     * @param id
     */
    public ContainerCell(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @XmlAttribute
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @XmlAttribute
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @XmlElement(name="toolbar")
    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;

    }

    public void setTabbar(Tabbar tabbar) {
        this.tabbar = tabbar;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append( " cell [" + id + "]");
        if( this.width != null ) {
            buffer.append( " width:" + this.width );
        }
        if( this.height != null ) {
            buffer.append( " height:" + this.width );
        }
        return buffer.toString();
    }

    public void setGridCellection(GridReference collection) {
        this.grid = collection;
    }

    public void setDataViewCollection(DataViewReference collection) {
        this.dataview = collection;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
