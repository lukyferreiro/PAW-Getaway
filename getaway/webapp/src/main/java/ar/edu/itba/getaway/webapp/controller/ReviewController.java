package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.ContentExpectedException;
import ar.edu.itba.getaway.interfaces.exceptions.ReviewNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.webapp.constraints.DtoConstraintValidator;
import ar.edu.itba.getaway.webapp.constraints.exceptions.DtoValidationException;
import ar.edu.itba.getaway.webapp.dto.request.NewReviewDto;
import ar.edu.itba.getaway.webapp.dto.response.ReviewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("reviews")
@Component
public class ReviewController {

    @Context
    private UriInfo uriInfo;
    private final ReviewService reviewService;
    private final DtoConstraintValidator dtoValidator;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    public ReviewController(ReviewService reviewService, DtoConstraintValidator dtoValidator) {
        this.reviewService = reviewService;
        this.dtoValidator = dtoValidator;
    }

    @GET
    @Path("/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getReview(
            @PathParam("reviewId") final Long id
    ) {

        LOGGER.info("Called /reviews/{} GET", id);

        final ReviewModel review = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);

        return Response.ok(new ReviewDto(review, uriInfo)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response editReview(
            @PathParam("reviewId") final Long id,
            @Context final HttpServletRequest request,
            @Valid final NewReviewDto reviewDto
    ) throws DtoValidationException {

        LOGGER.info("Called /reviews/{} PUT", id);

        if (reviewDto == null) {
            throw new ContentExpectedException();
        }
        dtoValidator.validate(reviewDto, "Invalid Body Request");

        final ReviewModel reviewModel = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);

        reviewModel.setDescription(reviewDto.getDescription());
        reviewModel.setScore(Long.parseLong(reviewDto.getScore()));
        reviewModel.setTitle(reviewDto.getTitle());
        reviewService.updateReview(reviewModel);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response deleteReview(@PathParam("reviewId") final Long id) {
        LOGGER.info("Called /reviews/{} DELETE", id);
        final ReviewModel reviewModel = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);
        reviewService.deleteReview(reviewModel);
        return Response.noContent().build();
    }
}
