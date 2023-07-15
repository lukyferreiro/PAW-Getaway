package ar.edu.itba.getaway.webapp.dto.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PasswordResetEmailDto {

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotEmpty")
    @Length(max = 255)
    @Pattern(regexp = "^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$")
    private String email;

    public PasswordResetEmailDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
