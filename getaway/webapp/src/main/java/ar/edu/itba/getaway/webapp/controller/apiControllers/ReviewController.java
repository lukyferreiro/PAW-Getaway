package ar.edu.itba.getaway.webapp.controller.apiControllers;

import ar.edu.itba.getaway.interfaces.exceptions.ReviewNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.dto.request.NewExperienceDto;
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
    private UserService userService;
    @Autowired
    private AuthFacade authFacade;
    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @GET
    @Path("/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getReview(@PathParam("reviewId") final Long id) {
        LOGGER.info("Called /reviews/{} GET", id);

        final ReviewModel review = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);

        return Response.ok(new ReviewDto(review, uriInfo)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response editReview(@PathParam("reviewId") final Long id,
                               @Context final HttpServletRequest request,
                               @Valid NewReviewDto reviewDto) {
        final UserModel user = authFacade.getCurrentUser();
//        final UserModel user = userService.getUserByEmail(authFacade.getCurrentUser().getEmail()).orElseThrow(UserNotFoundException::new);
//        final UserModel user = userService.getUserById(1).orElseThrow(UserNotFoundException::new);

        final ReviewModel reviewModel = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);
        assureUserResourceCorrelation(user, reviewModel.getUser().getUserId());

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
        final UserModel user = authFacade.getCurrentUser();
//        final UserModel user = userService.getUserByEmail(authFacade.getCurrentUser().getEmail()).orElseThrow(UserNotFoundException::new);
//        final UserModel user = userService.getUserById(1).orElseThrow(UserNotFoundException::new);

        final ReviewModel reviewModel = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);
        assureUserResourceCorrelation(user, reviewModel.getUser().getUserId());
        reviewService.deleteReview(reviewModel);
        return Response.noContent().build();
    }

    private void assureUserResourceCorrelation(UserModel user, long userId) {
        if (user.getUserId() != userId) {
            throw new ForbiddenException();
        }
    }
}
