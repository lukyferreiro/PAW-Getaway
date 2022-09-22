package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.FavExperienceModel;

public interface FavExperienceDao {
    FavExperienceModel create (long userId, long experienceId);
    boolean delete(long userId, long experienceId);
}
