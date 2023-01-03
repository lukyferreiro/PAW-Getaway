package ar.edu.itba.getaway.webapp.dto;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class ValidationError {

    private String message;

    public static ValidationError fromValidationException(final ConstraintViolationException vex){
        final ValidationError dto = new ValidationError();
        for(ConstraintViolation<?> v : vex.getConstraintViolations()){
            v.getMessage();
        }
        dto.message = vex.getLocalizedMessage();
        return dto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
