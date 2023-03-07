package ar.edu.itba.getaway.webapp.dto.response;

import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class RootDto implements Serializable {

    private Collection<String> usersUrl;
    private Collection<String> experiencesUrl;
    private String reviewsUrl;
    private Collection<String> locationUrl;
    private String categoryUrl;

    //TODO check estas urls de la api

    private static final Collection<String> userStrings =
            Collections.unmodifiableCollection(Arrays.asList("users", "users/currentUser", "users/{userId}", "users/{userId}/profileImage",
                    "users/{userId}/experiences?{name,order,page}", "users/{userId}/reviews?{page}",
                    "users/{userId}/favExperiences?{order,page}", "users/emailVerification", "users/passwordReset"));
    private static final Collection<String> experienceStrings =
            Collections.unmodifiableCollection(Arrays.asList("experiences", "experiences/experience/{experienceId}", "experiences/experience/{experienceId}/name",
                    "experiences/experience/{experienceId}/experienceImage", "experiences/experience/{experienceId}/reviews?{page}",
                    "experiences/experience/{experienceId}/fav?{set}", "experiences/experience/{experienceId}/observable?{set}",
                    "experiences/landingPage", "experiences/filter?{category,name,order,price,score,city,page}",
                    "experiences/filter/maxPrice?{category,name}", "experiences/filter/orderByModels?{category,name}"));
    private static final Collection<String> locationStrings =
            Collections.unmodifiableCollection(Arrays.asList("location/country", "location/country/{id}/cities", "location/cities/{cityId}"));
    private static final String reviewString = "reviews/{reviewId}";
    private static final String categoryString = "categories";

    public RootDto() {
        // Used by Jersey
    }

    public RootDto(UriInfo uriInfo) {
        final String baseUrl = uriInfo.getBaseUriBuilder().build().toString();
        usersUrl = userStrings.stream().map(s -> baseUrl + s).collect(Collectors.toList());
        experiencesUrl = experienceStrings.stream().map(s -> baseUrl + s).collect(Collectors.toList());
        reviewsUrl = baseUrl + reviewString;
        locationUrl = locationStrings.stream().map(s -> baseUrl + s).collect(Collectors.toList());
        categoryUrl = baseUrl + categoryString;
    }

    public Collection<String> getUsersUrl() {
        return usersUrl;
    }

    public void setUsersUrl(Collection<String> usersUrl) {
        this.usersUrl = usersUrl;
    }

    public Collection<String> getExperiencesUrl() {
        return experiencesUrl;
    }

    public void setExperiencesUrl(Collection<String> experiencesUrl) {
        this.experiencesUrl = experiencesUrl;
    }

    public String getReviewsUrl() {
        return reviewsUrl;
    }

    public void setReviewsUrl(String reviewsUrl) {
        this.reviewsUrl = reviewsUrl;
    }

    public Collection<String> getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(Collection<String> locationUrl) {
        this.locationUrl = locationUrl;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }
}
