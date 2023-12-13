package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.*;
import ar.edu.itba.getaway.interfaces.services.*;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.webapp.controller.queryParamsValidators.GetExperiencesFilter;
import ar.edu.itba.getaway.webapp.controller.queryParamsValidators.GetExperiencesParams;
import ar.edu.itba.getaway.webapp.controller.queryParamsValidators.GetOrdersParams;
import ar.edu.itba.getaway.webapp.controller.queryParamsValidators.InvalidRequestParamsException;
import ar.edu.itba.getaway.webapp.controller.util.CacheResponse;
import ar.edu.itba.getaway.webapp.controller.util.PaginationResponse;
import ar.edu.itba.getaway.webapp.dto.request.NewExperienceDto;
import ar.edu.itba.getaway.webapp.dto.request.PatchVisibilityDto;
import ar.edu.itba.getaway.webapp.dto.response.*;
import ar.edu.itba.getaway.webapp.security.api.CustomMediaType;
import ar.edu.itba.getaway.webapp.security.services.AuthContext;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
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
    private final Integer maxRequestSize;
    private final AuthContext authContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceController.class);
    private static final String ACCEPTED_MIME_TYPES = "image/";

    @Autowired
    public ExperienceController(ExperienceService experienceService, FavAndViewExperienceService favAndViewExperienceService,
                                CategoryService categoryService, LocationService locationService, ImageService imageService,
                                Integer maxRequestSize, AuthContext authContext) {
        this.experienceService = experienceService;
        this.favAndViewExperienceService = favAndViewExperienceService;
        this.categoryService = categoryService;
        this.locationService = locationService;
        this.imageService = imageService;
        this.maxRequestSize = maxRequestSize;
        this.authContext = authContext;
    }

    // Endpoint para crear una experiencia
    @POST
    @Consumes(value = {CustomMediaType.EXPERIENCE_V1})
    public Response createExperience(
            @Valid final NewExperienceDto experienceDto
    ) throws DuplicateExperienceException {

        LOGGER.info("Called /experiences POST");

        if (experienceDto == null) {
            throw new ContentExpectedException();
        }

        final UserModel user = authContext.getCurrentUser();
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
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(experience.getExperienceId())).build();
        return Response.created(location).build();
    }

    // Endpoint para obtener las experiencias
    @GET
    @Produces(value = {CustomMediaType.EXPERIENCE_LIST_V1})
    @PreAuthorize("@antMatcherVoter.checkGetExperiences(authentication, #userId, #filter)")
    public Response getExperiences(
            @QueryParam("category") @DefaultValue("") String category,
            @QueryParam("name") @DefaultValue("") String name,
            @QueryParam("order") @DefaultValue("OrderByAZ") OrderByModel order,  //TODO cambiar a string
            @QueryParam("price") @DefaultValue("-1") Double maxPrice,
            @QueryParam("score") @DefaultValue("0") Long maxScore,
            @QueryParam("city") @DefaultValue("-1") Long cityId,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("userId") Long userId,
            @QueryParam("filter") @DefaultValue("SEARCH") String filter
    ) {
        LOGGER.info("Called /experiences GET");

        final GetExperiencesFilter getExperiencesFilter = GetExperiencesFilter.fromString(filter);
        final GetExperiencesParams params = getExperiencesFilter.validateParams(authContext, categoryService, experienceService, locationService, category, name, order, maxPrice, maxScore, cityId, userId);
        final Page<ExperienceModel> experiences = getExperiencesFilter.getExperiences(experienceService, params, page);
        final UriBuilder uriBuilder = getExperiencesFilter.getUriBuilder(uriInfo, category, name, order, maxPrice, maxScore, params.getCity(), userId, page);

        if (experiences.getContent().isEmpty()) {
            return Response.noContent().build();
        }

        final Collection<ExperienceDto> experienceDto = ExperienceDto.mapExperienceToDto(experiences.getContent(), uriInfo);

        return PaginationResponse.createPaginationResponse(experiences, new GenericEntity<Collection<ExperienceDto>>(experienceDto) {
        }, uriBuilder);
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

        experienceService.increaseViews(user, view, experience);
        favAndViewExperienceService.setViewed(user, view, experience);

        final ExperienceDto experienceDto = new ExperienceDto(experience, uriInfo);
        return Response.ok(experienceDto).build();
    }

    // Endpoint para editar una experiencia
    @PUT
    @Path("/{experienceId:[0-9]+}")
    @Consumes(value = {CustomMediaType.EXPERIENCE_V1})
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
        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);
        final CityModel cityModel = locationService.getCityById(experienceDto.getCity()).orElseThrow(CityNotFoundException::new);
        final CategoryModel categoryModel = categoryService.getCategoryById(experienceDto.getCategory()).orElseThrow(CategoryNotFoundException::new);

        final ExperienceModel toUpdateExperience = new ExperienceModel(
                id, experienceDto.getName(), experienceDto.getAddress(), experienceDto.getDescription(),
                experienceDto.getMail(), experienceDto.getUrl(), experienceDto.getPrice(), cityModel,
                categoryModel, user, experience.getExperienceImage(), experience.getObservable(), experience.getViews());

        experienceService.updateExperience(toUpdateExperience);
        LOGGER.info("The experience with id {} has been updated successfully", id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{experienceId:[0-9]+}")
    @Consumes(value = {CustomMediaType.EXPERIENCE_VISIBILITY_V1})
    @PreAuthorize("@antMatcherVoter.canEditExperienceById(authentication, #experienceId)")
    public Response setVisibility(
            @Context final HttpServletRequest request,
            @Valid final PatchVisibilityDto patchVisibilityDto,
            @PathParam("experienceId") final long experienceId
    ) {
        LOGGER.info("Called /experiences/{} PATCH", experienceId);

        if (request.getContentLength() == -1 || request.getContentLength() > maxRequestSize) {
            throw new MaxUploadSizeRequestException();
        }

        if (patchVisibilityDto == null) {
            throw new ContentExpectedException();
        }

        final UserModel user = authContext.getCurrentUser();
        final ExperienceModel experience = experienceService.getVisibleExperienceById(experienceId, user).orElseThrow(ExperienceNotFoundException::new);
        experienceService.changeVisibility(experience, patchVisibilityDto.getVisibility());
        return Response.noContent().build();
    }

    // Endpoint para eliminar una experiencia
    @DELETE
    @Path("/{experienceId:[0-9]+}")
    public Response deleteExperience(
            @PathParam("experienceId") final long id
    ) {
        LOGGER.info("Called /experiences/{} DELETE", id);
        final ExperienceModel experienceModel = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);
        experienceService.deleteExperience(experienceModel);
        return Response.noContent().build();
    }

    // Endpoint para obtener la imagen de una experiencia
    @GET
    @Path("/{experienceId:[0-9]+}/experienceImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})       //TODO check @Produces(MediaType.MULTIPART_FORM_DATA)
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
    @Consumes(MediaType.MULTIPART_FORM_DATA)
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
        return Response.noContent().build();
    }


    @PUT
    @Path("/{experienceId:[0-9]+}/fav")
    public Response favExperience(
            @PathParam("experienceId") final long id,
            @QueryParam("fav") final Boolean fav
    ) {
        LOGGER.info("Called /experiences/{}/fav PUT", id);

        if(fav == null){
            throw new InvalidRequestParamsException("errors.invalidParam.fav");
        }

        final UserModel user = authContext.getCurrentUser();
        final ExperienceModel experience = experienceService.getVisibleExperienceById(id, user).orElseThrow(ExperienceNotFoundException::new);
        favAndViewExperienceService.setFav(user, fav, experience);
        return Response.noContent().build();
    }


    @GET
    @Path("/orders")
    @Produces(value = {CustomMediaType.ORDER_LIST_V1})
    public Response getExperiencesOrders(
            @QueryParam("provider") @DefaultValue("false") Boolean isProvider
    ){
        LOGGER.info("Called /experiences/orders GET");
        OrderByModel[] orderByModels = GetOrdersParams.getOrdersByParams(isProvider);
        return Response.ok(new OrdersDto(orderByModels, uriInfo)).build();
    }

    @GET
    @Path("/categories")
    @Produces(value = {CustomMediaType.CATEGORY_LIST_V1})
    public Response getCategories(){
        LOGGER.info("Called /categories GET");
        final List<CategoryModel> categories = categoryService.listAllCategories();
        final Collection<CategoryDto> categoriesDtos = CategoryDto.mapCategoriesToDto(categories, uriInfo);
        return Response.ok(new GenericEntity<Collection<CategoryDto>>(categoriesDtos) {}).build();
    }

    @GET
    @Path("/categories/{categoryId:[0-9]+}")
    @Produces(value = {CustomMediaType.CATEGORY_V1})
    public Response getCategoryById(
            @PathParam("categoryId") final long id
    ) {
        LOGGER.info("Called /categories/{} GET", id);
        final CategoryModel category = categoryService.getCategoryById(id).orElseThrow(CategoryNotFoundException::new);
        return Response.ok(new CategoryDto(category, uriInfo)).build();
    }
}