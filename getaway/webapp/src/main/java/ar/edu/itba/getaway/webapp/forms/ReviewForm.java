package ar.edu.itba.getaway.webapp.forms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ReviewForm {
    @NotEmpty
    @Size(min = 3, max = 50)
    String title;

    @NotEmpty
    @Size(min = 3, max = 255)
    String description;

    @NotEmpty
    @Pattern(regexp = "^([0-5])$")
    long score;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getScore() {
        return score;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
