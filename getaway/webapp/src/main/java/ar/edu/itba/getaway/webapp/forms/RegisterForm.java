package ar.edu.itba.getaway.webapp.forms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterForm {

    @NotEmpty
    @Pattern(regexp = "^[A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð' ]*$")
    private String name;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð' ]*$")
    private String surname;
    
//    @NotEmpty
//    @Size(min = 4, max = 20)
//    //https://stackoverflow.com/questions/12018245/regular-expression-to-validate-username
//    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9._]*[a-zA-Z0-9]+$")
//    private String username;

    //https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
    @NotEmpty
    @Size(min = 8, max = 25)
    @Pattern(regexp = "^[A-Za-z0-9@$!%*#?&_]*$")
    private String password;

    @NotEmpty
    @Size(min = 8, max = 25)
    @Pattern(regexp = "^[A-Za-z0-9@$!%*#?&_]*$")
    private String confirmPassword;

    @NotEmpty
    @Pattern(regexp = "^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$")
    private String email;

//    public String getUsername() {
//        return username;
//    }
    public String getPassword() {
        return password;
    }
//    public void setUsername(String username) {
//        this.username = username;
//    }
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
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
}
