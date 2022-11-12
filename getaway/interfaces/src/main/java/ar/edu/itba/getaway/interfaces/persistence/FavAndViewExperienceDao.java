package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.UserModel;

public interface FavAndViewExperienceDao {
    void addFav (UserModel user, ExperienceModel experience);
    void deleteFav (UserModel user, ExperienceModel experience);
    void addViewed(UserModel user, ExperienceModel experience);
}
