package ar.edu.itba.getaway.models;


import java.util.Date;

public class ReviewUserModel {
    private final long reviewId;
    private final String title;
    private final String description;
    private final long score;
    private final long experienceId;
    private final Date reviewDate;
    private final long userId;
    private final String userName;
    private final String userSurname;
    private final Long imgId;

    public ReviewUserModel(long reviewId, String title, String description, long score, long experienceId, Date reviewDate, long userId, String userName, String userSurname, Long imgId) {
        this.reviewId = reviewId;
        this.title = title;
        this.description = description;
        this.score = score;
        this.experienceId = experienceId;
        this.reviewDate = reviewDate;
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.imgId = imgId;
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
    public long getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public String getUserSurname() {
        return userSurname;
    }
    public Long getImgId() {
        return imgId;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if( !(o instanceof ReviewUserModel)){
            return false;
        }
        ReviewUserModel other = (ReviewUserModel) o;
        return this.userId == other.userId && this.reviewId == other.reviewId;
    }
}
