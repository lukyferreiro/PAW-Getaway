package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.FavExperienceModel;

public interface FavExperienceService {
    FavExperienceModel create (long userId, long experienceId);
    boolean delete(long userId, long experienceId);
}
