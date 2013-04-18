package com.mylaensys.isis.viewer.dhtmlx.desktop;

import com.mylaensys.isis.dsl.dhtmlx.ParserHelper;
import com.mylaensys.isis.viewer.dhtmlx.Component;
import com.mylaensys.isis.viewer.dhtmlx.desktop.AbstractService;
import com.mylaensys.isis.viewer.dhtmlx.desktop.component.GridReference;
import com.mylaensys.isis.viewer.dhtmlx.servlet.utils.Utils;
import org.apache.isis.applib.profiles.Localization;
import org.apache.isis.core.commons.authentication.AnonymousSession;
import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.consent.Consent;
import org.apache.isis.core.metamodel.facets.object.parseable.InvalidEntryException;
import org.apache.isis.core.metamodel.facets.object.parseable.ParseableFacet;
import org.apache.isis.core.metamodel.facets.object.parseable.TextEntryParseException;
import org.apache.isis.core.metamodel.spec.feature.ObjectAction;
import org.apache.isis.core.metamodel.spec.feature.ObjectActionParameter;
import org.apache.isis.core.runtime.system.context.IsisContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/services")
public class ViewerServiceDesktop extends AbstractService {

    /**
     * Obtain summary representation of all domain services (Service C15)
     *
     * @return
     */
	@GET
	@Path("/")
    @Produces("application/xml")
	public Response services(@QueryParam("dhx") String dhx) {

        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);

        Component component = null;
        try {
            dhx = (dhx == null ? Component.TOOLBAR : dhx);
            component = factory.createComponent( dhx );
            final List<ObjectAdapter> services = IsisContext.getPersistenceSession().getServices();
            component = component.acceptServices(services, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IsisContext.closeSession();
        }

		return Response.status(200).entity(component).build();
	}


    /**
     * Obtain a view/prompt for the service action
     *
     * @param service
     * @param action
     * @return
     */
    @GET
	@Path("/{service}/actions/{action}")
    @Produces("application/xml")
	public Response serviceActionPrompt(@PathParam("service") String service,
                                        @PathParam("action") String action,
                                        @Context HttpServletRequest request) {

        Component component = null;
        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);

