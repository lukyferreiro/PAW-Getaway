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
public class QueryParamExceptionMapper implements ExceptionMapper<ParamException.QueryParamException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParamException.QueryParamException.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ParamException.QueryParamException e) {
        LOGGER.error("Query param exception mapper");
        return ExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, "Malformed query parameter", uriInfo);
    }
}