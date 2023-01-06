package ar.edu.itba.getaway.webapp.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BadRequestExceptionMapper.class);

    @Override
    public Response toResponse(BadRequestException exception) {
        LOGGER.error("Bad request exception mapper");
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}
