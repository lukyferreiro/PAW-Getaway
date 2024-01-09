package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.ContentExpectedException;
import ar.edu.itba.getaway.interfaces.exceptions.ReviewNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.webapp.controller.queryParamsValidators.GetReviewsParams;
import ar.edu.itba.getaway.webapp.controller.queryParamsValidators.InvalidRequestParamsException;
import ar.edu.itba.getaway.webapp.controller.util.PaginationResponse;
import ar.edu.itba.getaway.webapp.dto.request.NewReviewDto;
import ar.edu.itba.getaway.webapp.dto.response.ReviewDto;
import ar.edu.itba.getaway.webapp.security.api.CustomMediaType;
import ar.edu.itba.getaway.webapp.security.services.AuthContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.Collection;

@Path("reviews")
@Component
public class ReviewController {

    @Context
    private UriInfo uriInfo;
    private final ReviewService reviewService;
    private final ExperienceService experienceService;
    private final AuthContext authContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    public ReviewController(ReviewService reviewService, ExperienceService experienceService, AuthContext authContext) {
        this.reviewService = reviewService;
        this.experienceService = experienceService;
        this.authContext = authContext;
    }

    @GET
    @Produces(value = {CustomMediaType.REVIEW_LIST_V1})
    @PreAuthorize("@antMatcherVoter.checkGetReviews(authentication, #userId, #experienceId)")
    public Response getReviews(
            @QueryParam("userId") final Long userId,
            @QueryParam("experienceId") final Long experienceId,
            @QueryParam("page") @DefaultValue("1") final int page
    ) {
        LOGGER.info("Called /reviews GET");

        Page<ReviewModel> reviews = GetReviewsParams.getReviewsByParams(userId, experienceId, page, reviewService, experienceService, authContext);
        final UriBuilder uriBuilder = GetReviewsParams.getUriBuilder(userId, experienceId, page, uriInfo);

        if (reviews.getContent().isEmpty()) {
            return Response.noContent().build();
        }

        final Collection<ReviewDto> reviewDtos = ReviewDto.mapReviewToDto(reviews.getContent(), uriInfo);

        return PaginationResponse.createPaginationResponse(reviews, new GenericEntity<Collection<ReviewDto>>(reviewDtos) {
        }, uriBuilder);

    }

    // Endpoint para crear una reseña en la experiencia
    @POST
    @Consumes(value = {CustomMediaType.REVIEW_V1})
    public Response createReview(
            @QueryParam("experienceId") final Long experienceId,
            @Valid final NewReviewDto newReviewDto
    ) {

        if (newReviewDto == null) {
            throw new ContentExpectedException();
        }

        LOGGER.info("Called /reviews POST");

        if(experienceId == null){
            throw new InvalidRequestParamsException("errors.invalidParam.postReview");
        }

        final UserModel user = authContext.getCurrentUser();

        final ReviewModel reviewModel = reviewService.createReview(newReviewDto.getTitle(), newReviewDto.getDescription(), newReviewDto.getLongScore(), experienceId, LocalDate.now(), user);
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(reviewModel.getReviewId())).build();

        return Response.created(location).build();
    }


    @GET
    @Path("/{reviewId:[0-9]+}")
    @Produces(value = {CustomMediaType.REVIEW_V1})
    public Response getReviewById(
            @PathParam("reviewId") final Long id
    ) {
        LOGGER.info("Called /reviews/{} GET", id);
        final ReviewModel review = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);
        return Response.ok(new ReviewDto(review, uriInfo)).build();
    }

    @PUT
    @Path("/{reviewId:[0-9]+}")
    @Consumes(value = {CustomMediaType.REVIEW_V1})
    public Response editReview(
            @PathParam("reviewId") final Long id,
            @Valid final NewReviewDto reviewDto
    ) {

        LOGGER.info("Called /reviews/{} PUT", id);

        if (reviewDto == null) {
            throw new ContentExpectedException();
        }

        reviewService.updateReview(id, reviewDto.getTitle(), reviewDto.getDescription(), reviewDto.getScore());

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{reviewId:[0-9]+}")
    public Response deleteReview(
            @PathParam("reviewId") final Long id
    ) {
        LOGGER.info("Called /reviews/{} DELETE", id);
        reviewService.deleteReview(id);
        return Response.noContent().build();
    }
}
