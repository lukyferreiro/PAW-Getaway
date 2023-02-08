package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.models.ExperienceModel;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ExperienceNameDto {
    private Long id;
    private String name;

    //TODO: check if uri can be returned and used in frontend navigate

    public static Collection<ExperienceNameDto> mapExperienceToDto(Collection<ExperienceModel> experiences, UriInfo uriInfo) {
        return experiences.stream().map(exp -> new ExperienceNameDto(exp, uriInfo)).collect(Collectors.toList());
    }

    public static UriBuilder getExperienceUriBuilder(ExperienceModel experience, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone().path("experiences/experience").path(String.valueOf(experience.getExperienceId()));
    }

    public ExperienceNameDto() {
        // Used by Jersey
    }

    //UriInfo no se pa q se usa
    public ExperienceNameDto(ExperienceModel experience, UriInfo uriInfo) {
        final UriBuilder uriBuilder = getExperienceUriBuilder(experience, uriInfo);
        this.id = experience.getExperienceId();
        this.name = experience.getExperienceName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
