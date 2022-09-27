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
    List<ExperienceModel> listAll();
    Optional<ExperienceModel> getById(long experienceId);

    String getCountryCity(long experienceId);
    List<ExperienceModel> getByUserId(long userId);
    Optional<Long> getAvgReviews(long experienceId);

    Page<ExperienceModel> listByFilterWithCity(long categoryId, Double max, long cityId, long score, int page);
    Page<ExperienceModel> listByFilterWithCityAsc(long categoryId, Double max, long cityId, long score, String order, int page);
    Page<ExperienceModel> listByFilterWithCityDesc(long categoryId, Double max, long cityId, long score, String order, int page);
    Page<ExperienceModel> listByFilter(long categoryId, Double max,  long score, int page);
    Page<ExperienceModel> listByFilterAsc(long categoryId, Double max,  long score, String order, int page);
    Page<ExperienceModel> listByFilterDesc(long categoryId, Double max,  long score, String order, int page);

    Optional<Double> getMaxPrice(long categoryId);
    List<ExperienceModel> getByUserIdOrderBy(long id, String order);
    List<ExperienceModel> getByUserIdOrderByDesc(long id, String order);
    List<ExperienceModel> getOrderByDesc(String order);
    List<ExperienceModel> getOrderBy(String order);
}
