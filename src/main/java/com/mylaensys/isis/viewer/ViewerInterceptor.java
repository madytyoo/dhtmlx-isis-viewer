package com.mylaensys.isis.viewer;

import org.apache.isis.core.commons.authentication.AnonymousSession;
import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

@Provider
@ServerInterceptor
public class ViewerInterceptor implements PreProcessInterceptor,PostProcessInterceptor{

    @Override
    public ServerResponse preProcess(HttpRequest httpRequest, ResourceMethod resourceMethod) throws Failure, WebApplicationException {
        AuthenticationSession session = new AnonymousSession();
        IsisContext.openSession(session);
        return null;
    }
    @Override
    public void postProcess(ServerResponse serverResponse) {
        IsisContext.closeSession();
    }

}
