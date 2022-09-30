package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    ReviewModel create (String title, String description, long score, long experienceId, Date reviewDate, long userId);
    List<ReviewModel> getReviewsFromId(long experienceId);
    Long getAverageScore(long experienceId);
    Integer getReviewCount(long experienceId);
//  Page<ReviewUserModel getReviewAndUser(long experienceId, int page);
    List<ReviewUserModel> getReviewAndUser(long experienceId);
    Optional<ReviewModel> getById(long reviewId);
//  Page<ReviewUserModel getByUserId(long userId, int page);
    List<ReviewUserModel> getByUserId(long userId);
    boolean delete(long reviewId);
    boolean update(long reviewId, ReviewModel reviewModel);
}
