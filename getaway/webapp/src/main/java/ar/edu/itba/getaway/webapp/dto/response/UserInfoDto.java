//package ar.edu.itba.getaway.webapp.dto.response;
//
//import ar.edu.itba.getaway.models.UserModel;
//
//import javax.ws.rs.core.UriBuilder;
//import javax.ws.rs.core.UriInfo;
//import java.io.Serializable;
//import java.net.URI;
//
//public class UserInfoDto implements Serializable {
//
//    private long id;
//    private String name;
//    private String surname;
//    private boolean hasImage;
//    private URI profileImageUrl;
//
//    public UserInfoDto() {
//        // Used by Jersey
//    }
//
//    public static UriBuilder getUserUriBuilder(UserModel user, UriInfo uriInfo) {
//        return uriInfo.getBaseUriBuilder().clone().path("users").path(String.valueOf(user.getUserId()));
//    }
//
//
//    public UserInfoDto(UserModel user, UriInfo uriInfo) {
//        final UriBuilder uriBuilder = getUserUriBuilder(user, uriInfo);
//        this.id = user.getUserId();
//        this.name = user.getName();
//        this.surname = user.getSurname();
//        this.hasImage = user.getImage() != null;
//        if (user.getProfileImage() != null) {
//            this.profileImageUrl = uriBuilder.clone().path("profileImage").build();  // /user/{id}/profileImage
//        }
//    }
//
//    public long getId() {
//        return id;
//    }
//    public void setId(long id) {
//        this.id = id;
//    }
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//    public String getSurname() {
//        return surname;
//    }
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
//    public boolean isHasImage() {
//        return hasImage;
//    }
//    public void setHasImage(boolean hasImage) {
//        this.hasImage = hasImage;
//    }
//    public URI getProfileImageUrl() {
//        return profileImageUrl;
//    }
//    public void setProfileImageUrl(URI profileImageUrl) {
//        this.profileImageUrl = profileImageUrl;
//    }
//}
