package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

public class ReviewDto implements Serializable {

    private long id;
    private String title;
    private String description;
    private long score;
    private String date;
    private URI self;
    private URI userUrl;
    private URI experienceUrl;
    private URI userImage;

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
        final UriBuilder uriBuilder = getReviewUriBuilder(review, uriInfo);
        this.id = review.getReviewId();
        this.title = review.getTitle();
        this.description = review.getDescription();
        this.score = review.getScore();
        this.date = review.getReviewDate().toString();
        this.self = uriBuilder.clone().build();
        this.userUrl = uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(review.getUser().getUserId())).build();
        this.experienceUrl = uriInfo.getBaseUriBuilder().path("experiences").path(String.valueOf(review.getExperience().getExperienceId())).build();;
        final UserModel user = review.getUser();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getScore() {
        return score;
    }
    public void setScore(long score) {
        this.score = score;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public URI getSelf() {
        return self;
    }
    public void setSelf(URI self) {
        this.self = self;
    }
    public URI getUserUrl() {
        return userUrl;
    }
    public void setUserUrl(URI userUrl) {
        this.userUrl = userUrl;
    }
    public URI getExperienceUrl() {
        return experienceUrl;
    }
    public void setExperienceUrl(URI experienceUrl) {
        this.experienceUrl = experienceUrl;
    }
    public URI getUserImage() {
        return userImage;
    }
    public void setUserImage(URI userImage) {
        this.userImage = userImage;
    }
}
