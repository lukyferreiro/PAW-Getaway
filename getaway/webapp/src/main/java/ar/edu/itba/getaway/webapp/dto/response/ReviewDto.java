package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.ReviewModel;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

public class ReviewDto implements Serializable {

    private long id;
    private String title;
    private String description;
    private long score;
    private String date;
    private UserInfoDto user;
    private ExperienceNameDto experience;
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
        this.date = review.getReviewDate().toString();
        this.user = new UserInfoDto(review.getUser(), uriInfo);
        this.experience = new ExperienceNameDto(review.getExperience(), uriInfo);
        this.selfUrl = getReviewUriBuilder(review, uriInfo).build().toString();
        this.experienceUrl = uriInfo.getBaseUriBuilder().path("experiences").path("experience")
                .path(review.getExperience().getExperienceId().toString())
                .build().toString();    // /experiences/experience/{id}
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
    public UserInfoDto getUser() {
        return user;
    }
    public void setUser(UserInfoDto user) {
        this.user = user;
    }
    public String getSelfUrl() {
        return selfUrl;
    }
    public void setSelfUrl(String selfUrl) {
        this.selfUrl = selfUrl;
    }
    public String getExperienceUrl() {
        return experienceUrl;
    }
    public void setExperienceUrl(String experienceUrl) {
        this.experienceUrl = experienceUrl;
    }
    public ExperienceNameDto getExperience() {
        return experience;
    }
    public void setExperience(ExperienceNameDto experience) {
        this.experience = experience;
    }


}
