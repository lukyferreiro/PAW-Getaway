package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.*;
import ar.edu.itba.getaway.interfaces.services.*;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.webapp.constraints.DtoConstraintValidator;
import ar.edu.itba.getaway.webapp.constraints.exceptions.DtoValidationException;
import ar.edu.itba.getaway.webapp.controller.util.PaginationResponse;
import ar.edu.itba.getaway.webapp.dto.request.NewExperienceDto;
import ar.edu.itba.getaway.webapp.dto.request.NewReviewDto;
import ar.edu.itba.getaway.webapp.dto.response.*;
import ar.edu.itba.getaway.webapp.security.services.AuthContext;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDate;
import java.util.*;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("experiences")
@Component
public class ExperienceController {
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private FavAndViewExperienceService favAndViewExperienceService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private int maxRequestSize;
    @Autowired
    private AuthContext authContext;
    @Autowired
    private DtoConstraintValidator dtoValidator;
    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceController.class);
    private static final String ACCEPTED_MIME_TYPES = "image/";

    @GET
    @Path("/landingPage")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getLandingPageExperiences(){
        final UserModel user = authContext.getCurrentUser();

        List<List<ExperienceModel>> landingPageList;

        if (user != null ){
            landingPageList = experienceService.userLandingPage(user);
            return Response.ok(new GenericEntity<UserLandingPageDto>(new UserLandingPageDto(landingPageList, uriInfo)){}).build();
        }
        else {
            landingPageList = experienceService.getExperiencesListByCategories(null);
            return Response.ok(new GenericEntity<AnonymousLandingPageDto>(new AnonymousLandingPageDto(landingPageList, uriInfo)){}).build();

        }
    }

    // Endpoint para obtener las experiencias de una categoria
    @GET
    @Path("/filter")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperiencesFromCategory(
            @QueryParam("category") @DefaultValue("") String category,
            @QueryParam("name") @DefaultValue("") String name,
            @QueryParam("order") @DefaultValue("OrderByAZ") OrderByModel order,
            @QueryParam("price") @DefaultValue("-1") Double maxPrice,
            @QueryParam("score") @DefaultValue("0") Long maxScore,
            @QueryParam("city") @DefaultValue("-1") Long cityId,
            @QueryParam("page") @DefaultValue("1") int page
    ) {
        LOGGER.info("Called /experiences/{} GET", category);

        CategoryModel categoryModel = null;
        if (!category.equals("")){
            categoryModel = categoryService.getCategoryByName(category).orElseThrow(CategoryNotFoundException::new);
        }

        if (maxPrice == -1) {
            maxPrice = experienceService.getMaxPriceByCategoryAndName(categoryModel, name).orElse(0.0);
        }
        CityModel cityModel = null;
        if (cityId != -1) {
            cityModel = locationService.getCityById(cityId).orElseThrow(CityNotFoundException::new);
        }

        final UserModel user = authContext.getCurrentUser();
        final Page<ExperienceModel> experiences = experienceService.listExperiencesByFilter(categoryModel, name, maxPrice, maxScore, cityModel, Optional.of(order), page, user);

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
                .queryParam("name", name)
                .queryParam("order", order)
                .queryParam("price", maxPrice)
                .queryParam("score", maxScore)
                .queryParam("page", page);

        if (cityModel != null) {
            uriBuilder.queryParam("city", cityId);
        }

        return PaginationResponse.createPaginationResponse(experiences, new GenericEntity<Collection<ExperienceDto>>(experienceDto) {
        }, uriBuilder);
    }

    @GET
    @Path("/filter/maxPrice")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getCategoryMaxPrice(
            @QueryParam("category") @DefaultValue("") final String category,
            @QueryParam("name") @DefaultValue("") String name
        ){
        CategoryModel categoryModel = null;
        if (!category.equals("")){
            categoryModel = categoryService.getCategoryByName(category).orElseThrow(CategoryNotFoundException::new);
        }
        final Double maxPrice = experienceService.getMaxPriceByCategoryAndName(categoryModel, name).orElse(0.0);
        return Response.ok(new MaxPriceDto(maxPrice)).build();
    }

    @GET
    @Path("/filter/orderByModels")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getOrderByModels(
            @QueryParam("user") @DefaultValue("false") boolean user,
            @QueryParam("provider") @DefaultValue("false") boolean owner
    ){
        OrderByModel[] orderByModels = null;

        if ((owner && user) || (!owner && !user)) {
            throw new BadRequestException();
        }

        if (owner){
            orderByModels = OrderByModel.getProviderOrderByModel();
        }
        else if (user) {
            orderByModels = OrderByModel.getUserOrderByModel();
        }

        Collection<OrderByDto> orderByDtos = OrderByDto.mapOrderByToDto(Arrays.asList(orderByModels));
        return Response.ok(new GenericEntity<Collection<OrderByDto>>(orderByDtos) {}).build();
    }

    // Endpoint para crear una experiencia
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response registerExperience(
            @Valid final NewExperienceDto experienceDto
    ) throws DuplicateExperienceException, DtoValidationException {

        LOGGER.info("Called /experiences/ POST");

        if (experienceDto == null) {
            throw new ContentExpectedException();
        }
        dtoValidator.validate(experienceDto, "Invalid Body Request");

        final UserModel user = authContext.getCurrentUser();
        final CityModel city = locationService.getCityByName(experienceDto.getCity()).orElseThrow(CityNotFoundException::new);
        final CategoryModel category = categoryService.getCategoryById(experienceDto.getCategory()).orElseThrow(CategoryNotFoundException::new);

        ExperienceModel experience;
        try {
            experience = experienceService.createExperience(
                experienceDto.getName(), experienceDto.getAddress(),
                experienceDto.getDescription(), experienceDto.getMail(),
                experienceDto.getUrl(), experienceDto.getPrice(),
                city, category, user);
        } catch (DuplicateExperienceException e) {
            LOGGER.warn("Error in experienceDto ExperienceForm, there is already an experience with this id");
            throw new DuplicateExperienceException();
        }

        LOGGER.info("Created experience with id {}", experience.getExperienceId());
        return Response.created(ExperienceDto.getExperienceUriBuilder(experience, uriInfo).build()).build();
    }

    //TODO: limit when views is increased somehow

    // Endpoint para obtener una experiencia a partir de su ID
    @GET
    @Path("/experience/{experienceId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperienceId(
            @PathParam("experienceId") final long id,
            @QueryParam("view") @DefaultValue("false") final boolean view
    ) {

        LOGGER.info("Called /experiences/{} GET", id);

        final UserModel user = authContext.getCurrentUser();
        final ExperienceModel experience = experienceService.getVisibleExperienceById(id, user).orElseThrow(ExperienceNotFoundException::new);

        if(view) {
            favAndViewExperienceService.setViewed(user, experience);
            if (user!=null && !experience.getUser().equals(user)) {
                experienceService.increaseViews(experience);
            }
        }

        final ExperienceDto experienceDto = new ExperienceDto(experience, uriInfo);
        return Response.ok(experienceDto).build();
    }

    // Endpoint para obtener una experiencia a partir de su ID
    @GET
    @Path("/experience/{experienceId}/name")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperienceNameById(
            @PathParam("experienceId") final long id
    ) {
        LOGGER.info("Called /experiences/{} GET", id);

        final UserModel user = authContext.getCurrentUser();
        final ExperienceModel experience = experienceService.getVisibleExperienceById(id, user).orElseThrow(ExperienceNotFoundException::new);

        final ExperienceNameDto experienceDto = new ExperienceNameDto(experience, uriInfo);
        return Response.ok(experienceDto).build();
    }

    // Endpoint para editar una experiencia
    @PUT
    @Path("/experience/{experienceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response updateExperience(
            @Context final HttpServletRequest request,
            @Valid final NewExperienceDto experienceDto,
            @PathParam("experienceId") final long id
    ) {

        LOGGER.info("Called /experiences/{} PUT", id);

        if (request.getContentLength() == -1 || request.getContentLength() > maxRequestSize) {
            throw new MaxUploadSizeRequestException();
        }

        if (experienceDto == null) {
            throw new ContentExpectedException();
        }
        dtoValidator.validate(experienceDto, "Invalid Body Request");

        final UserModel user = authContext.getCurrentUser();
        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);

        if (experience.getUser().getUserId() != (user.getUserId())) {
            LOGGER.error("Error, user with id {} is trying to update the experience with id {} that belongs to user with id {}",
                    user.getUserId(), id, experience.getUser().getUserId());
            throw new IllegalOperationException();
        }

        final CityModel cityModel = locationService.getCityByName(experienceDto.getCity()).orElseThrow(CityNotFoundException::new);
        final CategoryModel categoryModel = categoryService.getCategoryById(experienceDto.getCategory()).orElseThrow(CategoryNotFoundException::new);

        final ExperienceModel toUpdateExperience = new ExperienceModel(id, experienceDto.getName(), experienceDto.getAddress(), experienceDto.getDescription(),
        experienceDto.getMail(), experienceDto.getUrl(), experienceDto.getPrice(), cityModel, categoryModel, user, experience.getExperienceImage(), experience.getObservable(), experience.getViews());

        experienceService.updateExperience(toUpdateExperience);
        LOGGER.info("The experience with id {} has been updated successfully", id);
        return Response.ok().build();
    }

    // Endpoint para eliminar una experiencia
    @DELETE
    @Path("/experience/{experienceId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteExperience(
            @PathParam("experienceId") final long id
    ) {

        final UserModel user = authContext.getCurrentUser();
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
    @Path("/experience/{experienceId}/experienceImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getExperienceImage(
            @PathParam("experienceId") final long id,
            @Context final Request request
    ) {

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
    @Path("/experience/{experienceId}/experienceImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response updateExperienceImage(
            @PathParam("experienceId") final long id,
            @FormDataParam("experienceImage") final FormDataBodyPart experienceImageBody,
            @Size(max = 1024 * 1024) @FormDataParam("experienceImage") byte[] experienceImageBytes
    ) {

        if (experienceImageBody == null) {
            throw new ContentExpectedException();
        }

        if (!experienceImageBody.getMediaType().toString().contains(ACCEPTED_MIME_TYPES)) {
            throw new IllegalContentTypeException();
        }

        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);

        imageService.updateImg(experienceImageBytes, experienceImageBody.getMediaType().toString(), experience.getExperienceImage());

        return Response.noContent()
                .contentLocation(ExperienceDto.getExperienceUriBuilder(experience, uriInfo).path("experienceImage").build())
                .build();
    }

    // Endpoint para obtener la reseñas de una experiencia
    @GET
    @Path("/experience/{experienceId}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperienceReviews(
            @PathParam("experienceId") final long id,
            @QueryParam("page") @DefaultValue("1") final int page
    ) {

        LOGGER.info("Called /experiences/{}/reviews GET", id);
        final ExperienceModel experienceModel = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);
        Page<ReviewModel> reviewModelList = reviewService.getReviewAndUser(experienceModel, page);

        final Collection<ReviewDto> reviewDto = ReviewDto.mapReviewToDto(reviewModelList.getContent(), uriInfo);

        final UriBuilder uriBuilder = uriInfo
                .getAbsolutePathBuilder()
                .queryParam("page", page);

        return PaginationResponse.createPaginationResponse(reviewModelList, new GenericEntity<Collection<ReviewDto>>(reviewDto) {
        }, uriBuilder);
    }

    // Endpoint para crear una reseña en la experiencia
    @POST
    @Path("/experience/{experienceId}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response createExperienceReview(
            @PathParam("experienceId") final long id,
            @Valid final NewReviewDto newReviewDto
    ) throws DtoValidationException {

        if (newReviewDto == null) {
            throw new ContentExpectedException();
        }
        dtoValidator.validate(newReviewDto, "Invalid Body Request");

        LOGGER.info("Creating review with /experience/category/{}/create_review POST", id);
        //TODO: check usage of localdate.now()

        final UserModel user = authContext.getCurrentUser();
        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);

        final ReviewModel reviewModel = reviewService.createReview(newReviewDto.getTitle(), newReviewDto.getDescription(), newReviewDto.getLongScore(), experience, LocalDate.now(), user);
        return Response.created(ReviewDto.getReviewUriBuilder(reviewModel, uriInfo).build()).build();
    }

    @PUT
    @Path("/experience/{experienceId}/fav")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response favExperience(
            @PathParam("experienceId") final long id,
            @QueryParam("set") final boolean set
    ) {
        LOGGER.info("Called /experiences/{} GET", id);

        final UserModel user = authContext.getCurrentUser();
        final ExperienceModel experience = experienceService.getVisibleExperienceById(id, user).orElseThrow(ExperienceNotFoundException::new);

        favAndViewExperienceService.setFav(user, set, experience);

        return Response.noContent().build();
    }

    @PUT
    @Path("/experience/{experienceId}/observable")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response observable(
            @PathParam("experienceId") final long id,
            @QueryParam("set") final boolean set
    ) {
        LOGGER.info("Called /experiences/{} GET", id);

        final UserModel user = authContext.getCurrentUser();
        final ExperienceModel experience = experienceService.getVisibleExperienceById(id, user).orElseThrow(ExperienceNotFoundException::new);

        experienceService.changeVisibility(experience, set);

        return Response.noContent().build();
    }
}

