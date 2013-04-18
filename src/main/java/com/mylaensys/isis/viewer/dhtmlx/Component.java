package com.mylaensys.isis.viewer.dhtmlx;


import com.mylaensys.isis.viewer.dhtmlx.desktop.ComponentRenderException;
import com.mylaensys.isis.viewer.dhtmlx.desktop.component.Widget;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.feature.ObjectAction;

import java.util.List;

public interface Component {
    String MENU     = "dhtmlXMenuObject";
    String TOOLBAR  = "dhtmlXToolbarObject";
    String GRID     = "dhtmlXGridObject";
    String DATAVIEW     = "dhtmlXDataView";
    String FORM     = "dhtmlxForm";
    String LAYOUT   = "dhtmlXLayoutObject";
    String TABBAR   = "dhtmlxTabBar";
    String ERRORS   = "errors";
    String REF      = "reference";

    String SELECT   = "dhtmlXFormSelect";
    String WIDGET   = "widget";


    String TOUCHVIEW = "touchView";
    String GROUPLIST = "touchGroupList";



    Component acceptServices(List<ObjectAdapter> services, String objectName) throws ComponentRenderException;

    Component acceptObject(ObjectAdapter object, ObjectAdapter result);
    Component acceptObject(ObjectAdapter object, ObjectAction action, ObjectAdapter result) throws ComponentRenderException;

    Component acceptWidget(Widget widget);

    Component acceptObject(ObjectAdapter object, ObjectAction action) throws ComponentRenderException;


    Component acceptInstance(ObjectAdapter instance) throws ComponentRenderException;

    void setContent(Widget widget);

    Component acceptInstance(ObjectAdapter instance, String collection) throws ComponentRenderException;

    void setParameter(String name, String value);

}