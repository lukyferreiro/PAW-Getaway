package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAcceptableExceptionMapper implements ExceptionMapper<NotAcceptableException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotAcceptableExceptionMapper.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(NotAcceptableException e) {
        LOGGER.error("Not acceptable exception mapper");
        return ExceptionMapperUtil.toResponse(Response.Status.NOT_ACCEPTABLE, e.getMessage(), uriInfo);
    }
}
