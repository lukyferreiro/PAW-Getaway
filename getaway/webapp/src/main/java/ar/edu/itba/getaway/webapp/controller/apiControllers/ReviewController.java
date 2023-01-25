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

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getReview(@PathParam("id") final Long id) {
        LOGGER.info("Called /reviews/{} GET", id);

        final ReviewModel review = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);

        return Response.ok(new ReviewDto(review, uriInfo)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response editReview(@PathParam("id") final Long id,
                               @Context final HttpServletRequest request,
                               @Valid NewReviewDto reviewDto) {
        // final UserModel user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        final UserModel user = userService.getUserById(1).orElseThrow(UserNotFoundException::new);

        final ReviewModel reviewModel = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);
        assureUserResourceCorrelation(user, reviewModel.getUser().getUserId());

        reviewModel.setDescription(reviewDto.getDescription());
        reviewModel.setScore(Long.parseLong(reviewDto.getScore()));
        reviewModel.setTitle(reviewDto.getTitle());
        reviewService.updateReview(reviewModel);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response deleteReview(@PathParam("id") final long id) {
//        final UserModel user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        final UserModel user = userService.getUserById(1).orElseThrow(UserNotFoundException::new);

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
