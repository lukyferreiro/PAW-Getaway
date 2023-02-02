package ar.edu.itba.getaway.webapp.controller.apiControllers;

import ar.edu.itba.getaway.webapp.dto.response.RootDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class RootController {

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listEndpoints() {
        return Response.ok(new RootDto(uriInfo)).build();
    }

}
