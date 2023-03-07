package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.ExperienceModel;

import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class UserLandingPageDto implements Serializable {
    private Collection<ExperienceDto> viewed;
    private Collection<ExperienceDto> recommendedByFavs;
    private Collection<ExperienceDto> recommendedByReviews;

    public UserLandingPageDto() {
        // Used by Jersey
    }

    public UserLandingPageDto(List<List<ExperienceModel>> experiencesList, UriInfo uriInfo) {
        this.viewed = ExperienceDto.mapExperienceToDto(experiencesList.get(0), uriInfo)  ;
        this.recommendedByFavs = ExperienceDto.mapExperienceToDto(experiencesList.get(1), uriInfo);
        this.recommendedByReviews = ExperienceDto.mapExperienceToDto(experiencesList.get(2), uriInfo);
    }

    public Collection<ExperienceDto> getViewed() {
        return viewed;
    }

    public void setViewed(Collection<ExperienceDto> viewed) {
        this.viewed = viewed;
    }

    public Collection<ExperienceDto> getRecommendedByFavs() {
        return recommendedByFavs;
    }

    public void setRecommendedByFavs(Collection<ExperienceDto> recommendedByFavs) {
        this.recommendedByFavs = recommendedByFavs;
    }

    public Collection<ExperienceDto> getRecommendedByReviews() {
        return recommendedByReviews;
    }

    public void setRecommendedByReviews(Collection<ExperienceDto> recommendedByReviews) {
        this.recommendedByReviews = recommendedByReviews;
    }
}
