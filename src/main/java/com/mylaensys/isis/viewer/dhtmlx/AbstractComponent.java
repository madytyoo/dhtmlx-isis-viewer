package com.mylaensys.isis.viewer.dhtmlx;

import com.mylaensys.isis.viewer.dhtmlx.desktop.ComponentRenderException;
import com.mylaensys.isis.viewer.dhtmlx.desktop.component.Widget;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.feature.ObjectAction;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Abstract component class
 */
public class AbstractComponent implements Component {
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    protected Map<String,String> paramters = new HashMap<String, String>();

    @Override
    public Component acceptServices(List<ObjectAdapter> services, String objectName) throws ComponentRenderException {
        return this;
    }

    @Override
    public Component acceptObject(ObjectAdapter object, ObjectAdapter result) {
        return this;
    }

    @Override
    public Component acceptWidget(Widget widget) {
        return this;
    }

    @Override
    public Component acceptObject(ObjectAdapter object, ObjectAction action) throws ComponentRenderException {
        return this;
    }

    @Override
    public Component acceptObject(ObjectAdapter object, ObjectAction action, ObjectAdapter result) throws ComponentRenderException {
        return this;
    }

    @Override
    public Component acceptInstance(ObjectAdapter instance) throws ComponentRenderException {
        return this;
    }

    @Override
    public Component acceptInstance(ObjectAdapter instance, String collection) throws ComponentRenderException {
        return this;
    }

    @Override
    public void setParameter(String name, String value) {
        this.paramters.put(name,value);
    }

    public String toDHX(Object value , String type) {
        if( value == null )
            return "";
        if( type.endsWith("java.util.Date") ) {
            return dateFormat.format( value );
        }


        if( "boolean" == type ) {
            if( value.toString().equalsIgnoreCase("false")) {
                return "0";
            } else {
                return "1";
            }
        }
        return value.toString();
    }

    public String formatToDHX(String type) {
        return null;
    }

    public String alignToDHX(String type) {
        return "left";
    }

    public String typeToDHX(String type) {
        if( "boolean".equalsIgnoreCase( type ) ) {
            return "ch";
        }
        return "ro";
    }

    @Override
    public void setContent(Widget widget) { }

}
