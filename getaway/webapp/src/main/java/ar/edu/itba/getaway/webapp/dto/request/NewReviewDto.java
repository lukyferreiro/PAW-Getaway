package ar.edu.itba.getaway.webapp.dto.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class NewReviewDto {

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    @Length(min = 3, max = 50, message = "Size.reviewForm.title")
    @Pattern(regexp = "^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"$%#&=:¿?!¡/.-])*$",
             message = "Pattern.reviewForm.title")
    private String title;

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    @Length(min = 3, max = 255, message = "Size.reviewForm.description")
    @Pattern(regexp = "^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"$%#&=:;\\n\\s\\t¿?!¡/.-])*$",
            message = "Pattern.reviewForm.description")
    private String description;

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    @Length(min = 1, max = 1, message = "Pattern.reviewForm.score")
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
