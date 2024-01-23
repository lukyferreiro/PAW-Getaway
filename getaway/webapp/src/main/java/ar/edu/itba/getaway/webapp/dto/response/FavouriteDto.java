package ar.edu.itba.getaway.webapp.dto.response;

import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;

public class FavouriteDto implements Serializable {
    private boolean favourite;
    private URI self;


    public FavouriteDto() {
        // Used by Jersey
    }

    public FavouriteDto(boolean favourite, long userId, long experienceId, UriInfo uriInfo) {
        this.favourite = favourite;
        this.self = uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(userId)).path("favourites").path(String.valueOf(experienceId)).build();;
    }

    public boolean getFavourite() {
        return favourite;
    }
    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
    public URI getSelf() {
        return self;
    }
    public void setSelf(URI self) {
        this.self = self;
    }
}
