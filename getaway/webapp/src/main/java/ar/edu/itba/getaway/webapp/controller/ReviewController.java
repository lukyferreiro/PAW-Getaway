package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.ContentExpectedException;
import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.ReviewNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.dto.request.NewReviewDto;
import ar.edu.itba.getaway.webapp.dto.response.ReviewDto;
import ar.edu.itba.getaway.webapp.security.api.CustomMediaType;
import ar.edu.itba.getaway.webapp.security.services.AuthContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDate;

@Path("reviews")
@Component
public class ReviewController {

    @Context
    private UriInfo uriInfo;
    private final ReviewService reviewService;
    private final AuthContext authContext;
    private final ExperienceService experienceService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    public ReviewController(ReviewService reviewService, ExperienceService experienceService, AuthContext authContext) {
        this.reviewService = reviewService;
        this.experienceService = experienceService;
        this.authContext = authContext;
    }

    // Endpoint para crear una reseña en la experiencia
    @POST
    @Produces(value = {CustomMediaType.REVIEW_V1})
    public Response createReview(
            @QueryParam("experienceId") final long experienceId,
            @Valid final NewReviewDto newReviewDto
    ) {

        if (newReviewDto == null) {
            throw new ContentExpectedException();
        }

        LOGGER.info("Called /reviews POST");
        final UserModel user = authContext.getCurrentUser();
        //TODO se podria sacar este get de aca
        final ExperienceModel experience = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final ReviewModel reviewModel = reviewService.createReview(newReviewDto.getTitle(), newReviewDto.getDescription(), newReviewDto.getLongScore(), experience, LocalDate.now(), user);
        return Response.created(ReviewDto.getReviewUriBuilder(reviewModel, uriInfo).build()).build();
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{reviewId:[0-9]+}")
    @Produces(value = {CustomMediaType.REVIEW_V1})
    public Response editReview(
            @PathParam("reviewId") final Long id,
            @Valid final NewReviewDto reviewDto
    ) {

        LOGGER.info("Called /reviews/{} PUT", id);

        if (reviewDto == null) {
            throw new ContentExpectedException();
        }

        final ReviewModel reviewModel = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);

        //TODO se podrian sacar estos set de aca
        reviewModel.setDescription(reviewDto.getDescription());
        reviewModel.setScore(Long.parseLong(reviewDto.getScore()));
        reviewModel.setTitle(reviewDto.getTitle());
        reviewService.updateReview(reviewModel);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{reviewId:[0-9]+}")
    //TODO check
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteReview(
            @PathParam("reviewId") final Long id
    ) {
        LOGGER.info("Called /reviews/{} DELETE", id);
        final ReviewModel reviewModel = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);
        reviewService.deleteReview(reviewModel);
        //TODO devolver un simple message
        return Response.noContent().build();
    }
}
