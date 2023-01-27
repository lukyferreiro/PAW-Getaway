package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BadRequestExceptionMapper.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(BadRequestException e) {
        LOGGER.error("Bad request exception mapper");
        return ExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
    }
}
