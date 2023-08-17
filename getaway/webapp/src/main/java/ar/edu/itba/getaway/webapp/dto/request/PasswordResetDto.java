package ar.edu.itba.getaway.webapp.dto.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PasswordResetDto {

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    @Length(min = 8, max = 25,  message = "Size.registerForm.password")
    @Pattern(regexp = "^[A-Za-z0-9@$!%*#?&_]*$", message = "Pattern.registerForm.password")
    private String password;

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    private String token;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
