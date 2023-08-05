package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import ar.edu.itba.getaway.webapp.security.api.CustomMediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(value = { CustomMediaType.ERROR_V1 })
public class ExperienceNotFoundExceptionMapper implements ExceptionMapper<ExperienceNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceNotFoundExceptionMapper.class);

    @Autowired
    private MessageSource messageSource;
    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ExperienceNotFoundException e) {
        LOGGER.error("Experience not found exception mapper");
        String message = ExceptionMapperUtil.getLocalizedMessage(e.getMessage(), messageSource);
        return ExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, message, uriInfo);
    }
}
