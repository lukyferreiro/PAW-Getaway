package ar.edu.itba.getaway.models;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userId_seq")
    @SequenceGenerator(sequenceName = "users_userId_seq", name = "users_userId_seq", allocationSize = 1)
    @Column(name = "userId")
    private long userId;
    @Column(name = "userName", length = 50, nullable = false)
    private String name;
    @Column(name = "userSurname", length = 50, nullable = false)
    private String surname;
    @Column(name = "email", length = 255, nullable = false)
    private String email;
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "imgId")
    private ImageModel profileImage;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userroles", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<RoleModel> roles;

    @ManyToMany
    @JoinTable(name = "favuserexperience", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "experienceId"))
    private List<ExperienceModel> favExperiences;

    @ManyToMany
    @JoinTable(name = "viewed",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "experienceId")
    )
    private List<ExperienceModel> viewedExperiences;

    @Formula(value = "(select count(*) from experiences where experiences.userId = userId)!=0")
    private boolean hasExperiences;

    @Formula(value = "(select count(*) from reviews where reviews.userId = userId)!=0")
    private boolean hasReviews;

    /* default */
    protected UserModel() {
        // Just for Hibernate
    }

    public UserModel(String password, String name, String surname, String email, Collection<RoleModel> roles, ImageModel profileImage) {
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.roles = new ArrayList<>(roles);
        this.profileImage = profileImage;
    }

    public UserModel(long userId, String password, String name, String surname, String email, Collection<RoleModel> roles, ImageModel profileImage) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.roles = new ArrayList<>(roles);
        this.profileImage = profileImage;
        this.favExperiences = new ArrayList<>();
        this.viewedExperiences = new ArrayList<>();
    }

    public ImageModel getProfileImage() {
        return profileImage;
    }
    public void setProfileImageId(ImageModel profileImage) {
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
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    // Role methods
    public Collection<RoleModel> getRoles() {
        return roles;
    }
    public void addRole(RoleModel role) {
        roles.add(role);
    }
    public void removeRole(RoleModel role) {
        roles.remove(role);
    }
    public boolean hasRole(String role) {
        return roles.stream().anyMatch(p -> p.getRoleName().name().equals(role));
    }

    // Favs methods
    public int getFavCount() {
        return favExperiences.size();
    }
    public List<ExperienceModel> getFavExperiences() {
        return favExperiences;
    }
    public void addFav(ExperienceModel experience) {
        favExperiences.add(experience);
    }
    public void removeFav(ExperienceModel experience) {
        favExperiences.remove(experience);
    }
    public boolean isFav(ExperienceModel experience) {
        return favExperiences.contains(experience);
    }

    // Image methods
    public long getImageId() {
        if (profileImage == null) {
            return -1;
        } else {
            return profileImage.getImageId();
        }
    }

    public byte[] getImage() {
        if (profileImage == null) {
            return null;
        } else {
            return profileImage.getImage();
        }
    }

    @Transient
    public boolean hasExperiences(){
        return hasExperiences;
    }

    @Transient
    public boolean hasReviews(){
        return hasReviews;
    }

    public void addViewed(ExperienceModel experience) {
        viewedExperiences.add(experience);
    }
    public boolean isViewed(ExperienceModel experience) {
        return viewedExperiences.contains(experience);
    }
    public List<ExperienceModel> getViewedExperiences() {
        return viewedExperiences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserModel)) {
            return false;
        }
        UserModel other = (UserModel) o;
        return this.getUserId() == other.getUserId() && this.getEmail().equals(other.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
