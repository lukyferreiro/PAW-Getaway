package ar.edu.itba.getaway.webapp.controller.apiControllers;

import ar.edu.itba.getaway.interfaces.exceptions.ContentExpectedException;
import ar.edu.itba.getaway.interfaces.exceptions.DuplicateExperienceException;
import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.UserInfo;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.dto.response.ExperienceDto;
import ar.edu.itba.getaway.webapp.dto.response.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;

@Path("/experiences")
@Component
public class ExperienceController {
    @Autowired
    private ExperienceService experienceService;

    @Context
    UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceController.class);
    @GET
    @Path("/")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperiences() {
        LOGGER.info("Called /experiences GET");

        Collection<ExperienceModel> experiences = experienceService.getExperiences();
        Collection<ExperienceDto> experienceDto = ExperienceDto.mapExperienceToDto(experiences, uriInfo);

        return Response.ok(new GenericEntity<Collection<ExperienceDto>>(experienceDto) {}).build();
    }

//    Ejemplo obtener las experiencias de aventura/hoteleria/etc
    @GET
    @Path("/{category}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperiencesFromCategory(@PathParam("category") final String category) {}

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getExperienceId(@PathParam("id") final long id) {}

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response registerExperience(@Valid final ExperienceDto experienceDto) throws DuplicateExperienceException {
        LOGGER.info("Called /experiences/ POST");
        if (experienceDto == null) {
            throw new ContentExpectedException();
        }
        ExperienceModel experience;
        try {
            experience = experienceService.createExperience(experienceDto.getName(), experienceDto.getAddress(), experienceDto.getDescription(), experienceDto.getContact_email(), experienceDto.getUrl(), experienceDto.getPrice(), experienceDto.getCityModel(), experienceDto.getCategoryModel(), experienceDto.getUserModel(), experienceDto.getImage());
        } catch (DuplicateExperienceException e) {
            LOGGER.warn("Error in experienceDto ExperienceForm, there is already an experience with this id");
            throw new DuplicateExperienceException();
        }

        return Response.created(ExperienceDto.getExperienceUriBuilder(experience, uriInfo).build()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateExperience(@Valid ExperienceDto experienceDto, @PathParam("id") final long id){
        LOGGER.info("Called /experiences/{} PUT", id);

        if (experienceDto == null) {
            throw new ContentExpectedException();
        }

        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(UserNotFoundException::new);
//        experienceService.updateExperience(experience, experience.getImage()); ????

        return Response.ok().build();
    }
}
