package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.ReviewNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.webapp.dto.request.NewReviewDto;
import ar.edu.itba.getaway.webapp.dto.response.ReviewDto;
import ar.edu.itba.getaway.webapp.security.services.AuthFacade;
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

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private AuthFacade authFacade;
    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

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
            @Valid NewReviewDto reviewDto
    ) {

        LOGGER.info("Called /reviews/{} PUT", id);

        final ReviewModel reviewModel = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);

        reviewModel.setDescription(reviewDto.getDescription());
        reviewModel.setScore(Long.parseLong(reviewDto.getScore()));
        reviewModel.setTitle(reviewDto.getTitle());
        reviewService.updateReview(reviewModel);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response deleteReview(@PathParam("reviewId") final long id) {
        LOGGER.info("Called /reviews/{} DELETE", id);
        final ReviewModel reviewModel = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);
        reviewService.deleteReview(reviewModel);
        return Response.noContent().build();
    }
}