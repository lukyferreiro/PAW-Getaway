//package ar.edu.itba.getaway.webapp.dto.response;
//
//import ar.edu.itba.getaway.models.ExperienceModel;
//
//import javax.ws.rs.core.UriInfo;
//import java.io.Serializable;
//
//public class ExperienceNameDto implements Serializable {
//    private Long id;
//    private String name;
//
//    public ExperienceNameDto() {
//        // Used by Jersey
//    }
//
//    //UriInfo no se pa q se usa
//    public ExperienceNameDto(ExperienceModel experience, UriInfo uriInfo) {
//        this.id = experience.getExperienceId();
//        this.name = experience.getExperienceName();
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//}
