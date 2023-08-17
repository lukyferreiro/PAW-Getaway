package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.controller.queryParamsValidators.GetExperiencesFilter;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserDto implements Serializable {

    private long id;
    private String name;
    private String surname;
    private String email;
    private boolean isVerified;
    private boolean isProvider;
    private boolean hasImage;
    private URI self;
    private URI profileImageUrl;
    private URI experiencesUrl;
    private URI reviewsUrl;
    private URI favsUrl;
    private URI viewedUrl;
    private URI recommendationsByFavsUrl;
    private URI recommendationsByReviewsUrl;
//    private URI emailTokenUrl;  //TODO check
//    private URI passwordTokenUrl;   //TODO check

    public UserDto() {
        // Used by Jersey
    }

    public UserDto(UserModel user, UriInfo uriInfo) {
        this.id = user.getUserId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.isVerified = user.isVerified();
        this.isProvider = user.isProvider();
        this.hasImage = user.getImage() != null;
        this.self = uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(user.getUserId())).build();
        if (user.getProfileImage() != null) {
            this.profileImageUrl = uriInfo.getBaseUriBuilder().path("users")
                    .path(String.valueOf(user.getUserId())).path("profileImage").build();
        }
        if(user.isProvider()) {
            this.experiencesUrl = uriInfo.getBaseUriBuilder().path("experiences")
                    .queryParam("filter", GetExperiencesFilter.PROVIDER.toString())
                    .queryParam("userId", user.getUserId()).build();
        }
        this.reviewsUrl = uriInfo.getBaseUriBuilder().path("reviews")
                .queryParam("userId", user.getUserId()).build();
        this.favsUrl = uriInfo.getBaseUriBuilder().path("experiences")
                .queryParam("filter", GetExperiencesFilter.FAVS.toString())
                .queryParam("userId", user.getUserId()).build();
        this.viewedUrl = uriInfo.getBaseUriBuilder().path("experiences")
                .queryParam("filter", GetExperiencesFilter.VIEWED.toString())
                .queryParam("userId", user.getUserId()).build();
        this.recommendationsByFavsUrl = uriInfo.getBaseUriBuilder().path("experiences")
                .queryParam("filter", GetExperiencesFilter.RECOMMENDED_BY_FAVS.toString())
                .queryParam("userId", user.getUserId()).build();
        this.recommendationsByReviewsUrl = uriInfo.getBaseUriBuilder().path("experiences")
                .queryParam("filter", GetExperiencesFilter.RECOMMENDED_BY_REVIEWS.toString())
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isVerified() {
        return isVerified;
    }
    public void setVerified(boolean verified) {
        isVerified = verified;
    }
    public boolean isProvider() {
        return isProvider;
    }
    public void setProvider(boolean provider) {
        isProvider = provider;
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
    public URI getExperiencesUrl() {
        return experiencesUrl;
    }
    public void setExperiencesUrl(URI experiencesUrl) {
        this.experiencesUrl = experiencesUrl;
    }
    public URI getReviewsUrl() {
        return reviewsUrl;
    }
    public void setReviewsUrl(URI reviewsUrl) {
        this.reviewsUrl = reviewsUrl;
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
    public URI getRecommendationsByReviewsUrl() {
        return recommendationsByReviewsUrl;
    }
    public void setRecommendationsByReviewsUrl(URI recommendationsByReviewsUrl) {
        this.recommendationsByReviewsUrl = recommendationsByReviewsUrl;
    }
}


