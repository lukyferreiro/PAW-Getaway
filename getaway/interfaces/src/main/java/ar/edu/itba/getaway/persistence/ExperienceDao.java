package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.exceptions.DuplicateImageException;
import ar.edu.itba.getaway.models.ExperienceModel;

import java.util.List;
import java.util.Optional;

public interface ExperienceDao {
    ExperienceModel create (String name, String address, String description, String email, String url, Double price, long cityId, long categoryId, long userId, byte[] image) throws DuplicateImageException;
    boolean update (ExperienceModel experienceModel, byte[] image);
    boolean delete (long experienceId);
    Optional<ExperienceModel> getById (long experienceId);
    String getCountryCity(long experienceId);
    Optional<Long> getAvgReviews(long experienceId);
    Optional<Double> getMaxPrice(long categoryId);

    List<ExperienceModel> listAll (String order);
    List<ExperienceModel> listByUserId(long userId, String order);
    List<ExperienceModel> listByFilterWithCity(long categoryId, Double max, long cityId, long score, String order, int page, int page_size);
    List<ExperienceModel> listByFilter(long categoryId, Double max,  long score, String order, int page, int page_size);
    List<ExperienceModel> listBetterRanked(long categoryId);

    Integer countListByFilterWithCity(long categoryId, Double max, long cityId, long score);
    Integer countListByFilter(long categoryId, Double max, long score);

}
