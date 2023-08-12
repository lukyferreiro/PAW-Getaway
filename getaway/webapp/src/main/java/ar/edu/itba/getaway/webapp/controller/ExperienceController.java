package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.*;
import ar.edu.itba.getaway.interfaces.services.*;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.webapp.controller.util.CacheResponse;
import ar.edu.itba.getaway.webapp.controller.util.PaginationResponse;
import ar.edu.itba.getaway.webapp.dto.request.NewExperienceDto;
import ar.edu.itba.getaway.webapp.dto.response.*;
import ar.edu.itba.getaway.webapp.security.api.CustomMediaType;
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
import java.util.*;

@Path("experiences")
@Component
public class ExperienceController {

    @Context
    private UriInfo uriInfo;
    private final ExperienceService experienceService;
    private final FavAndViewExperienceService favAndViewExperienceService;
    private final CategoryService categoryService;
    private final LocationService locationService;
    private final ImageService imageService;
    private final ReviewService reviewService;
    private final Integer maxRequestSize;
    private final AuthContext authContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceController.class);
    private static final String ACCEPTED_MIME_TYPES = "image/";

    @Autowired
    public ExperienceController(ExperienceService experienceService, FavAndViewExperienceService favAndViewExperienceService, CategoryService categoryService,
                                LocationService locationService, ImageService imageService, ReviewService reviewService,
                                Integer maxRequestSize, AuthContext authContext) {
        this.experienceService = experienceService;
        this.favAndViewExperienceService = favAndViewExperienceService;
        this.categoryService = categoryService;
        this.locationService = locationService;
        this.imageService = imageService;
        this.reviewService = reviewService;
        this.maxRequestSize = maxRequestSize;
        this.authContext = authContext;
    }

    // Endpoint para crear una experiencia
    @POST
    @Produces(value = {CustomMediaType.EXPERIENCE_V1})
    public Response createExperience(
            @Valid final NewExperienceDto experienceDto
    ) throws DuplicateExperienceException {

        LOGGER.info("Called /experiences/ POST");

        if (experienceDto == null) {
            throw new ContentExpectedException();
        }

        final UserModel user = authContext.getCurrentUser();
        //TODO sacar estos get y meterlos en createExperience del service
        final CityModel city = locationService.getCityById(experienceDto.getCity()).orElseThrow(CityNotFoundException::new);
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

    // TODO habria que agregar el country como queryParam ????
    // TODO chequear todo esto para los filtros
    // Endpoint para obtener las experiencias
    @GET
    @Produces(value = {CustomMediaType.EXPERIENCE_LIST_V1})
    public Response getExperiences(
            @QueryParam("category") @DefaultValue("") String category,
            @QueryParam("name") @DefaultValue("") String name,
            @QueryParam("order") @DefaultValue("OrderByAZ") OrderByModel order,
            @QueryParam("price") @DefaultValue("-1") Double maxPrice,
            @QueryParam("score") @DefaultValue("0") Long maxScore,
            @QueryParam("city") @DefaultValue("-1") Long cityId,
            @QueryParam("page") @DefaultValue("1") int page
    ) {
        LOGGER.info("Called /experiences GET");

        //TODO chequear como manejar los filtros

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

    //TODO ver si se puede fusionar este en el endpoint anterior
    //TODO puede volar? no mg tener este endpoint
    @GET
    @Path("/maxPrice")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperiencesMaxPrice(
            @QueryParam("category") @DefaultValue("") final String category,
            @QueryParam("name") @DefaultValue("") String name
        ){
        LOGGER.info("Called /experiences/maxPrice GET");
        CategoryModel categoryModel = null;

        if (!category.equals("")){
            categoryModel = categoryService.getCategoryByName(category).orElseThrow(CategoryNotFoundException::new);
        }

        final Double maxPrice = experienceService.getMaxPriceByCategoryAndName(categoryModel, name).orElse(0.0);
        return Response.ok(new MaxPriceDto(maxPrice, uriInfo)).build();
    }

    //TODO chequear
    @GET
    @Path("/orders")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperiencesOrders(
            @QueryParam("user") @DefaultValue("false") boolean user,
            @QueryParam("provider") @DefaultValue("false") boolean owner
    ){
        LOGGER.info("Called /experiences/orders GET");
        OrderByModel[] orderByModels = null;

        if ((owner && user) || (!owner && !user)) {
            throw new BadRequestException();
        }

        if (owner) {
            orderByModels = OrderByModel.getProviderOrderByModel();
        } else if (user) {
            orderByModels = OrderByModel.getUserOrderByModel();
        }

        Collection<OrderByDto> orderByDtos = OrderByDto.mapOrderByToDto(Arrays.asList(orderByModels), uriInfo);
        return Response.ok(new GenericEntity<Collection<OrderByDto>>(orderByDtos) {}).build();
    }

    //Endpoint para obtener las recomendaciones para un usuario
    @GET
    @Path("/recommendations")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperiencesRecommendations() {
        LOGGER.info("Called /experiences/recommendations GET");
        List<List<ExperienceModel>> recommendations= experienceService.getExperiencesListByCategories(null);
        return Response.ok(new GenericEntity<AnonymousRecommendationsDto>(new AnonymousRecommendationsDto(recommendations, uriInfo)) {
        }).build();

    }

    // Endpoint para obtener una experiencia a partir de su ID
    @GET
    @Path("/{experienceId:[0-9]+}")
    @Produces(value = {CustomMediaType.EXPERIENCE_V1})
    public Response getExperienceId(
            @PathParam("experienceId") final long id,
            @QueryParam("view") @DefaultValue("false") final boolean view
    ) {

        LOGGER.info("Called /experiences/{} GET", id);

        final UserModel user = authContext.getCurrentUser();
        final ExperienceModel experience = experienceService.getVisibleExperienceById(id, user).orElseThrow(ExperienceNotFoundException::new);

        // TODO habria que sacar esta logica de aca
        if (view) {
            favAndViewExperienceService.setViewed(user, experience);
            if (user != null && !experience.getUser().equals(user)) {
                experienceService.increaseViews(experience);
            }
        }

        final ExperienceDto experienceDto = new ExperienceDto(experience, uriInfo);
        return Response.ok(experienceDto).build();
    }

    // Endpoint para editar una experiencia
    @PUT
    @Path("/{experienceId:[0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {CustomMediaType.EXPERIENCE_V1})
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

        final UserModel user = authContext.getCurrentUser();
        //TODO sacar estos get
        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);
        final CityModel cityModel = locationService.getCityById(experienceDto.getCity()).orElseThrow(CityNotFoundException::new);
        final CategoryModel categoryModel = categoryService.getCategoryById(experienceDto.getCategory()).orElseThrow(CategoryNotFoundException::new);

        final ExperienceModel toUpdateExperience = new ExperienceModel(
                id, experienceDto.getName(), experienceDto.getAddress(), experienceDto.getDescription(),
                experienceDto.getMail(), experienceDto.getUrl(), experienceDto.getPrice(), cityModel,
                categoryModel, user, experience.getExperienceImage(), experience.getObservable(), experience.getViews());

        experienceService.updateExperience(toUpdateExperience);
        LOGGER.info("The experience with id {} has been updated successfully", id);
        //TODO devolver la experiencia actualizada ??
        return Response.ok().build();
    }

    // Endpoint para eliminar una experiencia
    @DELETE
    @Path("/{experienceId:[0-9]+}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteExperience(
            @PathParam("experienceId") final long id
    ) {
        LOGGER.info("Called /experiences/{} DELETE", id);
        final ExperienceModel experienceModel = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);
        experienceService.deleteExperience(experienceModel);
        return Response.noContent().build();
    }

    // Endpoint para obtener una experiencia a partir de su ID
    // TODO este endpoint??? puede volar
    @GET
    @Path("/{experienceId:[0-9]+}/name")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperienceNameById(
            @PathParam("experienceId") final long id
    ) {
        LOGGER.info("Called /experiences/{}/name GET", id);

        final UserModel user = authContext.getCurrentUser();
        final ExperienceModel experience = experienceService.getVisibleExperienceById(id, user).orElseThrow(ExperienceNotFoundException::new);

        final ExperienceNameDto experienceDto = new ExperienceNameDto(experience, uriInfo);
        return Response.ok(experienceDto).build();
    }

    // Endpoint para obtener la imagen de una experiencia
    @GET
    @Path("/{experienceId:[0-9]+}/experienceImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getExperienceImage(
            @PathParam("experienceId") final long id,
            @Context final Request request
    ) {
        LOGGER.info("Called /experiences/{}/experienceImage GET", id);

        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(UserNotFoundException::new);

        if(experience.getImage() == null) {
            return Response.noContent().build();
        }

        final ImageModel image = experience.getExperienceImage();
        return CacheResponse.cacheResponse(image, request);
    }

    @PUT
    @Path("/{experienceId:[0-9]+}/experienceImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response updateExperienceImage(
            @PathParam("experienceId") final long id,
            @FormDataParam("experienceImage") final FormDataBodyPart experienceImageBody,
            @Size(max = 1024 * 1024) @FormDataParam("experienceImage") byte[] experienceImageBytes
    ) {
        LOGGER.info("Called /experiences/{}/experienceImage PUT", id);

        if (experienceImageBody == null) {
            throw new ContentExpectedException();
        }

        if (!experienceImageBody.getMediaType().toString().contains(ACCEPTED_MIME_TYPES)) {
            throw new IllegalContentTypeException();
        }

        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);
        imageService.updateImg(experienceImageBytes, experienceImageBody.getMediaType().toString(), experience.getExperienceImage());
        return Response.ok().build();
    }

    // Endpoint para obtener la reseñas de una experiencia
    //TODO check
