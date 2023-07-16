package ar.edu.itba.getaway.webapp.dto.response;

import org.springframework.context.MessageSource;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExceptionRequestDto implements Serializable {

    private String title;
    private Integer status;
    private String message;
    private List<ValidationErrorDto> errors;

    public ExceptionRequestDto() {
        // Used by Jersey
    }

    public ExceptionRequestDto(final String message) {
        this.setMessage(message);
    }

    public ExceptionRequestDto(final String message, final Set<? extends ConstraintViolation<?>> constraintViolations, String title, Integer status, MessageSource messageSource) {
        this.setMessage(message);
        errors = new ArrayList<>(constraintViolations.size());
        this.title = title;
        this.status = status;
        constraintViolations.forEach((constraintViolation) -> {
            if (!constraintViolation.getPropertyPath().toString().isEmpty())
                errors.add(new ValidationErrorDto(constraintViolation, messageSource));
        });
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public List<ValidationErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(final List<ValidationErrorDto> errors) {
        this.errors = errors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}