package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CityNotFoundExceptionMapper implements ExceptionMapper<CityNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityNotFoundExceptionMapper.class);

    @Autowired
    private MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(CityNotFoundException e) {
        LOGGER.error("City not found exception mapper");
        String message = ExceptionMapperUtil.getLocalizedMessage(e.getMessage(), messageSource);
        return ExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, message, uriInfo);
    }

}
