package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.ReviewModel;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@XmlType(name = "")
public class ReviewDto {

    private Long id;
    private String title;
    private String description;
    private long score;
    private LocalDate reviewDate;
    private UserDto user;
    private String selfUrl;
    private String experienceUrl;

    public static Collection<ReviewDto> mapReviewToDto(Collection<ReviewModel> reviews, UriInfo uriInfo) {
        return reviews.stream().map(r -> new ReviewDto(r, uriInfo)).collect(Collectors.toList());
    }

    public static UriBuilder getReviewUriBuilder(ReviewModel review, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("reviews").path(String.valueOf(review.getReviewId()));
    }

    public ReviewDto() {
        // Used by Jersey
    }

    public ReviewDto(ReviewModel review, UriInfo uriInfo) {
        this.id = review.getReviewId();
        this.title = review.getTitle();
        this.description = review.getDescription();
        this.score = review.getScore();
        this.reviewDate = review.getReviewDate();
        this.user = new UserDto(review.getUser(), uriInfo);
        this.selfUrl = getReviewUriBuilder(review, uriInfo).build().toString();
        this.experienceUrl = uriInfo.getBaseUriBuilder().path("experience")
                .path(review.getExperience().getExperienceId().toString())
                .build().toString();    // /experience/{id}
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public UserDto getUser() {
        return user;
    }

    public long getScore() {
        return score;
    }
}
