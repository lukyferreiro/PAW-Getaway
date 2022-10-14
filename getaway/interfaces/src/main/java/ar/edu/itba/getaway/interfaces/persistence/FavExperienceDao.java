package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.FavExperienceModel;
import ar.edu.itba.getaway.models.UserModel;

import java.util.List;

public interface FavExperienceDao {
    FavExperienceModel createFav (UserModel user, ExperienceModel experience);
    void deleteFav (UserModel user, ExperienceModel experience);
    List<Long> listFavsByUser (UserModel user);
    boolean isFav (UserModel user, ExperienceModel experience);
}
