package com.mylaensys.isis.viewer.dhtmlx.desktop.component;


import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;
import com.mylaensys.isis.viewer.dhtmlx.Component;
import com.mylaensys.isis.viewer.dhtmlx.desktop.ComponentRenderException;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.services.ServiceUtil;
import org.apache.isis.core.metamodel.spec.ActionType;
import org.apache.isis.core.metamodel.spec.feature.ObjectAction;
import org.apache.isis.core.metamodel.spec.feature.ObjectActionContainer;
import org.apache.isis.core.runtime.system.context.IsisContext;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DHTMLX Toolbar (dhtmlXToolbarObject)
 */
@XmlRootElement(name = "toolbar")
public class Toolbar extends AbstractComponent implements Component {
    /** list of toolbar items.*/
    @XmlElement(name="item")
    private List<ToolbarItem> items;

    /**
     * Default constructor required by JAXB.
     */
    public Toolbar() {
        this.items = new ArrayList<ToolbarItem>();
    }

    /**
     * Returns the list of Toolbar items
     * @return the list of items.
     */
    public List<ToolbarItem> getItems() {
        return items;
    }


    /**
     * Add a button to the toolbar
     * @param id
     * @param text
     */
    public ToolbarItem addButton(String id, String text) {
        ToolbarItem item = new ToolbarItem( id, "button", text, null );
        this.items.add( item );
        return item;
    }


    /**
     * Add a button Select
     * @param id unique key
     * @param text toolbar button text
     * @param img toolbar button image
     *
     * @return an instance of a toolbar item button
     */
    public ToolbarItem addSelect( String id, String text, String img ) {
        ToolbarItem item = new ToolbarItem( id, "buttonSelect", text, img );
        this.items.add( item );
        return item;
    }
    /**
     * Add a text
     * @param id unique key
     * @return an instance of a toolbar item (separator)
     */
    public ToolbarItem addText(String id,String label) {
        ToolbarItem item = new ToolbarItem( id, "text", label, null );
        this.items.add( item );
        return item;
    }
    /**
     * Add a separator
     * @param id unique key
     * @return an instance of a toolbar item (separator)
     */
    public ToolbarItem addSeparator( String id ) {
        ToolbarItem item = new ToolbarItem( id, "separator" );
        this.items.add( item );
        return item;
    }

    /**
     * Return the specified Toolbar Item
     * @param id unique key
     * @return an instance of a ContextMenuItem
     * @throws ComponentRenderException if the id does not exists
     */
    public ToolbarItem get(String id) throws ComponentRenderException {
        for(ToolbarItem item : this.items) {
            if( item.getId().compareTo( id ) == 0 ) {
                return item;
            }
        }
        throw new ComponentRenderException("Invalid item : " + id);
    }

    /**
     * Create a toolbar for Services
     * @param services the List of services
     * @param objectName the name of the Object
     * @return a Toolbar
     * @throws ComponentRenderException
     */
    @Override
    public Component acceptServices(List<ObjectAdapter> services, String objectName) throws ComponentRenderException {

        /* For each service creates a toolbar button select */
        for (final ObjectAdapter serviceAdapter : services) {
            String icon = serviceAdapter.getIconName() != null ? serviceAdapter.getIconName() + ".png" : null;
            String service = ServiceUtil.id(serviceAdapter.getObject());
            if( service.equalsIgnoreCase( "dom.channel.clients" )) {
                continue;
            }
                this.addSelect(
                    service,
                    serviceAdapter.titleString(),
                    icon
            );
            String targetURL = "";
            /* For each action creates a button */
            final List<ObjectAction> serviceActions = serviceAdapter.getSpecification().getObjectActions(Arrays.asList(ActionType.USER, ActionType.EXPLORATION, ActionType.PROTOTYPE, ActionType.DEBUG), ObjectActionContainer.Contributed.INCLUDED);

            for (ObjectAction serviceAction : serviceActions) {
                String action = serviceAction.getId();
                /*  Handles @Hidden annotation */
                if (!serviceAction.isVisible(IsisContext.getAuthenticationSession(), serviceAdapter, Where.OBJECT_FORMS).isAllowed() ) {
                    continue;
                }
                /*  Handles @NotInServiceMenu annotation */
                if( serviceAction.getFacet(org.apache.isis.core.progmodel.facets.actions.notinservicemenu.NotInServiceMenuFacet.class) != null ) {
                    continue;
                }

                /* The action prompts the target URL points the prompt service */


                targetURL = "dhx/services/" + service + "/actions/" + action  ;
                if( service.startsWith( "fixture" ) &&  serviceAction.getParameterCount() == 0) {
                    targetURL = "dhx/services/" + service + "/actions/" + action  + "/invoke" ;
                }
                if( serviceAction.getParameterCount() > 0 ) {
                    targetURL += "?prompt";
                }


                this.get(service).addButton(
                        targetURL,
                        serviceAction.getName(),
                        icon
                );
            }
            this.addSeparator("s_" + service);
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("toolbar");
        return buffer.toString();
    }


}
