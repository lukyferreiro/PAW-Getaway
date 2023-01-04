package ar.edu.itba.getaway.webapp.controller.apiControllers;

import ar.edu.itba.getaway.interfaces.exceptions.ReviewNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.webapp.dto.response.ReviewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/reviews")
@Component
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getReview(@PathParam("id") final Long id) {
        LOGGER.info("Called /reviews/{} GET", id);

        final ReviewModel review = reviewService.getReviewById(id).orElseThrow(ReviewNotFoundException::new);

        return Response.ok(new ReviewDto(review, uriInfo)).build();
    }

}
