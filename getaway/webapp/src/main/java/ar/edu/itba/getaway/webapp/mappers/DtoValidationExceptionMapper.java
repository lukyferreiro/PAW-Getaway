package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.constraints.exceptions.DtoValidationException;
import ar.edu.itba.getaway.webapp.dto.response.ExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DtoValidationExceptionMapper implements ExceptionMapper<DtoValidationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DtoValidationExceptionMapper.class);

    @Override
    public Response toResponse(final DtoValidationException exception) {
        LOGGER.error("Dto validation exception mapper");
        Response.Status status = Response.Status.CONFLICT;
        return Response.status(status).entity(new ExceptionDto(exception.getMessage(), exception.getConstraintViolations(), status.getReasonPhrase(), status.getStatusCode())).build();
    }

}
