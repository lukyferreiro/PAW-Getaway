package ar.edu.itba.getaway.webapp.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PasswordResetDto {

    @NotEmpty
    @Size(min = 8, max = 25)
    @Pattern(regexp = "^[A-Za-z0-9@$!%*#?&_]*$")
    private String password;

    @NotEmpty
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
