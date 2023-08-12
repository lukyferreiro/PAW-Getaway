package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;

import java.time.LocalDate;
import java.util.Optional;

public interface ReviewService {
    ReviewModel createReview(String title, String description, long score, ExperienceModel experienceModel, LocalDate reviewDate, UserModel userModel);
    void updateReview(ReviewModel reviewModel);

    void deleteReview(ReviewModel review);

    Page<ReviewModel> getReviewsByExperience(ExperienceModel experience, int page);

    Optional<ReviewModel> getReviewById(long reviewId);

    Page<ReviewModel> getReviewsByUser(UserModel user, int page);
}
