package ar.edu.itba.getaway.models;


import java.util.Date;

public class ReviewUserModel {
    private final Long reviewId;
    private final String title;
    private final String description;
    private final Long score;
    private final Long experienceId;
    //TODO cambiar tipo DATE
    private final Date reviewDate;
    private final Long userId;
    private final String userName;
    private final String userSurname;
    private final Long imgId;

    public ReviewUserModel(Long reviewId, String title, String description, Long score, Long experienceId, Date reviewDate, Long userId, String userName, String userSurname, Long imgId) {
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
    public Long getExperienceId() {
        return experienceId;
    }
    public Date getReviewDate() {
        return reviewDate;
    }
    public Long getUserId() {
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
        return this.userId.equals(other.userId) && this.reviewId.equals(other.reviewId);
    }
}
