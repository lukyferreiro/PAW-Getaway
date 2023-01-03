package ar.edu.itba.getaway.webapp.dto;

import ar.edu.itba.getaway.models.UserModel;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URL;

public class UserDto {

    private long id;
    private String username;
    //No tengo que embeber otras entidades que tiene su propio manejo en la API
    //por eso agrego un hipervinculo a las entidades para asociarlas
    private URI self;
    private URI assignedIssues;

    public UserDto() {
        // Used by Jersey
    }

    public UserDto(UserModel user, UriInfo uriInfo) {
        this.id = user.getUserId();
        this.username = user.getName();
    }

    public static UserDto fromUser(final UserModel userModel, final UriInfo uriInfo){
        final UserDto dto = new UserDto();
        dto.username = userModel.getName();
        dto.self = uriInfo.getAbsolutePathBuilder().replacePath("users")
                .path(String.valueOf(userModel.getUserId())).build();
        //...
        return dto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
