package ar.edu.itba.getaway.webapp.dto.response;

public class ValidationErrorDto {

    private String message;

    public ValidationErrorDto() {
        // Jersey, do not use
    }

    public ValidationErrorDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
