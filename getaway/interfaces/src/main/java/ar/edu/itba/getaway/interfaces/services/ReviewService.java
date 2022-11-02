package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;
//import ar.edu.itba.getaway.models.UserModelWithReviews;
import ar.edu.itba.getaway.models.pagination.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    //TODO ver aca
    ReviewModel createReview(String title, String description, Long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel);
    void updateReview(long reviewId, ReviewModel reviewModel);
    void deleteReview(ReviewModel review);
    Page<ReviewModel> getReviewAndUser(ExperienceModel experience, int page);
    Optional<ReviewModel> getReviewById(long reviewId);
    Page<ReviewModel> getReviewsByUser(UserModel user, int page);
}
