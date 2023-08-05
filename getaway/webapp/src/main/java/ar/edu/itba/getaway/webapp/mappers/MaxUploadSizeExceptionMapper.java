package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.interfaces.exceptions.MaxUploadSizeRequestException;
import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import ar.edu.itba.getaway.webapp.security.api.CustomMediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

@Singleton
@Produces(value = { CustomMediaType.ERROR_V1 })
public class MaxUploadSizeExceptionMapper implements ExceptionMapper<MaxUploadSizeRequestException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaxUploadSizeExceptionMapper.class);

    @Autowired
    private MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(MaxUploadSizeRequestException e) {
        LOGGER.error("Max upload size exception mapper)");
        String message = ExceptionMapperUtil.getLocalizedMessage(e.getMessage(), messageSource);
        return ExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, message, uriInfo);
    }
}
