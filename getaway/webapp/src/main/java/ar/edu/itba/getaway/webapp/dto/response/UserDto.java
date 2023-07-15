package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.UserModel;

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
    private URI selfUrl;
    private URI profileImageUrl;
    private URI experiencesUrl;
    private URI reviewsUrl;
    private URI favsUrl;
    private URI recommendationsUrl;

    public static Collection<UserDto> mapUserToDto(Collection<UserModel> users, UriInfo uriInfo) {
        return users.stream().map(u -> new UserDto(u, uriInfo)).collect(Collectors.toList());
    }

    public static UriBuilder getUserUriBuilder(UserModel user, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone().path("users").path(String.valueOf(user.getUserId()));
    }

    public UserDto() {
        // Used by Jersey
    }

    public UserDto(UserModel user, UriInfo uriInfo) {
        final UriBuilder uriBuilder = getUserUriBuilder(user, uriInfo);
        this.id = user.getUserId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.isVerified = user.isVerified();
        this.isProvider = user.isProvider();
        this.hasImage = user.getImage() != null;
        this.selfUrl = uriBuilder.clone().build();
        if (user.getProfileImage() != null) {
            this.profileImageUrl = uriBuilder.clone().path("profileImage").build();  // /user/{id}/profileImage
        }
        if(user.isProvider()) {
            this.experiencesUrl = uriBuilder.clone().path("experiences").build();    // /user/{id}/experiences
        }
        this.reviewsUrl = uriBuilder.clone().path("reviews").build();    // /user/{id}/reviews
        this.favsUrl = uriBuilder.clone().path("favExperiences").build();    // /user/{id}/favExperiences
        this.recommendationsUrl = uriBuilder.clone().path("recommendations").build();    // /user/{id}/recommendations
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
    public void setSelfUrl(URI selfUrl) {
        this.selfUrl = selfUrl;
    }
    public void setProfileImageUrl(URI profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
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
    public URI getRecommendationsUrl() {
        return recommendationsUrl;
    }
    public void setRecommendationsUrl(URI recommendationsUrl) {
        this.recommendationsUrl = recommendationsUrl;
    }
}


