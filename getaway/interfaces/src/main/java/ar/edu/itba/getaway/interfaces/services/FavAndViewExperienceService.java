package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.UserModel;

import java.util.Optional;

public interface FavAndViewExperienceService {
    void addFav(UserModel user, ExperienceModel experience);

    void deleteFav(UserModel user, ExperienceModel experience);

    boolean isFav(UserModel user, ExperienceModel experience);

    void setFav(UserModel user, boolean set, ExperienceModel experience);

    void addViewed(UserModel user, ExperienceModel experience);

    boolean isViewed(UserModel user, ExperienceModel experience);

    void setViewed(UserModel user, boolean view, ExperienceModel experience);
}
