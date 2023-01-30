package ar.edu.itba.getaway.webapp.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewReviewDto {
    @NotEmpty(message = "NotEmpty")
    @Size(min = 3, max = 50, message = "Size.reviewForm.title")
    @Pattern(regexp = "^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"$%#&=:¿?!¡/.-])*$",
             message = "Pattern.reviewForm.title")
    private String title;

    @NotEmpty(message = "NotEmpty")
    @Size(min = 3, max = 255, message = "Size.reviewForm.description")
    @Pattern(regexp = "^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"$%#&=:;\\n\\s\\t¿?!¡/.-])*$",
            message = "Pattern.reviewForm.description")
    private String description;

    @NotEmpty(message = "NotEmpty")
    @Pattern(regexp = "^([1-5])$", message = "Pattern.reviewForm.score")
    private String score;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getScore() {
        return score;
    }
    public long getLongScore() {
        return Long.parseLong(score);
    }
    public void setScore(String score) {
        this.score = score;
    }

}
