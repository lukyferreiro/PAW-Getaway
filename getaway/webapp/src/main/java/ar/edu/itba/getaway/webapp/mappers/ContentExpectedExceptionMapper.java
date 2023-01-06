package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.interfaces.exceptions.ContentExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class ContentExpectedExceptionMapper implements ExceptionMapper<ContentExpectedException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentExpectedExceptionMapper.class);

    @Autowired
    private MessageSource messageSource;

    public ContentExpectedExceptionMapper() {
    }

    @Override
    public Response toResponse(ContentExpectedException exception) {
        LOGGER.error("Content expected exception mapper");
        final String message = messageSource.getMessage(exception.getMessage(), null, LocaleContextHolder.getLocale());
        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
}
