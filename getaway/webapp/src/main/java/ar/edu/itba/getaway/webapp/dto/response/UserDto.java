package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.UserModel;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserDto {

    private long id;
    private String name;
    private String surname;
    private String email;
    private Integer favsCount;
    private String selfUrl;
    private String profileImageUrl;

    private String experiencesUrl;

    public static Collection<UserDto> mapUserToDto(Collection<UserModel> users, UriInfo uriInfo) {
        return users.stream().map(u -> new UserDto(u, uriInfo)).collect(Collectors.toList());
    }

    public static UriBuilder getUserUriBuilder(UserModel user, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone().path("users").path(String.valueOf(user.getUserId()));
    }

    public UserDto() {
        // Used by Jersey
    }

    public UserDto(UserModel user, UriInfo uriInfo) {
        final UriBuilder uriBuilder = getUserUriBuilder(user, uriInfo);

        this.id = user.getUserId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.favsCount = user.getFavExperiences().size();
        this.selfUrl = uriBuilder.clone().build().toString();
        if (user.getProfileImage() != null) {
            this.profileImageUrl = uriBuilder.clone().path("profileImage").build().toString();
        } else {
            this.profileImageUrl = "";
        }
        this.experiencesUrl = uriBuilder.clone().path("experiences").build().toString();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getFavsCount() {
        return favsCount;
    }
    public void setFavsCount(Integer favsCount) {
        this.favsCount = favsCount;
    }
    public String getSelfUrl() {
        return selfUrl;
    }
    public void setSelfUrl(String selfUrl) {
        this.selfUrl = selfUrl;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    public String getExperiencesUrl() {
        return experiencesUrl;
    }
    public void setExperiencesUrl(String experiencesUrl) {
        this.experiencesUrl = experiencesUrl;
    }
}


