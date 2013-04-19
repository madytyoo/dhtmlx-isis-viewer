package com.mylaensys.isis.viewer.dhtmlx.servlet.utils;

import com.mylaensys.isis.viewer.dhtmlx.desktop.InvalidActionException;
import com.mylaensys.isis.viewer.dhtmlx.desktop.InvalidObjectException;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.services.ServiceUtil;
import org.apache.isis.core.metamodel.spec.ActionType;
import org.apache.isis.core.metamodel.spec.feature.ObjectAction;
import org.apache.isis.core.metamodel.spec.feature.ObjectActionContainer;
import org.apache.isis.core.runtime.system.context.IsisContext;

import java.util.Arrays;
import java.util.List;

public class Utils {

    public static ObjectAdapter findService(String object) throws InvalidObjectException {
        final List<ObjectAdapter> serviceAdapters = IsisContext.getPersistenceSession().getServices();
        for (final ObjectAdapter serviceAdapter : serviceAdapters) {
            final Object service = serviceAdapter.getObject();
            if (ServiceUtil.id(service).equals(object.trim())) {
                final ObjectAdapter adapter = IsisContext.getPersistenceSession().getAdapterManager().getAdapterFor(service);
                return adapter;
            }
        }
        throw new InvalidObjectException("Failed to find object " + object);
    }

    public static ObjectAdapter findObject(String object) throws InvalidObjectException {
        final List<ObjectAdapter> serviceAdapters = IsisContext.getPersistenceSession().getServices();
        for (final ObjectAdapter serviceAdapter : serviceAdapters) {
            if (serviceAdapter.getSpecification().getSpecId().asString().equals(object))  {
                //final ObjectAdapter adapter = IsisContext.getPersistenceSession().getAdapterManager().getAdapterFor( serviceAdapter );
                //return adapter;
                return serviceAdapter ;
            }
        }
        throw new InvalidObjectException("Failed to find object " + object);
    }



    public static ObjectAction findAction(final ObjectAdapter object, final String methodName) throws InvalidActionException {
        if (object == null) {
            throw new InvalidActionException("Object not specified when looking for " + methodName);
        }

        final List<ObjectAction> actions = object.getSpecification().getObjectActions(Arrays.asList(ActionType.USER, ActionType.EXPLORATION, ActionType.PROTOTYPE, ActionType.DEBUG), ObjectActionContainer.Contributed.INCLUDED);
        final ObjectAction action = findAction(actions, methodName);
        if (action == null) {
            throw new InvalidActionException("Failed to find action " + methodName + " on " + object);
        }
        return action;
    }

    private static ObjectAction findAction(final List<ObjectAction> actions, final String methodName) {
        for (int i = 0; i < actions.size(); i++) {
            if (actions.get(i).getActions().size() > 0) {
                final ObjectAction action = findAction(actions.get(i).getActions(), methodName);
                if (action != null) {
                    return action;
                }

            } else {
                if (actions.get(i).getId().equals(methodName)) {
                    return actions.get(i);
                }
            }
        }
        return null;
    }

    public static String computeOid(String id) {
        int index = id.indexOf("^");
        if( index > -1 ) {
            id = id.substring(0,index);
        }
        return id;
    }

}