package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    //TODO ver aca
    ReviewModel createReview (String title, String description, Long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel);
    void updateReview (long reviewId, ReviewModel reviewModel);
    void deleteReview (ReviewModel review);
    Optional<ReviewModel> getReviewById (long reviewId);
    //TODO move this logic to a new UserModel
    List<ReviewModel> getReviewsByUser (UserModel user, int page, int pageSize);
    long getReviewByUserCount(UserModel user);
    //TODO move to a new ExperienceModel
    List<ReviewModel> getReviewsByExperience (ExperienceModel experience, int page, int pageSize);
    long getReviewByExperienceCount(ExperienceModel experience);
}
