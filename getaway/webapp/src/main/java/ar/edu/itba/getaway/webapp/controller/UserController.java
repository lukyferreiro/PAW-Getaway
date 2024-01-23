package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.*;
import ar.edu.itba.getaway.interfaces.services.*;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.webapp.controller.util.CacheResponse;
import ar.edu.itba.getaway.webapp.dto.request.*;
import ar.edu.itba.getaway.webapp.dto.request.UserInfoDto;
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

import java.net.URI;

@Path("users")
@Component
public class UserController {

    @Context
    private UriInfo uriInfo;
    private final UserService userService;
    private final ImageService imageService;
    private final FavAndViewExperienceService favAndViewExperienceService;
    private final AuthContext authContext;
    private final Integer maxRequestSize;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final String ACCEPTED_MIME_TYPES = "image/";

    @Autowired
    public UserController(UserService userService, ImageService imageService, FavAndViewExperienceService favAndViewExperienceService, AuthContext authContext, Integer maxRequestSize) {
        this.userService = userService;
        this.imageService = imageService;
        this.favAndViewExperienceService = favAndViewExperienceService;
        this.authContext = authContext;
        this.maxRequestSize = maxRequestSize;
    }

    //Endpoint que crea un usuario nuevo
    @POST
    @Consumes(value = {CustomMediaType.USER_V1})
    public Response registerUser(
            @Valid final RegisterDto registerDto
    ) throws DuplicateUserException {
        LOGGER.info("Called /users POST");

        if (registerDto == null) {
            throw new ContentExpectedException();
        }

        final UserModel user = userService.createUser(registerDto.getPassword(), registerDto.getName(), registerDto.getSurname(), registerDto.getEmail());
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getUserId())).build();
        return Response.created(location).build();
    }

    //Endpoint que devuelve informacion de un usuario segun el ID
    @GET
    @Path("/{userId:[0-9]+}")
    @Produces(value = {CustomMediaType.USER_V1})
    public Response getUserById(
            @PathParam("userId") final long userId
    ) {
        LOGGER.info("Called /users/{} GET", userId);
        final UserModel user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
        if (user.equals(authContext.getCurrentUser())) {
            return Response.ok(new UserDto(user, uriInfo)).build();
        } else {
            return Response.ok(new PublicUserDto(user, uriInfo)).build();
        }
    }

    //Endpoint para editar la informacion del usuario
    @PUT
    @Path("/{userId:[0-9]+}")
    @Consumes(value = {CustomMediaType.USER_INFO_V1})
    public Response updateUser(
            @PathParam("userId") final long userId,
            @Valid final UserInfoDto userInfoDto
    ) {
        LOGGER.info("Called /users/{} PUT", userId);

        if (userInfoDto == null) {
            throw new ContentExpectedException();
        }

        userService.updateUserInfo(userId, new UserInfo(userInfoDto.getName(), userInfoDto.getSurname()));
        return Response.noContent().build();
    }

    //Endpoint para cambiar la contraseña, recibiendo el token y la contraseña nueva
    @PATCH
    @Consumes(CustomMediaType.USER_PASSWORD_V1)
    public Response patchModifyPassword(
            @Valid final PatchPasswordDto patchPasswordDto,
            @QueryParam("token") @DefaultValue("") String token
    ) {
        LOGGER.info("Called /users PATCH");

        if (patchPasswordDto == null) {
            throw new ContentExpectedException();
        }

        userService.updatePassword(token, patchPasswordDto.getPassword()).orElseThrow(UserNotFoundException::new);
        return Response.noContent().build();
    }

    //Endpoint para blanquear la contraseña y enviar el mail de recupero
    @POST
    @Consumes(CustomMediaType.USER_PASSWORD_EMAIL_V1)
    public Response sendResetPasswordEmail(
            @Valid final PasswordResetEmailDto passwordResetEmailDto
    ) {
        LOGGER.info("Called /users POST");

        if (passwordResetEmailDto == null) {
            throw new ContentExpectedException();
        }
        userService.generateNewPassword(passwordResetEmailDto.getEmail());
        return Response.noContent().build();
    }

    //Endpoint para obtener la imagen de perfil del usuario
    @GET
    @Path("/{userId:[0-9]+}/profileImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})   //TODO check @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response getUserProfileImage(
            @PathParam("userId") final long userId,
            @Context final Request request
    ) {
        LOGGER.info("Called /users/{}/profileImage GET", userId);

        final UserModel user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);

        if (user.getImage() == null) {
            return Response.noContent().build();
        }

        final ImageModel image = user.getProfileImage();
        return CacheResponse.cacheResponse(image, request);
    }

    //Endpoint para editar la imagen de perfil del usuario
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/{userId:[0-9]+}/profileImage")
    public Response updateUserProfileImage(
            @PathParam("userId") final long userId,
            @FormDataParam("profileImage") final FormDataBodyPart profileImageBody,
            @Size(max = 1024 * 1024) @FormDataParam("profileImage") byte[] profileImageBytes
    ) {
        LOGGER.info("Called /users/{}/profileImage PUT", userId);

        if (profileImageBody == null) {
            throw new ContentExpectedException();
        }

        if (!profileImageBody.getMediaType().toString().contains(ACCEPTED_MIME_TYPES)) {
            throw new IllegalContentTypeException();
        }

        imageService.updateUserImg(profileImageBytes, profileImageBody.getMediaType().toString(), userId);
        return Response.noContent().build();
    }

    //Endpoint para agregar/quitar de favoritos
    @PUT
    @Path("/{userId:[0-9]+}/favourites/{experienceId:[0-9]+}")
    @Consumes(value = {CustomMediaType.USER_FAVOURITE_V1})
    public Response favExperience(
            @Context final HttpServletRequest request,
            @PathParam("userId") final long userId,
            @PathParam("experienceId") final long experienceId,
            @Valid final PutFavouriteDto putFavouriteDto
    ) {
        LOGGER.info("Called /users/{}/favourites/{} PUT", userId, experienceId);

        if (putFavouriteDto == null) {
            throw new ContentExpectedException();
        }

        favAndViewExperienceService.setFav(userId, putFavouriteDto.getFavourite(), experienceId);
        return Response.noContent().build();
    }


    //Endpoint para verificar si una experiencia es favorita de un usuario
    @GET
    @Path("/{userId:[0-9]+}/favourites/{experienceId:[0-9]+}")
    @Produces(value = {CustomMediaType.USER_FAVOURITE_V1})
    public Response isFavExperience(
            @PathParam("userId") final long userId,
            @PathParam("experienceId") final long experienceId
    ) {
        LOGGER.info("Called /users/{}/favourites/{} GET", userId, experienceId);
        final boolean isFavourite = favAndViewExperienceService.isFav(userId, experienceId);
        return Response.ok(new FavouriteDto(isFavourite, userId, experienceId, uriInfo)).build();
    }

}
