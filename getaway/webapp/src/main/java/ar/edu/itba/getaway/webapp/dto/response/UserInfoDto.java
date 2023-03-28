package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.UserModel;

import java.io.Serializable;

public class UserInfoDto implements Serializable {

    private long id;
    private String name;
    private String surname;
    private boolean hasImage;

    public UserInfoDto() {
        // Used by Jersey
    }

    public UserInfoDto(UserModel user) {
        this.id = user.getUserId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.hasImage = user.getImage() != null;
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
    public boolean isHasImage() {
        return hasImage;
    }
    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

}
