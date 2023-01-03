package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

//Este controller se encarga de los recursos de "users"
@Path("users")
@Component
public class UserControllerFinal {

    @Autowired
    private UserService us;
    @Context
    private UriInfo uriInfo;

    //Un GET a /users
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response listUsers() {
        final List<UserDto> allUsers = us.getAll()
                .stream.map(user -> UserDto.fromUser(user, uriInfo)).collect(Collectors.toList());
        if(allUsers.isEmpty())
            return Response.noContent().build();
        return Response.ok(new GenericEntity<List<UserDto>>(allUsers) {})
                .link("", "blabla")
                .build();
        return null;
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response createUser(@QueryParam("password") final String pass, final UserDto userDto) {
        final UserModel user = us.createUser(pass, userDto.getUsername(), "ss", "ss");
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getUserId())).build();
        return Response.created(uri).build();
        //return null;
    }

    //GET /users/{id}
    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getById(@PathParam("id") final long id) {
        final UserModel user = us.getById(id);
        if (user != null) {
            return Response.ok(new UserDto(user)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response deleteById(@PathParam("id") final long id) {
//        us.deleteById(id);
        return Response.noContent().build();
    }
}