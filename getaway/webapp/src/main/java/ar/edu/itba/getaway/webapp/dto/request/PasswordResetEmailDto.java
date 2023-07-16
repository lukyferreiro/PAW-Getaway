package ar.edu.itba.getaway.webapp.dto.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PasswordResetEmailDto {

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    @Length(max = 255,  message = "Size.registerForm.email")
    @Pattern(regexp = "^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$", message = "Pattern.registerForm.email")
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
