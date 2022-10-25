package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    ReviewModel createReview (String title, String description, Long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel);
    void updateReview (Long reviewId, ReviewModel reviewModel);
    void deleteReview (ReviewModel review);
    Optional<ReviewModel> getReviewById (Long reviewId);
    //TODO move this logic to a new UserModel
    List<ReviewModel> getReviewsByUser (UserModel user, Integer page, Integer page_size);
    Long getReviewByUserCount(UserModel user);
    //TODO move to a new ExperienceModel
    List<ReviewModel> getReviewsByExperience (ExperienceModel experience, Integer page, Integer page_size);
    Long getReviewByExperienceCount(ExperienceModel experience);
}
