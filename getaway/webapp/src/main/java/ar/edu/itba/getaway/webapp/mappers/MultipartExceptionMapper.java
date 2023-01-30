package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MultipartExceptionMapper implements ExceptionMapper<MultipartException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultipartExceptionMapper.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(MultipartException e) {
        LOGGER.error("Multipart exception mapper");
        return ExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
    }
}
