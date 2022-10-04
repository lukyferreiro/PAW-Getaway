package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    ReviewModel createReview (String title, String description, Long score, Long experienceId, Date reviewDate, Long userId);
    List<ReviewModel> getReviewsByExperienceId (Long experienceId);
    Long getReviewAverageScore (Long experienceId);
    List<Long> getListOfAverageScoreByExperienceList(List<ExperienceModel> experienceModelList);
    Integer getReviewCount (Long experienceId);
    List<Integer> getListOfReviewCountByExperienceList(List<ExperienceModel> experienceModelList);
    List<ReviewUserModel> getReviewAndUser (Long experienceId);
    Optional<ReviewModel> getReviewById (Long reviewId);
    List<ReviewUserModel> getReviewsByUserId (Long userId);
    void deleteReview (Long reviewId);
    void updateReview (Long reviewId, ReviewModel reviewModel);

}
