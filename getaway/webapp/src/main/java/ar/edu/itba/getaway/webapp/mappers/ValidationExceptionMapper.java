package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.dto.ValidationError;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ValidationError.fromValidationException(e)).build();

    }

}
