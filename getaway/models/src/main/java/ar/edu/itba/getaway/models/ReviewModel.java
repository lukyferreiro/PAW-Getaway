package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "reviews")
public class ReviewModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_reviewId_seq")
    @SequenceGenerator(sequenceName = "reviews_reviewId_seq", name = "reviews_reviewId_seq", allocationSize = 1)
    @Column(name = "reviewId")
    private Long reviewId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "score", nullable = false)
    private Long score;
    //TODO change tipo DATE
    @Column(name = "reviewDate", nullable = false)
    private Date reviewDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experienceId", nullable = false)
    private ExperienceModel experience;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserModel user;

    /* default */
    protected ReviewModel() {
        // Just for Hibernate
    }

    public ReviewModel(Long reviewId, String title, String description, Long score, ExperienceModel experience, Date reviewDate, UserModel user) {
        this.reviewId = reviewId;
        this.title = title;
        this.description = description;
        this.score = score;
        this.experience = experience;
        this.reviewDate = reviewDate;
        this.user = user;
    }

    public Long getReviewId() {
        return reviewId;
    }
    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
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
    public Long getScore() {
        return score;
    }
    public void setScore(Long score) {
        this.score = score;
    }
    public String getStringScore() {
        return String.valueOf(score);
    }
    public Date getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
    public ExperienceModel getExperience() {
        return experience;
    }
    public void setExperience(ExperienceModel experience) {
        this.experience = experience;
    }
    public UserModel getUser() {
        return user;
    }
    public void setUser(UserModel user) {
        this.user = user;
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

    @Override
    public int hashCode() {
        return Objects.hash(reviewId);
    }
}
