package ar.edu.itba.getaway.webapp.dto.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PasswordResetDto {

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotEmpty")
    @Length(min = 8, max = 25)
    @Pattern(regexp = "^[A-Za-z0-9@$!%*#?&_]*$")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
