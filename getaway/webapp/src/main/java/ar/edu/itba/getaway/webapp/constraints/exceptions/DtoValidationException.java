package ar.edu.itba.getaway.webapp.constraints.exceptions;

import javax.validation.ConstraintViolation;
import java.util.Set;

@SuppressWarnings("serial")
public class DtoValidationException extends RuntimeException {

    private final Set<? extends ConstraintViolation<?>> constraintViolations;

    public DtoValidationException(final String message, final Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(message);
        this.constraintViolations = constraintViolations;
    }

    public Set<? extends ConstraintViolation<?>> getConstraintViolations() {
        return constraintViolations;
    }

}
