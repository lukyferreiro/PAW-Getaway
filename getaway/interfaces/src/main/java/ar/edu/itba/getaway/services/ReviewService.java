package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ReviewModel;

import java.util.Date;
import java.util.List;

public interface ReviewService {
    ReviewModel create (String title, String description, long score, long experienceId, Date reviewDate, long userId);
    List<ReviewModel> getReviewsFromId(long experienceId);
}
