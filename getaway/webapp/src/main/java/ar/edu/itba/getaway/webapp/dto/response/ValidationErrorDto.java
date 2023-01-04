package ar.edu.itba.getaway.webapp.dto.response;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class ValidationErrorDto {

    private String message;

    public static ValidationErrorDto fromValidationException(final ConstraintViolationException vex){
        final ValidationErrorDto dto = new ValidationErrorDto();
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
