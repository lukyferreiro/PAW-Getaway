//package ar.edu.itba.getaway.webapp.dto.response;
//
//import ar.edu.itba.getaway.models.ExperienceModel;
//import ar.edu.itba.getaway.models.UserModel;
//
//import javax.ws.rs.core.UriBuilder;
//import javax.ws.rs.core.UriInfo;
//import java.io.Serializable;
//import java.net.URI;
//import java.util.Collection;
//import java.util.List;
//
//public class UserRecommendationsDto implements Serializable {
//    private Collection<ExperienceDto> viewed;
//    private Collection<ExperienceDto> recommendedByFavs;
//    private Collection<ExperienceDto> recommendedByReviews;
//    private URI self;
//    private URI userUrl;
//
//    public static UriBuilder getUserUriBuilder(UserModel user, UriInfo uriInfo) {
//        return uriInfo.getBaseUriBuilder().clone().path("users").path(String.valueOf(user.getUserId()));
//    }
//
//    public UserRecommendationsDto() {
//        // Used by Jersey
//    }
//
//    public UserRecommendationsDto(List<List<ExperienceModel>> experiencesList, UserModel user, UriInfo uriInfo) {
//        final UriBuilder uriBuilder = getUserUriBuilder(user, uriInfo);
//        this.viewed = ExperienceDto.mapExperienceToDto(experiencesList.get(0), uriInfo);
//        this.recommendedByFavs = ExperienceDto.mapExperienceToDto(experiencesList.get(1), uriInfo);
//        this.recommendedByReviews = ExperienceDto.mapExperienceToDto(experiencesList.get(2), uriInfo);
//        this.self = uriBuilder.clone().path("recommendations").build();
//        this.userUrl = uriBuilder.clone().build();
//    }
//
//    public Collection<ExperienceDto> getViewed() {
//        return viewed;
//    }
//
//    public void setViewed(Collection<ExperienceDto> viewed) {
//        this.viewed = viewed;
//    }
//
//    public Collection<ExperienceDto> getRecommendedByFavs() {
//        return recommendedByFavs;
//    }
//
//    public void setRecommendedByFavs(Collection<ExperienceDto> recommendedByFavs) {
//        this.recommendedByFavs = recommendedByFavs;
//    }
//
//    public Collection<ExperienceDto> getRecommendedByReviews() {
//        return recommendedByReviews;
//    }
//
//    public void setRecommendedByReviews(Collection<ExperienceDto> recommendedByReviews) {
//        this.recommendedByReviews = recommendedByReviews;
//    }
//
//    public URI getSelf() {
//        return self;
//    }
//
//    public void setSelf(URI self) {
//        this.self = self;
//    }
//
//    public URI getUserUrl() {
//        return userUrl;
//    }
//
//    public void setUserUrl(URI userUrl) {
//        this.userUrl = userUrl;
//    }
//}
