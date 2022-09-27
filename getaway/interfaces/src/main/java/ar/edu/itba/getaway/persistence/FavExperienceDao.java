package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.FavExperienceModel;

import java.util.List;

public interface FavExperienceDao {
    FavExperienceModel create (long userId, long experienceId);
    boolean delete(long userId, long experienceId);
    List<FavExperienceModel> getByExperienceId(long experienceId);
    List<FavExperienceModel> listAll();
    List<Long> listByUserId(Long userId);
}
