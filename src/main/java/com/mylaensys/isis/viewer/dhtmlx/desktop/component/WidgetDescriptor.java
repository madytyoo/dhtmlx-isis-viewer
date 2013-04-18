package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;
import com.mylaensys.isis.viewer.dhtmlx.Component;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Component descriptor
 */
@XmlRootElement(name = "widgets")
public class WidgetDescriptor extends AbstractComponent implements Component {
    @XmlElement(name="widget")
    private List<Widget> widgets;

    public WidgetDescriptor() {
        this.widgets = new ArrayList<Widget>();
    }


    public List<Widget> getWidgets() {
        return widgets;
    }

    @Override
    public Component acceptWidget(Widget widget) {
        this.widgets.add(widget);
        return this;
    }
}
