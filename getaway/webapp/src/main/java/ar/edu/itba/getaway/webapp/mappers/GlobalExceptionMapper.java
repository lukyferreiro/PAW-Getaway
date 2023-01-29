package ar.edu.itba.getaway.webapp.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper extends Throwable implements ExceptionMapper<Throwable> {

    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionMapper.class);
    @Override
    public Response toResponse(Throwable exception) {
        LOGGER.error("Server error exception mapper");

        final int status;
        final String message;

        if (exception instanceof WebApplicationException) {
            status = ((WebApplicationException) exception).getResponse().getStatus();
            message = exception.getMessage();
        } else {
            status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
            message = messageSource.getMessage("errors.ServerError", null, LocaleContextHolder.getLocale());
        }

        return Response.status(status).entity(message).build();
    }

}
