package ar.edu.itba.getaway.webapp.dto.request;

import javax.validation.constraints.NotNull;

public class PutFavouriteDto {

    @NotNull(message = "NotNull")
    private Boolean favourite;

    public Boolean getFavourite() {
        return favourite;
    }
    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }
}
