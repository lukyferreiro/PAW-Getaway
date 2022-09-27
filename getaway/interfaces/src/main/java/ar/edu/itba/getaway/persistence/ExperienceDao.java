package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.exceptions.DuplicateImageException;
import ar.edu.itba.getaway.models.ExperienceModel;

import java.util.List;
import java.util.Optional;

public interface ExperienceDao {
    ExperienceModel create (String name, String address, String description, String email, String url, Double price, long cityId, long categoryId, long userId, byte[] image) throws DuplicateImageException;
    boolean update (ExperienceModel experienceModel, byte[] image);
    boolean delete (long experienceId);
    List<ExperienceModel> listAll ();
    Optional<ExperienceModel> getById (long experienceId);
    String getCountryCity(long experienceId);
    List<ExperienceModel> getByUserId(long userId);
    Optional<Long> getAvgReviews(long experienceId);


    List<ExperienceModel> listByFilterWithCity(long categoryId, Double max, long cityId, long score);
    List<ExperienceModel> listByFilterWithCityAsc(long categoryId, Double max, long cityId, long score, String order);
    List<ExperienceModel> listByFilterWithCityDesc(long categoryId, Double max, long cityId, long score, String order);
    List<ExperienceModel> listByFilter(long categoryId, Double max,  long score);
    List<ExperienceModel> listByFilterAsc(long categoryId, Double max,  long score, String order);
    List<ExperienceModel> listByFilterDesc(long categoryId, Double max,  long score, String order);


    Optional<Double> getMaxPrice(long categoryId);
    List<ExperienceModel> getByUserIdOrderBy(long id, String order);
    List<ExperienceModel> getByUserIdOrderByDesc(long id, String order);

    List<ExperienceModel> getOrderByDesc(String order);
    List<ExperienceModel> getOrderBy(String order);
}
