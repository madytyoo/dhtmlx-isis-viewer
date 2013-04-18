package com.mylaensys.isis.viewer.dhtmlx.desktop;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.mylaensys.isis.dsl.dhtmlx.ParserHelper;
import com.mylaensys.isis.viewer.dhtmlx.Component;
import com.mylaensys.isis.viewer.dhtmlx.desktop.component.ContainerForm;
import com.mylaensys.isis.viewer.dhtmlx.desktop.component.Errors;
import com.mylaensys.isis.viewer.dhtmlx.desktop.component.Form;
import com.mylaensys.isis.viewer.dhtmlx.desktop.component.FormItem;
import com.mylaensys.isis.viewer.dhtmlx.servlet.utils.Utils;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.profiles.Localization;
import org.apache.isis.core.commons.authentication.AnonymousSession;
import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.adapter.oid.RootOidDefault;
import org.apache.isis.core.metamodel.consent.Consent;
import org.apache.isis.core.metamodel.facets.object.parseable.InvalidEntryException;
import org.apache.isis.core.metamodel.facets.object.parseable.ParseableFacet;
import org.apache.isis.core.metamodel.facets.object.parseable.TextEntryParseException;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.*;
import org.apache.isis.core.runtime.system.context.IsisContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.List;
import org.apache.log4j.Logger;

@Path("/objects")
public class ViewerObject extends AbstractService {
     private static final Logger log = Logger.getLogger(ViewerObject.class.getName());

    /**
     * Obtain a view for object ID
     *
     * @param object
     * @param id
     * @return
     */
    @GET
    @Path("/object/{object}/{id}")
    @Produces("application/xml")
    public Response viewObjectInstance(@PathParam("object") String object,
                                       @PathParam("id") String id,
                                       @Context HttpServletRequest request) {
        Component component = null;

        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);

        try {
            ObjectAdapter instance = IsisContext.getPersistenceSession().getAdapterManager().adapterFor(RootOidDefault.deString(id, IsisContext.getOidMarshaller()));

            String code = loadDSL(object, "view", request);
            if (code.length() > 0) {
                ParserHelper parser = new ParserHelper();
                component = (Component) parser.parseForObjectInstance(code, instance);
            } else {

            }
        } catch (Exception e) {
            log.error("viewObjectInstance exception", e);
        } finally {
            IsisContext.closeSession();
        }


