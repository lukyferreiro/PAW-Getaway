package ar.edu.itba.getaway.webapp.forms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ResetPasswordForm {

    @NotEmpty
    @Size(min = 8, max = 25)
    @Pattern(regexp = "^[A-Za-z0-9@$!%*#?&_]*$")
    private String password;

    @NotEmpty
    @Size(min = 8, max = 25)
    @Pattern(regexp = "^[A-Za-z0-9@$!%*#?&_]*$")
    private String confirmPassword;

    @NotEmpty
    private String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}