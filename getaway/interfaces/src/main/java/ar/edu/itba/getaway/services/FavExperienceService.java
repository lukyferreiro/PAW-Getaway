package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.FavExperienceModel;

import java.util.List;
import java.util.Optional;

public interface FavExperienceService {
    FavExperienceModel create (long userId, long experienceId);
    boolean delete(long userId, long experienceId);
//    List<FavExperienceModel> getByExperienceId(long experienceId);
//    List<FavExperienceModel> listAll();
    List<Long> listByUserId(Long userId);
    void setFav(long userId, Optional<Boolean> set, Optional<Long> experience);
}
