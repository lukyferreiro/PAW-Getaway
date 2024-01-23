package ar.edu.itba.getaway.webapp.dto.response;

import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;

public class FavouriteDto implements Serializable {
    private boolean isFavourite;
    private URI self;


    public FavouriteDto() {
        // Used by Jersey
    }

    public FavouriteDto(boolean isFavourite, long userId, long experienceId, UriInfo uriInfo) {
        this.isFavourite = isFavourite;
        this.self = uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(userId)).path("favourites").path(String.valueOf(experienceId)).build();;
    }

    public boolean isFavourite() {
        return isFavourite;
    }
    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
    public URI getSelf() {
        return self;
    }
    public void setSelf(URI self) {
        this.self = self;
    }
}
