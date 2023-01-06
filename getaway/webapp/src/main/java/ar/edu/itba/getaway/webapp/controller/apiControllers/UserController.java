package ar.edu.itba.getaway.webapp.controller.apiControllers;

import ar.edu.itba.getaway.interfaces.exceptions.ContentExpectedException;
import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.interfaces.exceptions.MaxUploadSizeRequestException;
import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.TokensService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.webapp.auth.JwtUtil;
import ar.edu.itba.getaway.webapp.dto.request.*;
import ar.edu.itba.getaway.webapp.dto.response.UserDto;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.io.IOException;
import java.io.InputStream;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/users")
@Component
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokensService tokensService;
    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private int maxRequestSize;

    @Context
    private SecurityContext securityContext;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getUser(@PathParam("id") final long id) {
        LOGGER.info("Called /users/{} GET", id);
        final UserModel user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);
        LOGGER.info("Return user with id {}", id);
        return Response.ok(new UserDto(user, uriInfo)).build();
    }

    //Endpoint para editar la informacion del usuario
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateUser(@Valid UserInfoDto userInfoDto, @PathParam("id") final long id) {
        LOGGER.info("Called /users/ PUT");

        if (userInfoDto == null) {
            throw new ContentExpectedException();
        }

        final UserModel user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        assureUserResourceCorrelation(user, id);
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
        final Response.ResponseBuilder responseBuilder = Response.noContent();

        if (user.isVerified()) {
            addAuthorizationHeader(responseBuilder, user);
            if (securityContext.getUserPrincipal() == null) {
                addSessionRefreshTokenHeader(responseBuilder, user);
            }
        }
        return responseBuilder.build();
    }

    //Endpoint para la verificacion del mail
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @Path("/emailVerification")
    public Response resendUserVerification() {
        LOGGER.info("Called /users/emailVerification POST");

        final UserModel user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
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

        final UserModel user = userService.getUserByEmail(passwordResetEmailDto.getEmail()).orElseThrow(UserNotFoundException::new);
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
    @Path("/{id}/profileImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getUserProfileImage(@PathParam("id") final long id, @Context Request request) {
        LOGGER.info("Called /users/{}/profileImage GET", id);

        final UserModel user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);
        final ImageModel img = user.getProfileImage();

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

    //Endpoint para editar la imagen de perfil del usuario
    @PUT
    @Path("/{id}/profileImage")
    public Response updateUserProfileImage(@Context final HttpServletRequest request,
                                           @NotNull(message = "{NotEmpty.profileImage.image}")
                                           //TODO poner un mensaje
                                           @FormDataParam("profileImage") FormDataBodyPart profileImage,
                                           @PathParam("id") final long id) throws IOException {

        if (request.getContentLength() == -1 || request.getContentLength() > maxRequestSize) {
            throw new MaxUploadSizeRequestException();
        }

        final UserModel user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        assureUserResourceCorrelation(user, id);

        LOGGER.info("Called /users/{}/profileImage PUT", id);

        InputStream in = profileImage.getEntityAs(InputStream.class);
        userService.updateProfileImage(new NewImageModel(StreamUtils.copyToByteArray(in), profileImage.getMediaType().toString()), user);

        return Response.ok().build();
    }

    //Endpoint para obtener las experiencias creadas por un usuario
    @GET
    @Path("/{id}/experiences")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserExperiences(@PathParam("id") final long id,@Valid final UserDto registerDto) {
        LOGGER.info("Called /{}/experiences GET", id);
       // final Page<ExperienceModel> experienceModel = experienceService.listExperiencesListByUser()
        return null;
    }

    //TODO ver si hace falta un endpoint para obtener los favs del usuario
    //TODO no se como pasarle el order como parametro para retornar la lista de experiencias, el resto los tengo mas
    // o menos pensado


    private void assureUserResourceCorrelation(UserModel user, long userId) {
        if (user.getUserId() != userId) {
            throw new ForbiddenException();
        }
    }

    private void addAuthorizationHeader(final Response.ResponseBuilder response, final UserModel user) {
        response.header(JwtUtil.JWT_HEADER, jwtUtil.generateToken(user, uriInfo.getBaseUri().toString()));
    }

    private void addSessionRefreshTokenHeader(final Response.ResponseBuilder response, final UserModel user) {
        response.header(JwtUtil.REFRESH_TOKEN_HEADER, tokensService.getSessionRefreshToken(user).getValue());
    }

}
