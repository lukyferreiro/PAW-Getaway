package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.ContentExpectedException;
import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.interfaces.exceptions.IllegalContentTypeException;
import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

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
    private final AuthContext authContext;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final String ACCEPTED_MIME_TYPES = "image/";

    @Autowired
    public UserController(UserService userService, ImageService imageService, AuthContext authContext) {
        this.userService = userService;
        this.imageService = imageService;
        this.authContext = authContext;
    }

    //Endpoint que crea un usuario nuevo
    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})     //TODO check
    @Produces(value = {CustomMediaType.USER_V1})
    public Response registerUser(
            @Valid final RegisterDto registerDto
    ) throws DuplicateUserException {
        LOGGER.info("Called /users POST");

        if (registerDto == null) {
            throw new ContentExpectedException();
        }

        UserModel user;
        try {
            user = userService.createUser(registerDto.getPassword(), registerDto.getName(), registerDto.getSurname(), registerDto.getEmail());
        } catch (DuplicateUserException e) {
            LOGGER.warn("Error in registerDto, email is already in used");
            throw new DuplicateUserException();
        }

        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getUserId())).build();
        return Response.created(location).build();
    }

    //Endpoint que devuelve informacion de un usuario segun el ID
    @GET
    @Path("/{userId:[0-9]+}")
    @Produces(value = {CustomMediaType.USER_V1})
    @PreAuthorize("@antMatcherVoter.accessUserInfo(authentication, #userId)")
    public Response getUserById(
            @PathParam("userId") final long id
    ) {
        LOGGER.info("Called /users/{} GET", id);
        final UserModel user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);
        return Response.ok(new UserDto(user, uriInfo)).build();
    }

    //Endpoint para editar la informacion del usuario
    @PUT
    @Path("/{userId:[0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)           //TODO check
    @Produces(value = {CustomMediaType.USER_V1})
    public Response updateUser(
            @PathParam("userId") final long id,
            @Valid final UserInfoDto userInfoDto
    ) {
        LOGGER.info("Called /users/{} PUT", id);

        if (userInfoDto == null) {
            throw new ContentExpectedException();
        }

        final UserModel user = authContext.getCurrentUser();
        userService.updateUserInfo(user, new UserInfo(userInfoDto.getName(), userInfoDto.getSurname()));
        return Response.ok().build();
    }

    //Endpoint para la verificar al usuario cuando recibo el token
    //TODO no se si meterlo en el endpoint anterior
    @PUT
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @Path("/emailToken")
    public Response verifyUser(
            @QueryParam("token") final String token
    ) {
        LOGGER.info("Called /users/emailToken PUT");
        userService.verifyAccount(token).orElseThrow(UserNotFoundException::new);
        return Response.ok().build();
    }

    //Endpoint para reenviar el mail de verificacion
    //TODO cambiar
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @Path("/emailToken")
    public Response resendUserVerification() {
        LOGGER.info("Called /users/emailToken POST");
        final UserModel user = authContext.getCurrentUser();
        userService.resendVerificationToken(user);
        return Response.ok().build();
    }


    //https://stackoverflow.com/questions/3077229/restful-password-reset
    //Endpoint para resetear la contraseña, recibiendo el token y la contraseña nueva
    //TODO cambiar
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/password")
    public Response resetPassword(
            @Valid final PasswordResetDto passwordResetDto
    ) {
        LOGGER.info("Called /users/password PUT");

        if (passwordResetDto == null) {
            throw new ContentExpectedException();
        }

        userService.updatePassword(passwordResetDto.getToken(), passwordResetDto.getPassword()).orElseThrow(UserNotFoundException::new);
        return Response.ok().build();
    }

    //Endpoint para enviar el mail del email que quiere reiniciar la contraseña
    //TODO cambiar
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/password")
    public Response sendResetPasswordEmail(
            @Valid final PasswordResetEmailDto passwordResetEmailDto
    ) {
        LOGGER.info("Called /users/password POST");

        if (passwordResetEmailDto == null) {
            throw new ContentExpectedException();
        }

        UserModel user = userService.getUserByEmail(passwordResetEmailDto.getEmail()).orElseThrow(UserNotFoundException::new);
        userService.generateNewPassword(user);
        return Response.ok().build();
    }

    //Endpoint para obtener la imagen de perfil del usuario
    @GET
    @Path("/{userId:[0-9]+}/profileImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})   //TODO check @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response getUserProfileImage(
            @PathParam("userId") final long id,
            @Context final Request request
    ) {
        LOGGER.info("Called /users/{}/profileImage GET", id);

        final UserModel user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        if(user.getImage() == null) {
            return Response.noContent().build();
        }

        final ImageModel image = user.getProfileImage();
        return CacheResponse.cacheResponse(image, request);
    }

    //Endpoint para editar la imagen de perfil del usuario
    @PUT
    @Produces(MediaType.APPLICATION_JSON)     //TODO check
    //@Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/{userId:[0-9]+}/profileImage")
    public Response updateUserProfileImage(
            @PathParam("userId") final long id,
            @FormDataParam("profileImage") final FormDataBodyPart profileImageBody,
            @Size(max = 1024 * 1024) @FormDataParam("profileImage") byte[] profileImageBytes
    ) {
        LOGGER.info("Called /users/{}/profileImage PUT", id);

        if (profileImageBody == null) {
            throw new ContentExpectedException();
        }

        if (!profileImageBody.getMediaType().toString().contains(ACCEPTED_MIME_TYPES)) {
            throw new IllegalContentTypeException();
        }

        final UserModel user = authContext.getCurrentUser();
        imageService.updateImg(profileImageBytes, profileImageBody.getMediaType().toString(), user.getProfileImage());
        return Response.ok().build();
    }

}
