package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.util.*;

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

    @OneToOne
    @JoinColumn(name = "imgId")
    private ImageModel profileImage;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userroles",
        joinColumns = @JoinColumn(name = "userId"),
        inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Collection<RoleModel> roles;

    @ManyToMany
    @JoinTable(name = "favuserexperience",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "experienceId")
    )
    private List<ExperienceModel> favExperiences;

    @ManyToMany
    @JoinTable(name = "viewed",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "experienceId")
    )
    private List<ExperienceModel> viewedExperiences;

    /* default */
    protected UserModel() {
        // Just for Hibernate
    }

    public UserModel(String password, String name, String surname, String email, Collection<RoleModel> roles, ImageModel profileImage) {
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.roles = roles;
        this.profileImage = profileImage;
    }

    public UserModel(Long userId, String password, String name, String surname, String email, Collection<RoleModel> roles, ImageModel profileImage) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.roles = roles;
        this.profileImage = profileImage;
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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
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
    public Integer getFavCount() {
        return favExperiences.size();
    }
    public List<ExperienceModel> getFavExperiences() {
        return favExperiences;
    }

    public List<ExperienceModel> getFavExperiences(Integer page, Integer pageSize, Optional<OrderByModel> orderByModel) {
        if (orderByModel.isPresent()) {
            favExperiences.sort(orderByModel.get().comparator);
        } else {
            favExperiences.sort(OrderByModel.OrderByRankDesc.comparator);
        }

        favExperiences.removeIf(experience -> !experience.getObservable());

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min((fromIndex + pageSize), favExperiences.size());
        return favExperiences.subList(fromIndex, toIndex);
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

    public void addViewed(ExperienceModel experience) {
        viewedExperiences.add(experience);
    }
    public boolean isViewed(ExperienceModel experience) {
        return viewedExperiences.contains(experience);
    }
    public List<ExperienceModel> getViewedExperiences() {
        int toIndex = viewedExperiences.size();
        int fromIndex = Math.max((toIndex-9), 0);
        return viewedExperiences.subList(fromIndex, toIndex);
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
        return this.userId.equals(other.userId) && this.email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
