package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.UserModel;

public interface FavAndViewExperienceService {
    void addFav(UserModel user, ExperienceModel experience);

    void deleteFav(UserModel user, ExperienceModel experience);

    boolean isFav(long userId,  long experienceId);

    void setFav(long userId, boolean set, long experienceId);

    void addViewed(UserModel user, ExperienceModel experience);

    boolean isViewed(UserModel user, ExperienceModel experience);

    void setViewed(UserModel user, boolean view, ExperienceModel experience);
}
