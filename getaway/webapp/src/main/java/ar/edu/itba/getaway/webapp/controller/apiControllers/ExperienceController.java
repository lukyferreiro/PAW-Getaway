package ar.edu.itba.getaway.webapp.controller.apiControllers;

import ar.edu.itba.getaway.interfaces.exceptions.*;
import ar.edu.itba.getaway.interfaces.services.*;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.webapp.dto.request.NewExperienceDto;
import ar.edu.itba.getaway.webapp.dto.request.NewReviewDto;
import ar.edu.itba.getaway.webapp.dto.response.ExperienceDto;
import ar.edu.itba.getaway.webapp.dto.response.ReviewDto;
import ar.edu.itba.getaway.webapp.dto.response.UserDto;
import ar.edu.itba.getaway.webapp.security.services.AuthFacade;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("experiences")
@Component
public class ExperienceController {
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private int maxRequestSize;
    @Autowired
    private AuthFacade authFacade;
    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceController.class);
    private static final String ACCEPTED_MIME_TYPES = "image/";

    // Endpoint para obtener las experiencias de una categoria
    @GET
    @Path("/category/{category}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperiencesFromCategory(
            @PathParam("category") final String category,
            @QueryParam("order") @DefaultValue("OrderByAZ") OrderByModel order,
            @QueryParam("price") @DefaultValue("-1") Double maxPrice,
            @QueryParam("score") @DefaultValue("0") Long maxScore,
            @QueryParam("city") @DefaultValue("-1") Long cityId,
            @QueryParam("page") @DefaultValue("1") int page
    ) {
        LOGGER.info("Called /experiences/{} GET", category);

        final CategoryModel categoryModel = categoryService.getCategoryByName(category).orElseThrow(CategoryNotFoundException::new);
        if (maxPrice == -1) {
            maxPrice = experienceService.getMaxPriceByCategory(categoryModel).orElse(0.0);
        }
        final CityModel cityModel = locationService.getCityById(cityId).orElse(null);

        //TODO: change for deployment

        //final UserModel user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        final UserModel user = userService.getUserById(1).orElseThrow(UserNotFoundException::new);


        final Page<ExperienceModel> experiences = experienceService.listExperiencesByFilter(categoryModel, maxPrice, maxScore, cityModel, Optional.of(order), page, user);

        if (experiences == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (experiences.getContent().isEmpty()) {
            return Response.noContent().build();
        }

        final Collection<ExperienceDto> experienceDto = ExperienceDto.mapExperienceToDto(experiences.getContent(), uriInfo);

        final UriBuilder uriBuilder = uriInfo
                .getAbsolutePathBuilder()
                .queryParam("category", category)
                .queryParam("order", order)
                .queryParam("price", maxPrice)
                .queryParam("score", maxScore)
                .queryParam("page", page);

        if (cityModel != null) {
            uriBuilder.queryParam("city", cityId);
        }

        return createPaginationResponse(experiences, new GenericEntity<Collection<ExperienceDto>>(experienceDto) {
        }, uriBuilder);
    }

    @GET
    @Path("/name/{name}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperiencesBySearch(
            @PathParam("name") final String name,
            @QueryParam("order") @DefaultValue("OrderByAZ") OrderByModel order,
            @QueryParam("page") @DefaultValue("1") int page
    ) {
        LOGGER.info("Called /experiences/{} GET", name);

        final UserModel user = userService.getUserByEmail(authFacade.getCurrentUser().getEmail()).orElseThrow(UserNotFoundException::new);
//        final UserModel user = userService.getUserById(1).orElseThrow(UserNotFoundException::new);

        final Page<ExperienceModel> experiences = experienceService.listExperiencesSearch(name, Optional.of(order), page, user);

        if (experiences == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (experiences.getContent().isEmpty()) {
            return Response.noContent().build();
        }

        final Collection<ExperienceDto> experienceDto = ExperienceDto.mapExperienceToDto(experiences.getContent(), uriInfo);

        final UriBuilder uriBuilder = uriInfo
                .getAbsolutePathBuilder()
                .queryParam("order", order)
                .queryParam("page", page);

        return createPaginationResponse(experiences, new GenericEntity<Collection<ExperienceDto>>(experienceDto) {
        }, uriBuilder);
    }

    // Endpoint para crear una experiencia
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response registerExperience(@Valid final NewExperienceDto experienceDto) throws DuplicateExperienceException {
        LOGGER.info("Called /experiences/ POST");

        if (experienceDto == null) {
            throw new ContentExpectedException();
        }

        final UserModel user = userService.getUserByEmail(authFacade.getCurrentUser().getEmail()).orElseThrow(UserNotFoundException::new);
//        final UserModel user = userService.getUserById(1).orElseThrow(UserNotFoundException::new);

        final CityModel city = locationService.getCityByName(experienceDto.getCity()).orElseThrow(CityNotFoundException::new);
        final CategoryModel category = categoryService.getCategoryById(experienceDto.getCategory()).orElseThrow(CategoryNotFoundException::new);

        ExperienceModel experience;
        try {
            experience = experienceService.createExperience(
                experienceDto.getName(), experienceDto.getAddress(),
                experienceDto.getDescription(), experienceDto.getMail(),
                experienceDto.getUrl(), Double.parseDouble(experienceDto.getPrice()),
                city, category, user);
        } catch (DuplicateExperienceException e) {
            LOGGER.warn("Error in experienceDto ExperienceForm, there is already an experience with this id");
            throw new DuplicateExperienceException();
        }

        LOGGER.info("Created experience with id {}", experience.getExperienceId());
        return Response.created(ExperienceDto.getExperienceUriBuilder(experience, uriInfo).build()).build();
    }

    // Endpoint para obtener una experiencia a partir de su ID
    @GET
    @Path("/experience/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperienceId(@PathParam("id") final long id) {
        LOGGER.info("Called /experiences/{} GET", id);

        final UserModel user = userService.getUserByEmail(authFacade.getCurrentUser().getEmail()).orElseThrow(UserNotFoundException::new);
//        final UserModel user = userService.getUserById(1).orElseThrow(UserNotFoundException::new);

        final ExperienceModel experience = experienceService.getVisibleExperienceById(id, user).orElseThrow(ExperienceNotFoundException::new);
        final ExperienceDto experienceDto = new ExperienceDto(experience, uriInfo);
        return Response.ok(experienceDto).build();
    }

    // Endpoint para editar una experiencia
    @PUT
    @Path("/experience/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response updateExperience(
            @Context final HttpServletRequest request,
            @Valid NewExperienceDto experienceDto,
            @PathParam("id") final long id) {
        LOGGER.info("Called /experiences/{} PUT", id);

        if (request.getContentLength() == -1 || request.getContentLength() > maxRequestSize) {
            throw new MaxUploadSizeRequestException();
        }

        if (experienceDto == null) {
            throw new ContentExpectedException();
        }

        final UserModel user = userService.getUserByEmail(authFacade.getCurrentUser().getEmail()).orElseThrow(UserNotFoundException::new);
//        final UserModel user = userService.getUserById(1).orElseThrow(UserNotFoundException::new);
        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);

        if (experience.getUser().getUserId() != (user.getUserId())) {
            LOGGER.error("Error, user with id {} is trying to update the experience with id {} that belongs to user with id {}",
                    user.getUserId(), id, experience.getUser().getUserId());
            throw new IllegalOperationException();
        }

        final CityModel cityModel = locationService.getCityByName(experienceDto.getCity()).orElseThrow(CityNotFoundException::new);
        final CategoryModel categoryModel = categoryService.getCategoryById(experienceDto.getCategory()).orElseThrow(CategoryNotFoundException::new);

        final ExperienceModel toUpdateExperience = new ExperienceModel(id, experienceDto.getName(), experienceDto.getAddress(), experienceDto.getDescription(),
        experienceDto.getMail(), experienceDto.getUrl(),  Double.parseDouble(experienceDto.getPrice()), cityModel, categoryModel, user, experience.getExperienceImage(), experience.getObservable(), experience.getViews());

        experienceService.updateExperience(toUpdateExperience);
        LOGGER.info("The experience with id {} has been updated successfully", id);
        return Response.created(ExperienceDto.getExperienceUriBuilder(experience, uriInfo).build()).build();
    }

    // Endpoint para eliminar una experiencia
    @DELETE
    @Path("/experience/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteExperience(@PathParam("id") final long id) {
        final UserModel user = userService.getUserByEmail(authFacade.getCurrentUser().getEmail()).orElseThrow(UserNotFoundException::new);
//        final UserModel user = userService.getUserById(1).orElseThrow(UserNotFoundException::new);
        final ExperienceModel experienceModel = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);

        if (experienceModel.getUser().getUserId() != (user.getUserId())) {
            LOGGER.error("Error, user with id {} is trying to update the experience with id {} that belongs to user with id {}",
                    user.getUserId(), id, experienceModel.getUser().getUserId());
            throw new IllegalOperationException();
        }

        experienceService.deleteExperience(experienceModel);
        return Response.noContent().build();
    }

    // Endpoint para obtener la imagen de una experiencia
    @GET
    @Path("/experience/{id}/experienceImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getExperienceImage(@PathParam("id") final long id, @Context Request request) {

        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(UserNotFoundException::new);
        final ImageModel img = experience.getExperienceImage();

        if (img == null) {
            return Response.status(NOT_FOUND).build();
        }

        final EntityTag eTag = new EntityTag(String.valueOf(img.getImageId()));
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);

        Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(eTag);
        if (responseBuilder == null) {
            final byte[] profileImage = img.getImage();
            responseBuilder = Response.ok(profileImage).type(img.getMimeType()).tag(eTag);
        }

        return responseBuilder.cacheControl(cacheControl).build();
    }

    @PUT
    @Path("/experience/{id}/experienceImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response updateExperienceImage(
            @PathParam("id") long id,
            @FormDataParam("experienceImage") final FormDataBodyPart experienceImageBody,
            @Size(max = 1024 * 1024) @FormDataParam("experienceImage") byte[] experienceImageBytes) {

        if (experienceImageBody == null) {
            throw new ContentExpectedException();
        }

        if (experienceImageBody.getMediaType().toString().contains(ACCEPTED_MIME_TYPES)) {
            throw new IllegalContentTypeException();
        }

//        final UserModel user = userService.getUserById(1).orElseThrow(UserNotFoundException::new);
        final UserModel user = userService.getUserByEmail(authFacade.getCurrentUser().getEmail()).orElseThrow(UserNotFoundException::new);
        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);

        imageService.updateImg(experienceImageBytes, experienceImageBody.getMediaType().toString(), experience.getExperienceImage());

        return Response.noContent()
                .contentLocation(ExperienceDto.getExperienceUriBuilder(experience, uriInfo).path("profileImage").build())
                .build();
    }

    // Endpoint para obtener la reseñas de una experiencia
    @GET
    @Path("/experience/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperienceReviews(
            @PathParam("id") final long id,
            @QueryParam("page") @DefaultValue("1") int page
    ) {
        LOGGER.info("Called /experiences/{}/reviews GET", id);
        final ExperienceModel experienceModel = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);
        Page<ReviewModel> reviewModelList = reviewService.getReviewAndUser(experienceModel, page);

        final Collection<ReviewDto> reviewDto = ReviewDto.mapReviewToDto(reviewModelList.getContent(), uriInfo);

        final UriBuilder uriBuilder = uriInfo
                .getAbsolutePathBuilder()
                .queryParam("page", page);

        return createPaginationResponse(reviewModelList, new GenericEntity<Collection<ReviewDto>>(reviewDto) {
        }, uriBuilder);
    }

    // Endpoint para crear una reseña en la experiencia
    @POST
    @Path("/experience/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response createExperienceReview(@PathParam("id") final long id, @Valid NewReviewDto newReviewDto) {
        LOGGER.info("Creating review with /experience/category/{}/create_review POST", id);
        //TODO: check usage of localdate.now()

        final UserModel user = userService.getUserByEmail(authFacade.getCurrentUser().getEmail()).orElseThrow(UserNotFoundException::new);
//        final UserModel user = userService.getUserById(1).orElseThrow(UserNotFoundException::new);
        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);

        final ReviewModel reviewModel = reviewService.createReview(newReviewDto.getTitle(), newReviewDto.getDescription(), newReviewDto.getLongScore(), experience, LocalDate.now(), user);
        return Response.created(ReviewDto.getReviewUriBuilder(reviewModel, uriInfo).build()).build();
    }

    private <T, K> Response createPaginationResponse(Page<T> results,
                                                     GenericEntity<K> resultsDto,
                                                     UriBuilder uriBuilder) {
        if (results.getContent().isEmpty()) {
            if (results.getCurrentPage() == 0) {
                return Response.noContent().build();
            } else {
                return Response.status(NOT_FOUND).build();
            }
        }

        final Response.ResponseBuilder response = Response.ok(resultsDto);

        addPaginationLinks(response, results, uriBuilder);

        return response.build();
    }

    private <T> void addPaginationLinks(Response.ResponseBuilder responseBuilder,
                                        Page<T> results,
                                        UriBuilder uriBuilder) {

        final int page = results.getCurrentPage();

        final int first = 0;
        final int last = results.getMaxPage();
        final int prev = page - 1;
        final int next = page + 1;

        responseBuilder.link(uriBuilder.clone().queryParam("page", first).build(), "first");

        responseBuilder.link(uriBuilder.clone().queryParam("page", last).build(), "last");

        if (page != first) {
            responseBuilder.link(uriBuilder.clone().queryParam("page", prev).build(), "prev");
        }

        if (page != last) {
            responseBuilder.link(uriBuilder.clone().queryParam("page", next).build(), "next");
        }
    }
}

