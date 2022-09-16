package ar.edu.itba.getaway.models;

import java.util.Date;

public class ReviewModel {
    private final long reviewId;
    private final String title;
    private final String description;
    private final long score;
    private final long experienceId;
    private final Date reviewDate;

    public ReviewModel(long reviewId, String title, String description, long score, long experienceId, Date reviewDate) {
        this.reviewId = reviewId;
        this.title = title;
        this.description = description;
        this.score = score;
        this.experienceId = experienceId;
        this.reviewDate = reviewDate;
    }

    public long getReviewId() {
        return reviewId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getScore() {
        return score;
    }

    public long getExperienceId() {
        return experienceId;
    }

    public Date getReviewDate() {
        return reviewDate;
    }
}
