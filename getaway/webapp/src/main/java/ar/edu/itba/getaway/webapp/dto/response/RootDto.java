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
    private Collection<String> reviewsUrl;
    private Collection<String> locationUrl;
    private Collection<String> categoryUrl;

    //TODO check estas urls de la api

    private static final Collection<String> userStrings =
            Collections.unmodifiableCollection(Arrays.asList("users", "users/{userId}", "users/{userId}/profileImage",
                    "users/{userId}/experiences?{name,order,page}", "users/{userId}/reviews?{page}",
                    "users/{userId}/favExperiences?{order,page}", "users/{userId}/recommendations",
                    "users/emailToken", "users/passwordToken"));
    private static final Collection<String> experienceStrings =
            Collections.unmodifiableCollection(Arrays.asList("experiences", "experiences/{experienceId}", "experiences/{experienceId}/name",
                    "experiences/{experienceId}/experienceImage", "experiences/{experienceId}/reviews?{page}",
                    "experiences/{experienceId}/fav?{set}", "experiences/{experienceId}/observable?{set}",
                    "experiences?{category,name,order,price,score,city,page}",
                    "experiences/maxPrice?{category,name}", "experiences/orders?{category,name}"));
    private static final Collection<String> locationStrings =
            Collections.unmodifiableCollection(Arrays.asList("location/country", "location/country/{countryId}",
                    "location/country/{countryId}/cities", "location/cities/{cityId}"));
    private static final Collection<String> reviewString =
            Collections.unmodifiableCollection(Arrays.asList("reviews/","reviews/{reviewId}"));
    private static final Collection<String> categoryString =
            Collections.unmodifiableCollection(Arrays.asList("categories/","categories/{categoryId}"));

    public RootDto() {
        // Used by Jersey
    }

    public RootDto(UriInfo uriInfo) {
        final String baseUrl = uriInfo.getBaseUriBuilder().build().toString();
        usersUrl = userStrings.stream().map(s -> baseUrl + s).collect(Collectors.toList());
        experiencesUrl = experienceStrings.stream().map(s -> baseUrl + s).collect(Collectors.toList());
        reviewsUrl = reviewString.stream().map(s -> baseUrl + s).collect(Collectors.toList());
        locationUrl = locationStrings.stream().map(s -> baseUrl + s).collect(Collectors.toList());
        categoryUrl = categoryString.stream().map(s -> baseUrl + s).collect(Collectors.toList());

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

    public Collection<String> getReviewsUrl() {
        return reviewsUrl;
    }

    public void setReviewsUrl(Collection<String> reviewsUrl) {
        this.reviewsUrl = reviewsUrl;
    }

    public Collection<String> getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(Collection<String> locationUrl) {
        this.locationUrl = locationUrl;
    }

    public Collection<String> getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(Collection<String> categoryUrl) {
        this.categoryUrl = categoryUrl;
    }
}
