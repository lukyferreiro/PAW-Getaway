package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import ar.edu.itba.getaway.webapp.security.api.CustomMediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(value = { CustomMediaType.ERROR_V1 })
public class NumberFormatExceptionMapper implements ExceptionMapper<NumberFormatException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberFormatExceptionMapper.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(NumberFormatException e) {
        LOGGER.error("Number format exception mapper");
        return ExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
    }
}