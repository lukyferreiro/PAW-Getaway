package ar.edu.itba.getaway.webapp.forms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ReviewForm {

    @NotEmpty
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"$%#&=:¿?!¡/.-])*$")
    private String title;

    @NotEmpty
    @Size(min = 3, max = 255)
    @Pattern(regexp = "^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"$%#&=:;\\n\\s\\t¿?!¡/.-])*$")
    private String description;

    @NotEmpty
    @Pattern(regexp = "^([1-5])$")
    private String score;

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getScore() {
        return score;
    }
    public long getLongScore(){
        return Long.parseLong(score);
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setScore(String score) {
        this.score = score;
    }
}