        return Response.status(200).entity(component).build();
    }

    /**
     * Obtain a view for object ID
     *
     * @param object
     * @param id
     * @return
     */
    @GET
    @Path("/object/{object}/{id}/collections/{collection}")
    @Produces("application/xml")
    public Response viewObjectCollection(@PathParam("object") String object, @PathParam("id") String id, @PathParam("collection") String collection, @Context UriInfo info) {
        Component component = null;

        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);

        try {
            String dhx = info.getQueryParameters().getFirst("dhx");
            dhx = (dhx == null ? Component.GRID : dhx);

            ObjectAdapter instance = IsisContext.getPersistenceSession().getAdapterManager().adapterFor(RootOidDefault.deString(id, IsisContext.getOidMarshaller()));
            component = factory.createComponent(dhx);

            String columns = info.getQueryParameters().getFirst("columns");
            if (columns != null) {
                component.setParameter("columns", columns);
            }

            component = component.acceptInstance(instance, collection);
        } catch (Exception e) {
            log.error("viewObjectInstance exception", e);
        } finally {
            IsisContext.closeSession();
        }


        return Response.status(200).entity(component).build();
    }

    /**
     * Action prompt
     *
     * @param object
     * @param method
     * @return
     */
    @GET
    @Path("/object/{object}/{id}/actions/{action}")
    @Produces("application/xml")
    public Response objectActionPrompt(@PathParam("object") String object,
                                       @PathParam("id") String id,
                                       @PathParam("action") String method,
                                       @Context HttpServletRequest request) {

        Component component = null;
        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);


        try {
            ObjectAdapter instance = IsisContext.getPersistenceSession().getAdapterManager().adapterFor(RootOidDefault.deString(id, IsisContext.getOidMarshaller()));
            final ObjectAction action = Utils.findAction(instance, method);

            /* The service prompts for parameters */
            if (action.promptForParameters(instance)) {
                String code = loadDSL(object, method,request);
                if (code.length() > 0) {
                    ParserHelper parser = new ParserHelper();
                    component = (Component) parser.parseForServicePrompt(code, object, method);

                } else {
                    Form form = (Form) factory.createComponent(Component.FORM);
                    form = (Form) form.acceptObject(instance, action);
                    /* PATCH : the _ref must point to the object instance */
                    String oid = Utils.computeOid(instance.getOid().enString(IsisContext.getOidMarshaller()));
                    FormItem ref = form.get("_ref");
                    ref.setValue("dhx/objects/object/" + instance.getSpecification().getFullIdentifier() + "/" + oid + "/actions/" + method);

                    component = new ContainerForm(form);
                }
            }

        } catch (Exception e) {
            log.error("viewObjectInstance exception", e);
        } finally {
            IsisContext.closeSession();
        }

        return Response.status(200).entity(component).build();

    }

    /**
     * Action invocation for post
     *
     * @param service
     * @param method
     * @return
     */
    @POST
    @Path("/object/{object}/{id}/actions/{action}/invoke")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/xml")
    public Response serviceActionPostForm(@PathParam("object") String service,
                                          @PathParam("id") String id,
                                          @PathParam("action") String method,
                                          MultivaluedMap<String, String> form) {
        int status = 403;
        Component component = null;
        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);


        try {
            final ObjectAdapter instance = IsisContext.getPersistenceSession().getAdapterManager().adapterFor(RootOidDefault.deString(id, IsisContext.getOidMarshaller()));

            final ObjectAction action = Utils.findAction(instance, method);

            final List<ObjectActionParameter> parameterList = action.getParameters();

            final ObjectAdapter[] parameters = new ObjectAdapter[action.getParameterCount()];
            for (int i = 0; i < action.getParameterCount(); i++) {
                ObjectActionParameter parameter = parameterList.get(i);

                if (parameter.getSpecification().isParseable()) {
                    /* gets the form value */
                    List<String> enteredValue = form.get(parameter.getId());
                    if (enteredValue != null) {
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
                } else {
                    List<String> enteredValue = form.get(parameter.getId());
                    if (enteredValue != null) {
                        String enteredText = enteredValue.get(0);
                        parameters[i] = IsisContext.getPersistenceSession().getAdapterManager().adapterFor(RootOidDefault.deString(enteredText, IsisContext.getOidMarshaller()));
                    }
                }

            }
            final Consent consent = action.isProposedArgumentSetValid(instance, parameters);
            if (consent.isAllowed()) {
                final ObjectAdapter result = action.execute(instance, parameters);
                component = factory.createComponent(Component.REF);
                component = component.acceptInstance(result);
                status = 200;
            } else {
                Errors errors = (Errors) factory.createComponent(Component.ERRORS);
                errors.add(consent.getReason());
                component = errors;
            }


        } catch (Exception e) {
            log.error("viewObjectInstance exception", e);
        } finally {
            IsisContext.closeSession();
        }


        return Response.status(status).entity(component).build();
    }

    /**
     * Direct action invocation for post
     *
     * @param service
     * @param method
     * @return
     */
    @POST
    @Path("/object/{object}/{id}/actions/{action}/invoke")
    @Produces("application/xml")
    public Response serviceActionPost(@PathParam("object") String service,
                                      @PathParam("id") String id,
                                      @PathParam("action") String method) {
        int status = 404;
        Component component = null;
        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);


        try {
            final ObjectAdapter instance = IsisContext.getPersistenceSession().getAdapterManager().adapterFor(RootOidDefault.deString(id, IsisContext.getOidMarshaller()));
            final ObjectAction action = Utils.findAction(instance, method);

            final List<ObjectActionParameter> parameterList = action.getParameters();
            final ObjectAdapter[] parameters = new ObjectAdapter[action.getParameterCount()];
            for (int i = 0; i < action.getParameterCount(); i++) {
                ObjectActionParameter parameter = parameterList.get(i);
                if (parameter.getSpecification().isParseable()) {

                }

            }

            final Consent consent = action.isProposedArgumentSetValid(instance, parameters);
            if (consent.isAllowed()) {
                final ObjectAdapter result = action.execute(instance, parameters);
                component = factory.createComponent(Component.REF);
                component = component.acceptInstance(result);
                status = 200;
            }


        } catch (Exception e) {
            log.error("viewObjectInstance exception", e);
        } finally {
            IsisContext.closeSession();
        }


        return Response.status(status).entity(component).build();
    }


    /**
     * Returns the object actions
     */
    @GET
    @Path("/object/{object}/{id}/actions")
    @Produces("application/xml")
    public Response objectActions(@PathParam("object") String object, @PathParam("id") String id) {
        Component component = null;
        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);


        try {
            final ObjectAdapter instance = IsisContext.getPersistenceSession().getAdapterManager().adapterFor(RootOidDefault.deString(id, IsisContext.getOidMarshaller()));

            component = factory.createComponent(Component.MENU);
            component = component.acceptInstance(instance);
        } catch (Exception e) {
            log.error("viewObjectInstance exception", e);
        } finally {
            IsisContext.closeSession();
        }


        return Response.status(200).entity(component).build();


    }

    /**
     * Action invocation for save an object
     *
     * @param service
     * @param method
     * @return
     */
    @POST
    @Path("/object/{object}/{id}")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/xml")
    public Response serviceSavePostForm(@PathParam("object") String object,
                                        @PathParam("id") String id,
                                        MultivaluedMap<String, String> form) {
        int status = 403;
        Component component = null;
        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);


        try {
            IsisContext.getPersistenceSession().getTransactionManager().startTransaction();
            final ObjectAdapter instance = IsisContext.getPersistenceSession().getAdapterManager().adapterFor(RootOidDefault.deString(id, IsisContext.getOidMarshaller()));

            final ObjectSpecification specification = instance.getSpecification();
            final List<ObjectAssociation> fields = specification.getAssociations(ObjectAssociationFilters.dynamicallyVisible(IsisContext.getAuthenticationSession(), instance, Where.NOWHERE));
            for (ObjectAssociation field : fields) {
                if (field.getSpecification().isParseable()) {
                    List<String> enteredValue = form.get(field.getId());
                    if (enteredValue != null) {
                        String enteredText = enteredValue.get(0);
                        String javaType = field.getSpecification().getFullIdentifier();
                        if (javaType.endsWith("boolean")) {

                        }
                        final ParseableFacet parser = field.getSpecification().getFacet(ParseableFacet.class);
                        /* try to parse the value */
                        Localization localization = IsisContext.getLocalization();
                        try {
                            ObjectAdapter value = parser.parseTextEntry(field.get(instance), enteredText, localization);
                            if (field.isOneToOneAssociation()) {
                                OneToOneAssociation oneToOneAssociation = ((OneToOneAssociation) field);
                                oneToOneAssociation.set(instance, value);
                            }
                        } catch (final InvalidEntryException e) {
                            e.printStackTrace();
                        } catch (final TextEntryParseException e) {
                            e.printStackTrace();
                        }

                    }

                } else if (field.getSpecification().getFullIdentifier().endsWith("BlobKey")) {
                    /* Only the first one */
                    List<String> original = form.get(field.getId() + "_r_0");
                    List<String> server = form.get(field.getId() + "_s_0");
                    if (server != null && original != null) {
                        String serverName = server.get(0);
                        String originalName = original.get(0);
                        FileService fileService = FileServiceFactory.getFileService();
                        AppEngineFile file = new AppEngineFile(serverName);
                        BlobKey blobKey = fileService.getBlobKey(file);

                        BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());
                        BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);

                        //BlobstoreService blobStoreService = BlobstoreServiceFactory.getBlobstoreService();
                        //byte[] bytes = blobStoreService.fetchData(blobKey, 0, blobInfo.getSize() );

                        //com.google.appengine.api.datastore.Blob blob = new com.google.appengine.api.datastore.Blob(bytes);
                        //org.apache.isis.applib.value.Blob blob = new Blob( originalName , blobInfo.getContentType() , bytes );
                        OneToOneAssociation oneToOneAssociation = ((OneToOneAssociation) field);
                        ObjectAdapter value = IsisContext.getPersistenceSession().getAdapterManager().adapterFor(blobKey);
                        oneToOneAssociation.set(instance, value);

                    }

                }

            }
            final Consent consent = instance.getSpecification().isValid(instance);
            if (consent.isAllowed()) {
                component = factory.createComponent(Component.REF);
                component = component.acceptInstance(instance);
                status = 200;
            } else {
                Errors errors = (Errors) factory.createComponent(Component.ERRORS);
                errors.add(consent.getReason());
                component = errors;
            }
        } catch (Exception e) {
            log.error("viewObjectInstance exception", e);
        } finally {
            IsisContext.getPersistenceSession().getTransactionManager().endTransaction();
            IsisContext.closeSession();
        }


        return Response.status(status).entity(component).build();
    }

    @POST
    @Path("/object/{object}/{id}/upload/{attribute}")
    public Response uploadFile(@PathParam("object") String object, @PathParam("id") String id, @PathParam("attribute") String attribute,
                               @Context HttpServletRequest request, @Context HttpServletResponse response) {

        log.info("Uploading");
        try {

            ServletFileUpload upload = new ServletFileUpload();
            response.setContentType("text/plain");


            FileItemIterator iterator = upload.getItemIterator(request);
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                InputStream stream = item.openStream();

                if (item.isFormField()) {
                    log.info("Got a form field: " + item.getFieldName());
                } else {
                    log.info("Got an uploaded file: " + item.getFieldName() + ", name = " + item.getName());

                    /* From google */
                    FileService fileService = FileServiceFactory.getFileService();
                    AppEngineFile file = fileService.createNewBlobFile(item.getContentType());

                    boolean lock = true;
                    FileWriteChannel writeChannel = fileService.openWriteChannel(file, lock);

                    // Different standard Java ways of writing to the channel
                    // are possible. Here we use a PrintWriter:
                    PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel, "UTF8"));

                    String path = file.getFullPath();

                    // Write more to the file in a separate request:
                    file = new AppEngineFile(path);



                    // You now have the filename (item.getName() and the
                    // contents (which you can read from stream). Here we just
                    // print them back out to the servlet output stream, but you
                    // will probably want to do something more interesting (for
                    // example, wrap them in a Blob and commit them to the
                    // datastore).
                    int len;
                    byte[] buffer = new byte[8192];
                    while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
                        // This time we write to the channel directly
                        writeChannel.write(ByteBuffer.wrap(buffer,0,len));

                    }
                    // Now finalize
                    writeChannel.closeFinally();

                    return Response.status(200).entity("{state: true, name:'" + file.getFullPath() + "'}").build();
                    //response.getOutputStream().println();
                }
            }
        } catch (Exception e) {
            log.error("uploadFile exception", e);
        }
        return Response.status(200).entity("").build();
    }

    /**
     * Obtain a view for object ID
     *
     * @param object
     * @param id
     * @return
     */
    @GET
    @Path("/object/{object}/{id}/property/{property}")
    public Response viewObjectProperty(@PathParam("object") String object, @PathParam("id") String id, @PathParam("property") String property, @Context HttpServletResponse response) {
        Component component = null;

        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);

        try {

            ObjectAdapter instance = IsisContext.getPersistenceSession().getAdapterManager().adapterFor(RootOidDefault.deString(id, IsisContext.getOidMarshaller()));
            final ObjectSpecification specification = instance.getSpecification();
            final List<ObjectAssociation> fields = specification.getAssociations(ObjectAssociationFilters.dynamicallyVisible(IsisContext.getAuthenticationSession(), instance, Where.NOWHERE));
            for (ObjectAssociation field : fields) {
                if (property.equalsIgnoreCase(field.getId())) {
                    if (field.getSpecification().getFullIdentifier().endsWith("BlobKey")) {
                        final ObjectAdapter associatedObject = field.get(instance);
                        if (associatedObject != null) {
                            BlobKey blobKey = (BlobKey) associatedObject.getObject();

                            BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());
                            BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
                            BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

                            byte[] image = blobstoreService.fetchData(blobKey, 0, blobInfo.getSize());

                            return Response.status(200).type(blobInfo.getContentType()).entity( image ).build();

                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error("viewObjectProperty exception", e);
        } finally {
            IsisContext.closeSession();
        }
        return Response.status(200).entity("").build();
    }

}
