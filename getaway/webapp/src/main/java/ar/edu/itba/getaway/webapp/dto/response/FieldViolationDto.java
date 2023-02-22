package ar.edu.itba.getaway.webapp.dto.response;

import javax.validation.ConstraintViolation;
import java.io.Serializable;

public class FieldViolationDto implements Serializable {

    private String field;
    private String violation;

    public FieldViolationDto() {
        // Used by Jersey
    }

    public FieldViolationDto(final ConstraintViolation<?> constraintViolation) {
        this.setField(constraintViolation.getPropertyPath().toString());
        this.setViolation(constraintViolation.getMessage());
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

}
