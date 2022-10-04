package ar.edu.itba.interfaces.services;

import ar.edu.itba.getaway.models.FavExperienceModel;

import java.util.List;
import java.util.Optional;

public interface FavExperienceService {
    FavExperienceModel createFav (Long userId, Long experienceId);
    void deleteFav (Long userId, Long experienceId);
    List<Long> listFavsByUserId (Long userId);
    boolean isFav (Long userId, Long experienceId);
    void setFav (Long userId, Optional<Boolean> set, Optional<Long> experience);
}
