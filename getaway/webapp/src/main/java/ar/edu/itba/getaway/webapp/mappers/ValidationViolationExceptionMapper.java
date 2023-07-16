package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.dto.response.ExceptionRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationViolationExceptionMapper.class);

    @Autowired
    private MessageSource messageSource;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        LOGGER.error("Constraint violation exception mapper");
        Response.Status status = Response.Status.BAD_REQUEST;
        return Response.status(status)
                .entity(new ExceptionRequestDto(
                        exception.getMessage(), exception.getConstraintViolations(),
                        status.getReasonPhrase(), status.getStatusCode(), messageSource))
                .type(MediaType.APPLICATION_JSON).build();
    }
}
