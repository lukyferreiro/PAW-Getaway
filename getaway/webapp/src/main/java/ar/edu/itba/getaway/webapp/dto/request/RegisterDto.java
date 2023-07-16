package ar.edu.itba.getaway.webapp.dto.request;

import ar.edu.itba.getaway.webapp.constraints.EmailNotUsed;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RegisterDto {
    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    @Length(max = 50, message = "Size.registerForm.name")
    @Pattern(regexp = "^[A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð' ]*$",
             message = "Pattern.registerForm.name")
    private String name;

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    @Length(max = 50, message = "Size.registerForm.surname")
    @Pattern(regexp = "^[A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð' ]*$",
            message = "Pattern.registerForm.surname")
    private String surname;

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    @Length(max = 255, message = "Size.registerForm.email")
    @Pattern(regexp = "^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$", message = "Pattern.registerForm.email")
    @EmailNotUsed
    private String email;

    //https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    @Length(min = 8, max = 25, message = "Size.registerForm.password")
    @Pattern(regexp = "^[A-Za-z0-9@$!%*#?&_]*$", message = "Pattern.registerForm.password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
