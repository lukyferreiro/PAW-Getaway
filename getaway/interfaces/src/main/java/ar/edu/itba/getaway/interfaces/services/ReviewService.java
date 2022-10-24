package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    ReviewModel createReview(String title, String description, Long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel);
    void updateReview(Long reviewId, ReviewModel reviewModel);
    void deleteReview(ReviewModel review);
    Page<ReviewModel> getReviewAndUser(ExperienceModel experience, Integer page);
    Optional<ReviewModel> getReviewById(Long reviewId);
    Page<ReviewModel> getReviewsByUser(UserModel user, Integer page);
}
