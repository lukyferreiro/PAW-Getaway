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
import ar.edu.itba.getaway.webapp.dto.validations.ImageTypeConstraint;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/experiences")
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
    @Context
    private SecurityContext securityContext;
    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceController.class);

    // Endpoint para obtener las experiencias de una categoria
    @GET
    @Path("/{category}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperiencesFromCategory(
            @PathParam("category") final String category,
            @QueryParam("order") @DefaultValue("OrderByAZ") OrderByModel order,
            @QueryParam("price") @DefaultValue("-1") Double maxPrice,
            @QueryParam("score") @DefaultValue("5") Long maxScore,
            @QueryParam("city") @DefaultValue("-1") Long cityId,
            @QueryParam("page") @DefaultValue("1") int page
    ) {
        LOGGER.info("Called /experiences/{} GET", category);

        final CategoryModel categoryModel = categoryService.getCategoryByName(category).orElseThrow(CategoryNotFoundException::new);
        if (maxPrice == -1) {
            maxPrice = experienceService.getMaxPriceByCategory(categoryModel).orElse(0.0);
        }
        final CityModel cityModel = locationService.getCityById(cityId).orElse(null);
        final UserModel user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        final Page<ExperienceModel> experiences = experienceService.listExperiencesByFilter(categoryModel, maxPrice, maxScore, cityModel, Optional.of(order), page, user);

        if (experiences == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
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

    // Endpoint para crear una experiencia
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response registerExperience(
            @Context final HttpServletRequest request,
            @Valid final NewExperienceDto experienceDto) throws DuplicateExperienceException, IOException {

        LOGGER.info("Called /experiences/ POST");

        if (experienceDto == null) {
            throw new ContentExpectedException();
        }

        if (request.getContentLength() == -1 || request.getContentLength() > maxRequestSize) {
            throw new MaxUploadSizeRequestException();
        }

        final UserModel user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        final FormDataBodyPart img = experienceDto.getImage();
        NewImageModel imageToUpload = null;

        if (img != null) {
            InputStream in = img.getEntityAs(InputStream.class);
            try {
                imageToUpload = new NewImageModel(StreamUtils.copyToByteArray(in), img.getMediaType().toString());
            } catch (IOException e) {
                LOGGER.error("Error getting bytes from images");
                throw new ServerInternalException();
            }
        }

        //TODO tengo dudas de esto
        final CityModel city = locationService.getCityByName(experienceDto.getCity()).orElseThrow(CityNotFoundException::new);
        final CategoryModel category = categoryService.getCategoryById(experienceDto.getCategory()).orElseThrow(CategoryNotFoundException::new);

        ExperienceModel experience;
        try {
            experience = experienceService.createExperience(
                experienceDto.getName(), experienceDto.getAddress(),
                experienceDto.getInformation(), experienceDto.getMail(),
                experienceDto.getUrl(), Double.parseDouble(experienceDto.getPrice()),
                city, category, user, imageToUpload.getImage(),
                imageToUpload.getMimeType());
        } catch (DuplicateExperienceException e) {
            LOGGER.warn("Error in experienceDto ExperienceForm, there is already an experience with this id");
            throw new DuplicateExperienceException();
        }

        LOGGER.info("Created experience with id {}", experience.getExperienceId());
        return Response.created(ExperienceDto.getExperienceUriBuilder(experience, uriInfo).build()).build();
    }

    // Endpoint para obtener una experiencia a partir de su ID
    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperienceId(@PathParam("id") final long id) {
        LOGGER.info("Called /experiences/{} GET", id);
        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);
        final ExperienceDto experienceDto = new ExperienceDto(experience, uriInfo);
        return Response.ok(experienceDto).build();
    }

    // Endpoint para editar una experiencia
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
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

        final UserModel user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);

        if (experience.getUser().getUserId() != (user.getUserId())) {
            LOGGER.error("Error, user with id {} is trying to update the experience with id {} that belongs to user with id {}",
                    user.getUserId(), id, experience.getUser().getUserId());
            throw new IllegalOperationException();
        }

        final FormDataBodyPart img = experienceDto.getImage();
        NewImageModel imageToUpload = null;

        if (img != null) {
            InputStream in = img.getEntityAs(InputStream.class);
            try {
                imageToUpload = new NewImageModel(StreamUtils.copyToByteArray(in), img.getMediaType().toString());
            } catch (IOException e) {
                LOGGER.error("Error getting bytes from images");
                throw new ServerInternalException();
            }
        }

        experienceService.updateExperience(experience, imageToUpload.getImage(), imageToUpload.getMimeType());
        LOGGER.info("The experience with id {} has been updated successfully", id);
        return Response.created(ExperienceDto.getExperienceUriBuilder(experience, uriInfo).build()).build();
    }

    // Endpoint para obtener la imagen de una experiencia
    @GET
    @Path("/{id}/image")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getExperienceImage(@PathParam("id") final long id) {

        final ImageModel image = imageService.getImgById(id).orElseThrow(ImageNotFoundException::new);

        if (image.getImage() != null) {
            final CacheControl cacheControl = new CacheControl();
            cacheControl.setNoTransform(false);
            cacheControl.getCacheExtension().put("public", null);
            cacheControl.setMaxAge(31536000);
            cacheControl.getCacheExtension().put("immutable", null);

            return Response.ok(image.getImage())
                    .type(image.getMimeType())
                    .cacheControl(cacheControl)
                    .build();
//            Response.ResponseBuilder response = Response.ok(new ByteArrayInputStream(imageModel.getImage()));
//            return response.build();
        }
        return Response.noContent().build();
    }

    // Endpoint para obtener la reseñas de una experiencia
    @GET
    @Path("/{id}/reviews")
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
    @Path("/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response createExperienceReview(@PathParam("id") final long id, @Valid NewReviewDto newReviewDto, @Valid ReviewDto reviewDto, @Valid UserDto userDto) {
        LOGGER.info("Creating review with /experience/category/{}/create_review POST", id);
        final ReviewModel reviewModel = reviewService.createReview(newReviewDto.getTitle(), newReviewDto.getDescription(), newReviewDto.getLongScore(), experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new), reviewDto.getReviewDate(), userService.getUserById(userDto.getId()).orElseThrow(UserNotFoundException::new));
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