        try {
            final ObjectAdapter serviceAdapter = Utils.findService(service);
            final ObjectAction serviceAction = Utils.findAction(serviceAdapter, action );

            /* The service prompts for parameters */
            if( serviceAction.promptForParameters( serviceAdapter )) {
                String code = loadDSL(service,action, request);
                if(code.length() > 0 ) {
                    ParserHelper parser = new ParserHelper( );
                    component = (Component) parser.parseForServicePrompt(code, service, action);
                } else {
                    component = factory.createComponent( Component.FORM );
                    component = component.acceptObject(serviceAdapter,serviceAction);
                }
            } else {
                String code = loadDSL(service,action, request);
                if(code.length() > 0 ) {
                    ParserHelper parser = new ParserHelper();
                    component = (Component) parser.parseForServiceAction( code , service, action );
                } else {

                    if("List".equalsIgnoreCase(serviceAction.getReturnType().getShortIdentifier())){
                        GridReference collection = new GridReference();
                        collection.setRef( "dhx/services/" + service + "/actions/" + action + "/invoke?");
                        component = collection;
                    } else {
                        component = factory.createComponent( Component.FORM );
                        component = component.acceptObject(serviceAdapter,serviceAction);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           IsisContext.closeSession();
        }

		return Response.status(200).entity(component).build();
	}

    /**
     * Action invocation
     * @param service
     * @param method
     * @return
     */
    @GET
	@Path("/{service}/actions/{action}/invoke")
    @Produces("application/xml")
	public Response serviceActionInvoke(@PathParam("service") String service,
                                        @PathParam("action") String method,
                                        @Context UriInfo info) {

        Component component = null;
        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);

        try {
            final ObjectAdapter object = Utils.findService(service);
            final ObjectAction action = Utils.findAction(object, method);

            final int parameterCount = action.getParameterCount();
            final ObjectAdapter[] parameters = new ObjectAdapter[parameterCount];

            final ObjectAdapter result = action.execute(object, parameters);

            String dhx = info.getQueryParameters().getFirst("dhx");
            dhx = (dhx != null ? dhx : Component.GRID );
            component = factory.createComponent( dhx );

            String columns = info.getQueryParameters().getFirst("columns");
            if( columns != null ) {
                component.setParameter("columns",columns);
            }
            component.acceptObject(object,action, result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
              IsisContext.closeSession();
        }


		return Response.status(200).entity(component).build();
	}


    /**
     * Action invocation for post
     * @param service
     * @param method
     * @return
     */
    @POST
	@Path("/{service}/actions/{action}/invoke")

    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/xml")
	public Response serviceActionPost(@PathParam("service") String service,
                                        @PathParam("action") String method,
                                        MultivaluedMap<String, String> form) {
        int status = 404;
        Component component = null;
        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);

        try {
            final ObjectAdapter object = Utils.findService(service);
            final ObjectAction action = Utils.findAction(object, method);

            final ObjectAdapter[] parameters = new ObjectAdapter[action.getParameterCount()];

            final List<ObjectActionParameter> parameterList = action.getParameters();
            for(int i = 0;i < action.getParameterCount() ;i++){
                ObjectActionParameter parameter = parameterList.get(i);

                if( parameter.getSpecification().isParseable() ) {
                    /* gets the form value */
                    List<String> enteredValue = form.get( parameter.getId() );
                    if( enteredValue != null ) {
                        String enteredText = enteredValue.get(0);
                        final ParseableFacet parser = parameter.getSpecification().getFacet(ParseableFacet.class);
                        /* try to parse the value */
                        Localization localization = IsisContext.getLocalization();
                        try {
                            parameters[i] = parser.parseTextEntry(parameters[i], enteredText, localization);
                        } catch (final InvalidEntryException e) {
                            e.printStackTrace();
                        } catch (final TextEntryParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
            final Consent consent = action.isProposedArgumentSetValid(object, parameters);
            if( consent.isAllowed() ) {
                final ObjectAdapter result = action.execute(object, parameters);
                component = factory.createComponent( Component.REF );
                component = component.acceptInstance( result );
                status = 200;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
              IsisContext.closeSession();
        }


		return Response.status(200).entity(component).build();
	}

    /**
     * Action invocation for post
     * @param service
     * @param method
     * @return
     */
    @POST
	@Path("/{service}/actions/{action}/invoke")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/xml")
	public Response objectActionPost(@PathParam("service") String service,
                                        @PathParam("action") String method,
                                        MultivaluedMap<String, String> form) {
        int status = 404;
        Component component = null;
        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);

        try {
            final ObjectAdapter object = Utils.findService(service);
            final ObjectAction action = Utils.findAction(object, method);

            final ObjectAdapter[] parameters = new ObjectAdapter[action.getParameterCount()];

            final List<ObjectActionParameter> parameterList = action.getParameters();
            for(int i = 0;i < action.getParameterCount() ;i++){
                ObjectActionParameter parameter = parameterList.get(i);

                if( parameter.getSpecification().isParseable() ) {
                    /* gets the form value */
                    List<String> enteredValue = form.get( parameter.getId() );
                    if( enteredValue != null ) {
                        String enteredText = enteredValue.get(0);
                        final ParseableFacet parser = parameter.getSpecification().getFacet(ParseableFacet.class);
                        /* try to parse the value */
                        Localization localization = IsisContext.getLocalization();
                        try {
                            parameters[i] = parser.parseTextEntry(parameters[i], enteredText, localization);
                        } catch (final InvalidEntryException e) {
                            e.printStackTrace();
                        } catch (final TextEntryParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
            final Consent consent = action.isProposedArgumentSetValid(object, parameters);
            if( consent.isAllowed() ) {
                final ObjectAdapter result = action.execute(object, parameters);
                component = factory.createComponent( Component.REF );
                component = component.acceptInstance( result );
                status = 200;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
              IsisContext.closeSession();
        }


		return Response.status(200).entity(component).build();
	}

}
