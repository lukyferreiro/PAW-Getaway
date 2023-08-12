package ar.edu.itba.getaway.webapp.controller.queryParamsValidators;

import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.webapp.security.exceptions.InvalidRequestParamsException;
import ar.edu.itba.getaway.webapp.security.services.AuthContext;

public class GetReviewsParams {

    public static Page<ReviewModel> getReviewsByParams(
            Long userId, Long experienceId, int page, ReviewService reviewService,
            ExperienceService experienceService, AuthContext authContext
    ) {

        if(userId == null && experienceId == null){
            throw new InvalidRequestParamsException("errors.InvalidParam.getReviews.bothNull");
        }

        if(userId != null && experienceId != null){
            throw new InvalidRequestParamsException("errors.InvalidParam.getReviews.bothNotNull");
        }

        Page<ReviewModel> reviews;

        if (userId == null) {
            final ExperienceModel experienceModel = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
            reviews = reviewService.getReviewsByExperience(experienceModel, page);
        } else {
            final UserModel user = authContext.getCurrentUser();
            reviews = reviewService.getReviewsByUser(user, page);
        }

        return reviews;
    }
}
