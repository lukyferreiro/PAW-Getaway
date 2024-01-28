package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import ar.edu.itba.getaway.webapp.security.api.CustomMediaType;
import org.glassfish.jersey.media.multipart.FormDataParamException;
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
public class FormDataParamExceptionMapper implements ExceptionMapper<FormDataParamException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormDataParamExceptionMapper.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(FormDataParamException e) {
        LOGGER.error("Form data param exception mapper");
        return ExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
    }
}
