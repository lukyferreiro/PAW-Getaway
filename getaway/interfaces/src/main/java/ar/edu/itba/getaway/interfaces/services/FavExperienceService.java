package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.UserModel;

import java.util.Optional;

public interface FavExperienceService {
    void addFav (UserModel user, ExperienceModel experience);
    void deleteFav (UserModel user, ExperienceModel experience);
    boolean isFav (UserModel user, ExperienceModel experience);
    void setFav (UserModel user, Optional<Boolean> set, Optional<ExperienceModel> experience);
}
