package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ReviewModel;

import java.util.Date;
import java.util.List;

public interface ReviewDao {
    ReviewModel create (String title, String description, long score, long experienceId, Date reviewDate, long userId);
    List<ReviewModel> getReviewsFromId(long experienceId);
    Double getAverageScore(long experienceId);
    Integer getReviewCount(long experienceId);
}
