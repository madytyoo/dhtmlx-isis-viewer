package com.mylaensys.isis.viewer.dhtmlx.desktop;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/skin")
public class ViewerSkin {
    @GET
	@Path("/{skin}")
    @Produces("application/text")
	public Response changeSkin(@PathParam("skin") String skin,@Context HttpServletRequest request) {
        request.getSession().setAttribute("skin",skin);

        return Response.status(200).entity(skin).build();
    }

}
