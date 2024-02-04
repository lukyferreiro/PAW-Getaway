package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import ar.edu.itba.getaway.webapp.security.api.CustomMediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(value = { CustomMediaType.ERROR_V1 })
public class UnsupportedMediaTypeMapper implements ExceptionMapper<NotSupportedException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnsupportedMediaTypeMapper.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(NotSupportedException e) {
        LOGGER.error("Unsupported media type exception mapper");
        return ExceptionMapperUtil.toResponse(Response.Status.UNSUPPORTED_MEDIA_TYPE, e.getMessage(), uriInfo);
    }
}