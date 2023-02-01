package ar.edu.itba.getaway.webapp.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserInfoDto {
    @NotEmpty(message = "NotEmpty")
    @Size(max = 50, message = "Size.registerForm.name")
    @Pattern(regexp = "^[A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð' ]*$",
            message = "Pattern.registerForm.name")
    private String name;

    @NotEmpty(message = "NotEmpty")
    @Size(max = 50, message = "Size.registerForm.surname")
    @Pattern(regexp = "^[A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð' ]*$",
            message = "Pattern.registerForm.surname")
    private String surname;


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
