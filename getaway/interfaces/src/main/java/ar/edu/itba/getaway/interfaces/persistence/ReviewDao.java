package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;
import ar.edu.itba.getaway.models.UserModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    ReviewModel createReview (String title, String description, Long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel);
    List<ReviewModel> getReviewsByExperience (ExperienceModel experience);
    Long getReviewAverageScore (ExperienceModel experienceModel);
    Integer getReviewCount (ExperienceModel experience);
    List<ReviewUserModel> getReviewAndUser (ExperienceModel experience, Integer page, Integer page_size);
    Optional<ReviewModel> getReviewById (Long reviewId);
    List<ReviewUserModel> getReviewsByUser (UserModel user, Integer page, Integer page_size);
    Integer getReviewByUserCount(UserModel user);
    void deleteReview (ReviewModel review);
    void updateReview (Long reviewId, ReviewModel reviewModel);
}
