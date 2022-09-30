package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.exceptions.DuplicateImageException;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.pagination.Page;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    ExperienceModel create (String name, String address, String description, String email, String url, Double price, long cityId, long categoryId, long userId, byte[] image) throws DuplicateImageException;
    boolean update (ExperienceModel experienceModel, byte[] image);
    boolean delete (long experienceId);

    Optional<Double> getMaxPrice(long categoryId);
    Optional<ExperienceModel> getById(long experienceId);

    List<ExperienceModel> listAll(String order);
    List<ExperienceModel> listByUserId(long userId, String order);
    Page<ExperienceModel> listByFilter(long categoryId, Double max, long score, String city, String order, int page);
    List<ExperienceModel> listByBestRanked(long categoryId);
    ExperienceModel listByName(String name);
}

