package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "favuserexperience")
public class FavExperienceModel {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experienceId")
    private ExperienceModel experience;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserModel user;

    /* default */
    protected FavExperienceModel() {
        // Just for Hibernate
    }

    public FavExperienceModel(UserModel user, ExperienceModel experience) {
        this.experience = experience;
        this.user = user;
    }

    public ExperienceModel getExperience() {
        return experience;
    }
    public void setExperience(ExperienceModel experience) {
        this.experience = experience;
    }
    public UserModel getUser() {
        return user;
    }
    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof FavExperienceModel)){
            return false;
        }
        FavExperienceModel other = (FavExperienceModel) o;
        return this.experience.equals(other.experience) && this.user.equals(other.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experience, user);
    }

}
