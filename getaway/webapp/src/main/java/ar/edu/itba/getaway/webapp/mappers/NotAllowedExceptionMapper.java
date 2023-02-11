package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotAllowedExceptionMapper.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(NotAllowedException e) {
        LOGGER.error("Not allowed exception mapper");
        return ExceptionMapperUtil.toResponse(Response.Status.METHOD_NOT_ALLOWED, e.getMessage(), uriInfo);
    }
}