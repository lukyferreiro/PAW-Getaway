package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.interfaces.exceptions.IllegalOperationException;
import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class IllegalOperationExceptionMapper implements ExceptionMapper<IllegalOperationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IllegalOperationException.class);

    @Autowired
    private MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(IllegalOperationException e) {
        LOGGER.error("Illegal operation exception mapper");
        final String message = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());
        return ExceptionMapperUtil.toResponse(Response.Status.UNAUTHORIZED, message, uriInfo);
    }
}
