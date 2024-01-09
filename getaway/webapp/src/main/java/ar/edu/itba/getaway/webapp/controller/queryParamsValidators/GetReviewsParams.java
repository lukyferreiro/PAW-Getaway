package ar.edu.itba.getaway.webapp.controller.queryParamsValidators;

import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.webapp.security.services.AuthContext;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class GetReviewsParams {

    public static Page<ReviewModel> getReviewsByParams(
            Long userId, Long experienceId, int page, ReviewService reviewService,
            ExperienceService experienceService, AuthContext authContext
    ) {

        if(userId == null && experienceId == null){
            throw new InvalidRequestParamsException("errors.invalidParam.getReviews.bothNull");
        }

        if(userId != null && experienceId != null){
            throw new InvalidRequestParamsException("errors.invalidParam.getReviews.bothNotNull");
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

    public static UriBuilder getUriBuilder(Long userId, Long experienceId, int page, UriInfo uriInfo){
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().queryParam("page", page);

        if (userId == null) {
            uriBuilder = uriBuilder.queryParam("experienceId", experienceId);
        } else {
            uriBuilder = uriBuilder.queryParam("userId", userId);
        }

        return uriBuilder;
    }
}
