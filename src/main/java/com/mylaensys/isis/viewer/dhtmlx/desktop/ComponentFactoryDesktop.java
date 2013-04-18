package com.mylaensys.isis.viewer.dhtmlx.desktop;

import com.mylaensys.isis.viewer.dhtmlx.Component;
import com.mylaensys.isis.viewer.dhtmlx.ComponentFactory;
import com.mylaensys.isis.viewer.dhtmlx.desktop.component.*;

public class ComponentFactoryDesktop extends ComponentFactory {

    public ComponentFactoryDesktop () {
        register(Component.TOOLBAR,new Toolbar());
        register(Component.MENU,new ContextMenu());
        register(Component.GRID,new Grid());
        register(Component.DATAVIEW,new DataView());
        register(Component.FORM,new Form());
        register(Component.SELECT,new FormSelect());
        register(Component.REF,new ViewReference());
        register(Component.WIDGET,new WidgetDescriptor());
        register(Component.ERRORS,new Errors());
    }
}
