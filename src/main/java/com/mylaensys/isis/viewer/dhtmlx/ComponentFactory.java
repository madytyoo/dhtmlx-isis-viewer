package com.mylaensys.isis.viewer.dhtmlx;

import com.mylaensys.isis.viewer.dhtmlx.desktop.ComponentCreationException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * DHTMLX component factory  (singleton)
 *
 */
public abstract class ComponentFactory implements Serializable {
    protected final Map<String,String> components;


    /**
     * Default Constructor
     */
    protected ComponentFactory() {
        this.components = new HashMap<String,String>();
    }

    /**
     * Register a component
     * @param component the coomponent to register
     */
    protected void register(final String name, final Component component) {
        components.put(name, component.getClass().getName() );
    }


    /**
     * Creates a DHTMLX component
     * @param name  name of the component
     * @return  an instance of the requested DHTMLX coponent
     * @throws ComponentCreationException
     *
     * @see Component
     */
    public Component createComponent(String name) throws ComponentCreationException {
        Component component = null;
        if(!components.containsKey(name)) {
            throw new ComponentCreationException("No such component " +  name);
        }
        try {
            component = (Component) Class.forName( components.get(name) ).newInstance();
        } catch (Exception e) {
            throw new ComponentCreationException(e.getMessage(),e);
        }
        return component;
    }

}