//    @GET
//    @Path("/{experienceId:[0-9]+}/reviews")
//    @Produces(value = {MediaType.APPLICATION_JSON})
//    public Response getExperienceReviews(
//            @PathParam("experienceId") final long id,
//            @QueryParam("page") @DefaultValue("1") final int page
//    ) {
//
//        LOGGER.info("Called /experiences/{}/reviews GET", id);
//        final ExperienceModel experienceModel = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);
//        Page<ReviewModel> reviewModelList = reviewService.getReviewsByExperience(experienceModel, page);
//
//        final Collection<ReviewDto> reviewDto = ReviewDto.mapReviewToDto(reviewModelList.getContent(), uriInfo);
//
//        final UriBuilder uriBuilder = uriInfo
//                .getAbsolutePathBuilder()
//                .queryParam("page", page);
//
//        return PaginationResponse.createPaginationResponse(reviewModelList, new GenericEntity<Collection<ReviewDto>>(reviewDto) {
//        }, uriBuilder);
//    }

    //TODO ver si se puede unificar en otro
    @PUT
    @Path("/{experienceId:[0-9]+}/fav")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response favExperience(
            @PathParam("experienceId") final long id,
            @QueryParam("set") final boolean set
    ) {
        LOGGER.info("Called /experiences/{}/fav PUT", id);

        final UserModel user = authContext.getCurrentUser();
        final ExperienceModel experience = experienceService.getVisibleExperienceById(id, user).orElseThrow(ExperienceNotFoundException::new);
        favAndViewExperienceService.setFav(user, set, experience);
        return Response.ok().build();
    }

    //TODO ver si se puede unificar en otro
    @PUT
    @Path("/{experienceId:[0-9]+}/observable")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response observable(
            @PathParam("experienceId") final long id,
            @QueryParam("set") final boolean set
    ) {
        LOGGER.info("Called /experiences/{}/observable PUT", id);

        final UserModel user = authContext.getCurrentUser();
        final ExperienceModel experience = experienceService.getVisibleExperienceById(id, user).orElseThrow(ExperienceNotFoundException::new);
        experienceService.changeVisibility(experience, set);
        return Response.ok().build();
    }
}