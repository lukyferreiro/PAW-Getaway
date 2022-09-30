package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;

import java.util.List;
import java.util.Optional;

public interface ExperienceDao {
    ExperienceModel create (String name, String address, String description, String email, String url, Double price, long cityId, long categoryId, long userId, byte[] image);
    boolean update (ExperienceModel experienceModel, byte[] image);
    boolean delete (long experienceId);
    Optional<ExperienceModel> getById (long experienceId);
    Optional<Double> getMaxPrice(long categoryId);
    List<ExperienceModel> listAll (String order);
    List<ExperienceModel> listByUserId(long userId, String order);
    List<ExperienceModel> listByFilter(long categoryId, Double max, long score, String city, String order, int page, int page_size);
    Integer countListByFilter(long categoryId, Double max, long score, String city);
    List<ExperienceModel> listByBestRanked(long categoryId);
}
