package ar.edu.itba.getaway.webapp.security.models;

import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.models.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUserDetails extends User {

    //We consider the email as username
    private Long userId;
    private String name;
    private String surname;
    private ImageModel image;
    private boolean isVerified;
    private boolean isProvider;

    public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                         Long userId, String name, String surname, boolean isVerified, boolean isProvider, ImageModel image) {
        super(username, password, authorities);
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.isVerified = isVerified;
        this.isProvider = isProvider;
        this.image = image;
    }

    public UserModel toUserModel() {
        // Remember that this.getUsername() = email
        return new UserModel(userId, name, surname, this.getUsername(), image, isVerified, isProvider);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public ImageModel getImage() {
        return image;
    }

    public void setImage(ImageModel image) {
        this.image = image;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean isProvider() {
        return isProvider;
    }

    public void setProvider(boolean provider) {
        isProvider = provider;
    }

}
