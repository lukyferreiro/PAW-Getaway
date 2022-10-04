package ar.edu.itba.getaway.models;

import java.util.Date;

public class ReviewModel {
    private final Long reviewId;
    private final String title;
    private final String description;
    private final Long score;
    private final Long experienceId;
    //TODO change tipo DATE
    private final Date reviewDate;
    private final Long userId;

    public ReviewModel(Long reviewId, String title, String description, Long score, Long experienceId, Date reviewDate, Long userId) {
        this.reviewId = reviewId;
        this.title = title;
        this.description = description;
        this.score = score;
        this.experienceId = experienceId;
        this.reviewDate = reviewDate;
        this.userId=userId;
    }

    public Long getReviewId() {
        return reviewId;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Long getScore() {
        return score;
    }
    public String getStringScore() {
        return String.valueOf(score);
    }
    public Long getExperienceId() {
        return experienceId;
    }
    public Date getReviewDate() {
        return reviewDate;
    }
    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof ReviewModel)){
            return false;
        }
        ReviewModel other = (ReviewModel) o;
        return this.reviewId.equals(other.reviewId);
    }
}
