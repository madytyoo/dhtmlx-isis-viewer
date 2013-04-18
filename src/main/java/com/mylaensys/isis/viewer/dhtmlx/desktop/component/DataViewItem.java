package com.mylaensys.isis.viewer.dhtmlx.desktop.component;


import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "item")
public class DataViewItem {
    @XmlAttribute
    private String id;

    private Map<String, String> data;

    public DataViewItem() {
    }

    public DataViewItem(String id) {
        this.id = id;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    @XmlAnyElement
    public List<JAXBElement<String>> getMonths() {
    List<JAXBElement<String>> elements = new ArrayList<JAXBElement<String>>();
    for (Map.Entry<String, String> month: data.entrySet())
        elements.add(new JAXBElement(new QName(month.getKey()),
                                     String.class, month.getValue()));
    return elements;
}
}
