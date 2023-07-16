package ar.edu.itba.getaway.webapp.dto.response;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Locale;

public class ValidationErrorDto implements Serializable {

    private String field;
    private String violation;

    public ValidationErrorDto() {
        // Used by Jersey
    }

    public ValidationErrorDto(final ConstraintViolation<?> constraintViolation, MessageSource messageSource) {
        this.setField(constraintViolation.getPropertyPath().toString());
        Locale locale = LocaleContextHolder.getLocale();
        this.setViolation(messageSource.getMessage(constraintViolation.getMessage(), null, locale));
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
