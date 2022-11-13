package ar.edu.itba.getaway.webapp.forms;

import javax.validation.constraints.Size;

public class SearchForm {

    @Size(max = 255)
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}