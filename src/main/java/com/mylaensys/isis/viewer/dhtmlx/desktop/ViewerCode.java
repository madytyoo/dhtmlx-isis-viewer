package com.mylaensys.isis.viewer.dhtmlx.desktop;

import com.mylaensys.isis.dsl.dhtmlx.ParserError;
import com.mylaensys.isis.dsl.dhtmlx.ParserErrorList;
import com.mylaensys.isis.dsl.dhtmlx.ParserHelper;
import org.apache.isis.core.commons.authentication.AnonymousSession;
import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.adapter.oid.RootOidDefault;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/code")
public class ViewerCode extends AbstractService {
    private static final Logger log = Logger.getLogger(ViewerObject.class.getName());


    /**
     * Obtain a view for a service
     *
     * @param object
     * @param action
     * @return
     */
    @GET
	@Path("/services/{service}/actions/{action}")
    @Produces("application/text")
	public Response viewServiceCode(@PathParam("service") String object,
                                    @PathParam("action") String action,
                                    @Context HttpServletRequest request) {

        String code = loadDSL(object,action, request);
        code = code.replaceAll("\r","");

        return Response.status(200).entity(code).build();
    }

    /**
     * Obtain a view for object ID
     *
     * @param object
     * @param id
     * @return
     */
    @GET
	@Path("/objects/object/{object}/{id}")
    @Produces("application/text")
	public Response viewObjectCode(@PathParam("object") String object,
                                   @PathParam("id") String id,
                                   @Context HttpServletRequest request) {

        String code = loadDSL(object,"view", request);
        code = code.replaceAll("\r","");

        return Response.status(200).entity(code).build();
    }
    /**
     * Obtain a view for object ID
     *
     * @param object
     * @param id
     * @return
     */
    @POST
	@Path("/objects/object/{object}/{id}")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/xml")
	public Response saveObjectCode(@PathParam("object") String object,
                                   @PathParam("id") String id,
                                   @Context HttpServletRequest request,
                                   MultivaluedMap<String, String> form) {


        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);

        try {
            ObjectAdapter instance = IsisContext.getPersistenceSession().getAdapterManager().adapterFor(RootOidDefault.deString(id, IsisContext.getOidMarshaller()));

            List<String> enteredValue = form.get("code");
            if (enteredValue != null) {
                String code = enteredValue.get(0);
                if (code.length() > 0) {
                    ParserHelper parser = new ParserHelper();
                    parser.parseForObjectInstance(code, instance);

                    List<ParserError> errorList = parser.getErrors();
                    if( errorList.size() == 0 ) {
                        saveDSL(object,"view",code,request);
                    }
                    ParserErrorList errors = new ParserErrorList( errorList );



                    return Response.status(200).entity(errors).build();
                }
            }

        } catch (Exception e) {
            log.error("saveObjectCode exception", e);
        } finally {
            IsisContext.closeSession();
        }

        return Response.status(200).entity("").build();
    }

}
