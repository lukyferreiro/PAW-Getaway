package ar.edu.itba.getaway.webapp.forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SearchForm {

    @Size(min = 0, max = 255)
    @Pattern(regexp = "^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'·#$%&=:¿?!¡/.-]*$")
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}