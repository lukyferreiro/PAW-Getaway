package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.webapp.dto.response.ValidationErrorDto;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Singleton
@Component
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {

        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ArrayList<ValidationErrorDto> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : violations) {
            errors.add(new ValidationErrorDto(violation.getMessage()));
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new GenericEntity<Collection<ValidationErrorDto>>(errors) {})
                .type(MediaType.APPLICATION_JSON).build();
    }

}