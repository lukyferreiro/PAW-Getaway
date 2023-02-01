package ar.edu.itba.getaway.webapp.controller.apiControllers;

import ar.edu.itba.getaway.interfaces.exceptions.ContentExpectedException;
import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.interfaces.exceptions.IllegalContentTypeException;
import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.interfaces.services.*;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.webapp.dto.request.*;
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

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.Collection;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("users")
@Component
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private TokensService tokensService;
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private AuthFacade authFacade;
    @Autowired
    private int maxRequestSize;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final String ACCEPTED_MIME_TYPES = "image/";

    //Endpoint que crea un usuario nuevo
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response registerUser(@Valid final RegisterDto registerDto) throws DuplicateUserException {
        LOGGER.info("Called /users/ POST");
        if (registerDto == null) {
            throw new ContentExpectedException();
        }
        UserModel user;
        try {
            user = userService.createUser(registerDto.getPassword(), registerDto.getName(), registerDto.getSurname(), registerDto.getEmail());
        } catch (DuplicateUserException e) {
            LOGGER.warn("Error in registerDto RegisterForm, email is already in used");
            throw new DuplicateUserException();
        }

        return Response.created(UserDto.getUserUriBuilder(user, uriInfo).build()).build();
    }

    //Endpoint que devuelve informacion de un usuario segun el ID
    @GET
    @Path("/{userId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getUser(@PathParam("userId") final long id) {
        LOGGER.info("Called /users/{} GET", id);
        final UserModel user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);
        LOGGER.info("Return user with id {}", id);
        return Response.ok(new UserDto(user, uriInfo)).build();
    }

    //Endpoint que devuelve informacion de un usuario segun el ID
    @GET
    @Path("/currentUser")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getCurrentUser() {
        LOGGER.info("Called /users/currentUser GET");
        final UserModel user = authFacade.getCurrentUser();

        if(user != null){
            return Response.ok(new UserDto(user, uriInfo)).build();
        }

        return Response.noContent().build();
    }

    //Endpoint para editar la informacion del usuario
    @PUT
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateUser(@Valid UserInfoDto userInfoDto, @PathParam("userId") final long id) {
        LOGGER.info("Called /users/ PUT");

        if (userInfoDto == null) {
            throw new ContentExpectedException();
        }

        final UserModel user = authFacade.getCurrentUser();

        // TODO: check if useless due to antmatcher
        //        assureUserResourceCorrelation(user, id);
        userService.updateUserInfo(user, new UserInfo(userInfoDto.getName(), userInfoDto.getSurname()));

        return Response.ok().build();
    }

    //Endpoint para la verificacion del mail
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @Path("/emailVerification")
    public Response verifyUser(@Valid TokenDto tokenDto) {
        LOGGER.info("Called /users/emailVerification PUT");

        if (tokenDto == null) {
            throw new ContentExpectedException();
        }

        final UserModel user = userService.verifyAccount(tokenDto.getToken()).orElseThrow(UserNotFoundException::new);

        //TODO: maybe return user
        return Response.ok().build();
    }

    //Endpoint para la verificacion del mail
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @Path("/emailVerification")
    public Response resendUserVerification() {
        LOGGER.info("Called /users/emailVerification POST");

        final UserModel user = authFacade.getCurrentUser();

        userService.resendVerificationToken(user);

        return Response.noContent().build();
    }

    //Endpoint para resetear la contraseña
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/passwordReset")
    public Response sendResetPasswordEmail(@Valid final PasswordResetEmailDto passwordResetEmailDto) {
        LOGGER.info("Called /users/passwordReset POST");

        if (passwordResetEmailDto == null) {
            throw new ContentExpectedException();
        }

        //TODO: check
        LOGGER.info(passwordResetEmailDto.getEmail());
        UserModel user = userService.getUserByEmail(passwordResetEmailDto.getEmail()).orElseThrow(UserNotFoundException::new);
        userService.generateNewPassword(user);

        return Response.noContent().build();
    }

    //Endpoint para resetear la contraseña
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/passwordReset")
    public Response resetPassword(@Valid final PasswordResetDto passwordResetDto) {
        LOGGER.info("Called /users/passwordReset PUT");

        if (passwordResetDto == null) {
            throw new ContentExpectedException();
        }

        userService.updatePassword(passwordResetDto.getToken(), passwordResetDto.getPassword()).orElseThrow(UserNotFoundException::new);

        return Response.noContent().build();
    }

    //Endpoint para obtener la imagen de perfil del usuario
    @GET
    @Path("/{userId}/profileImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getUserProfileImage(@PathParam("userId") final long id, @Context Request request) {
        LOGGER.info("Called /users/{}/profileImage GET", id);

        final UserModel user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);
        final ImageModel img = user.getProfileImage();

        if (img == null) {
//            return Response.status(NOT_FOUND).build();
            return Response.noContent().build();
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

    //Endpoint para editar la imagen de perfil del usuario
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/profileImage")
    public Response putUserProfileImage(
            @PathParam("userId") long id,
            @FormDataParam("profileImage") final FormDataBodyPart profileImageBody,
            @Size(max = 1024 * 1024) @FormDataParam("profileImage") byte[] profileImageBytes) {

        if (profileImageBody == null) {
            throw new ContentExpectedException();
        }

        if (!profileImageBody.getMediaType().toString().contains(ACCEPTED_MIME_TYPES)) {
            throw new IllegalContentTypeException();
        }

        final UserModel user = authFacade.getCurrentUser();

        imageService.updateImg(profileImageBytes, profileImageBody.getMediaType().toString(), user.getProfileImage());

        return Response.noContent()
                .contentLocation(UserDto.getUserUriBuilder(user, uriInfo).path("profileImage").build())
                .build();
    }

    //Endpoint para obtener las experiencias creadas por un usuario
    @GET
    @Path("/{userId}/experiences")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserExperiences(
            @PathParam("userId") final long id,
            @QueryParam("name") @DefaultValue("") String name,
            @QueryParam("order") @DefaultValue("OrderByAZ") OrderByModel order,
            @QueryParam("page") @DefaultValue("1") int page) {
        LOGGER.info("Called /users/{}/experiences GET", id);

        final UserModel user = authFacade.getCurrentUser();

        final Page<ExperienceModel> experiences = experienceService.listExperiencesSearchByUser(name, user, Optional.of(order), page);

        if (experiences == null) {
            return Response.status(BAD_REQUEST).build();
        }

        if(experiences.getContent().isEmpty()) {
            return Response.noContent().build();
        }

        final Collection<ExperienceDto> experienceDtos = ExperienceDto.mapExperienceToDto(experiences.getContent(), uriInfo);

        final UriBuilder uriBuilder = uriInfo
                .getAbsolutePathBuilder()
                .queryParam("order", order)
                .queryParam("page", page)
                .queryParam("name", name);

        return PaginationResponse.createPaginationResponse(experiences, new GenericEntity<Collection<ExperienceDto>>(experienceDtos) {
        }, uriBuilder);
    }

    //Endpoint para obtener las reseñas creadas por un usuario
    @GET
    @Path("/{userId}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserReviews(
            @PathParam("userId") final long id,
            @QueryParam("page") @DefaultValue("1") int page) {
        LOGGER.info("Called /users/{}/experiences GET", id);

        final UserModel user = authFacade.getCurrentUser();

        final Page<ReviewModel> reviews = reviewService.getReviewsByUser(user, page);

        if (reviews == null) {
            return Response.status(BAD_REQUEST).build();
        }

        if(reviews.getContent().isEmpty()) {
            return Response.noContent().build();
        }

        final Collection<ReviewDto> reviewDtos = ReviewDto.mapReviewToDto(reviews.getContent(), uriInfo);

        final UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder()
                .queryParam("page", page);

        return PaginationResponse.createPaginationResponse(reviews, new GenericEntity<Collection<ReviewDto>>(reviewDtos) {
        }, uriBuilder);
    }

    @GET
    @Path("/{userId}/favExperiences")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserFavExperiences(
            @PathParam("userId") final long id,
            @QueryParam("order") @DefaultValue("OrderByAZ") OrderByModel order,
            @QueryParam("page") @DefaultValue("1") int page) {
        LOGGER.info("Called /users/{}/favExperiences GET", id);

        final UserModel user = authFacade.getCurrentUser();

        final Page<ExperienceModel> favExperiences = experienceService.listExperiencesFavsByUser(user, Optional.of(order), page);

        if (favExperiences == null) {
            return Response.status(BAD_REQUEST).build();
        }

        if(favExperiences.getContent().isEmpty()) {
            return Response.noContent().build();
        }

        final Collection<ExperienceDto> experienceDtos = ExperienceDto.mapExperienceToDto(favExperiences.getContent(), uriInfo);

        final UriBuilder uriBuilder = uriInfo
                .getAbsolutePathBuilder()
                .queryParam("order", order)
                .queryParam("page", page);

        return PaginationResponse.createPaginationResponse(favExperiences, new GenericEntity<Collection<ExperienceDto>>(experienceDtos) {
        }, uriBuilder);
    }


}
