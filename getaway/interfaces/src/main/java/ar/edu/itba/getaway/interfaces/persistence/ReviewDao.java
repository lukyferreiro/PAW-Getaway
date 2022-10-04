package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    ReviewModel createReview (String title, String description, Long score, Long experienceId, Date reviewDate, Long userId);
    List<ReviewModel> getReviewsByExperienceId (Long experienceId);
    Long getReviewAverageScore (Long experienceId);
    Integer getReviewCount (Long experienceId);
    List<ReviewUserModel> getReviewAndUser (Long experienceId);
    Optional<ReviewModel> getReviewById (Long reviewId);
    List<ReviewUserModel> getReviewsByUserId (Long userId);
    boolean deleteReview (Long reviewId);
    boolean updateReview (Long reviewId, ReviewModel reviewModel);
}
