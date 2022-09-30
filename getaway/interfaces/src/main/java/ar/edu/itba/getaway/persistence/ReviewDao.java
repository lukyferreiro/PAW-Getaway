package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    ReviewModel create (String title, String description, long score, long experienceId, Date reviewDate, long userId);
    List<ReviewModel> getReviewsFromId(long experienceId);
    Long getAverageScore(long experienceId);
    Integer getReviewCount(long experienceId);
    List<ReviewUserModel> getReviewAndUser(long experienceId);
//        List<ReviewUserModel> getReviewAndUser(long experienceId, int page, int page_size);
    Optional<ReviewModel> getById(long reviewId);
//    List<ReviewUserModel> getByUserId(long userId, int page, int page_size);
    List<ReviewUserModel> getByUserId(long userId);
    boolean delete(long reviewId);
    boolean update(long reviewId, ReviewModel reviewModel);
}
