package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.controller.queryParamsValidators.GetExperiencesFilter;

import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;

public class PublicUserDto implements Serializable {

    private long id;
    private String name;
    private String surname;
    private boolean hasImage;
    private URI self;
    private URI profileImageUrl;
    private URI favsUrl;
    private URI viewedUrl;
    private URI recommendationsByFavsUrl;

    public PublicUserDto() {
        // Used by Jersey
    }

    public PublicUserDto(UserModel user, UriInfo uriInfo) {
        this.id = user.getUserId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.hasImage = user.getImage() != null;
        this.self = uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(user.getUserId())).build();
        if (user.getProfileImage() != null) {
            this.profileImageUrl = uriInfo.getBaseUriBuilder().path("users")
                    .path(String.valueOf(user.getUserId())).path("profileImage").build();
        }
        this.favsUrl = uriInfo.getBaseUriBuilder().path("experiences")
                .queryParam("filter", GetExperiencesFilter.FAVS.toString())
                .queryParam("userId", user.getUserId()).build();
        this.recommendationsByFavsUrl = uriInfo.getBaseUriBuilder().path("experiences")
                .queryParam("filter", GetExperiencesFilter.RECOMMENDED_BY_FAVS.toString())
                .queryParam("userId", user.getUserId()).build();
        this.viewedUrl = uriInfo.getBaseUriBuilder().path("experiences")
                .queryParam("filter", GetExperiencesFilter.VIEWED.toString())
                .queryParam("userId", user.getUserId()).build();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public boolean isHasImage() {
        return hasImage;
    }
    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }
    public URI getSelf() {
        return self;
    }
    public void setSelf(URI self) {
        this.self = self;
    }
    public URI getProfileImageUrl() {
        return profileImageUrl;
    }
    public void setProfileImageUrl(URI profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    public URI getFavsUrl() {
        return favsUrl;
    }
    public void setFavsUrl(URI favsUrl) {
        this.favsUrl = favsUrl;
    }
    public URI getViewedUrl() {
        return viewedUrl;
    }
    public void setViewedUrl(URI viewedUrl) {
        this.viewedUrl = viewedUrl;
    }
    public URI getRecommendationsByFavsUrl() {
        return recommendationsByFavsUrl;
    }
    public void setRecommendationsByFavsUrl(URI recommendationsByFavsUrl) {
        this.recommendationsByFavsUrl = recommendationsByFavsUrl;
    }
}


