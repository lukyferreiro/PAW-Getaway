package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    ReviewModel createReview (String title, String description, long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel);
    void updateReview (ReviewModel reviewModel);
    void deleteReview (ReviewModel review);
    Optional<ReviewModel> getReviewById (long reviewId);
    List<ReviewModel> getReviewsByUser (UserModel user, int page, int pageSize);
    long getReviewByUserCount(UserModel user);
    List<ReviewModel> getReviewsByExperience (ExperienceModel experience, int page, int pageSize);
    long getReviewByExperienceCount(ExperienceModel experience);
}
