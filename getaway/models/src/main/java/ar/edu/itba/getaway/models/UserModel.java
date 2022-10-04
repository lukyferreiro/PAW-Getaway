package ar.edu.itba.getaway.models;

import java.util.Collection;

public class UserModel {
    private String name, password, surname, email;
    private Long userId, profileImageId;
    private Collection<Roles> roles;

    public UserModel(Long userId, String password, String name, String surname, String email, Collection<Roles> roles, Long profileImageId) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.roles = roles;
        this.profileImageId = profileImageId;
    }

    public Long getProfileImageId() {
        return profileImageId;
    }
    public void setProfileImageId(Long profileImageId) {
        this.profileImageId = profileImageId;
    }
    public boolean hasRole(String role) {
        return roles.stream().anyMatch(p -> p.name().equals(role));
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Collection<Roles> getRoles() {
        return roles;
    }
    public void setRoles(Collection<Roles> roles) {
        this.roles = roles;
    }
    public void addRole(Roles role) {
        roles.add(role);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof UserModel)){
            return false;
        }
        UserModel other = (UserModel) o;
        return this.userId.equals(other.userId) && this.email.equals(other.email);
    }
}
