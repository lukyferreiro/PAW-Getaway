package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotFoundExceptionMapper.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(NotFoundException e) {
        LOGGER.error("Not found exception mapper");
        return ExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, e.getMessage(), uriInfo);
    }
}
