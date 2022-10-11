package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userId_seq")
    @SequenceGenerator(sequenceName = "users_userId_seq", name = "users_userId_seq", allocationSize = 1)
    @Column(name = "userId")
    private Long userId;
    @Column(name = "userName", length = 50, nullable = false)
    private String name;
    @Column(name = "userSurname", length = 50, nullable = false)
    private String surname;
    @Column(name = "email", length = 255, nullable = false)
    private String email;
    @Column(name = "password", length = 255, nullable = false)
    private String password;
//    @OneToOne
    @JoinColumn(name = "imgId")
    private Long profileImage;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "userRoles", joinColumns = @JoinColumn(name = "userId"))
    @Column(name = "roleId", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<Roles> roles;

    /* default */
    protected UserModel() {
        // Just for Hibernate
    }

    public UserModel(Long userId, String password, String name, String surname, String email, Collection<Roles> roles, Long profileImage) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.roles = roles;
        this.profileImage = profileImage;
    }

    public Long getProfileImage() {
        return profileImage;
    }
    public void setProfileImageId(Long profileImage) {
        this.profileImage = profileImage;
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
    public void removeRole(Roles role) {
        roles.remove(role);
    }
    public boolean hasRole(String role) {
        return roles.stream().anyMatch(p -> p.name().equals(role));
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

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
