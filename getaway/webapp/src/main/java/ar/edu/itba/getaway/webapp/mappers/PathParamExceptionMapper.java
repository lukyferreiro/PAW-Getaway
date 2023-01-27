package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import org.glassfish.jersey.server.ParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PathParamExceptionMapper implements ExceptionMapper<ParamException.PathParamException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParamException.PathParamException.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ParamException.PathParamException e) {
        LOGGER.error("Path param exception mapper");
        return ExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, e.getMessage(), uriInfo);
    }
}
