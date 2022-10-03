package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.OrderByModel;

import java.util.List;
import java.util.Optional;

public interface ExperienceDao {
    ExperienceModel create (String name, String address, String description, String email, String url, Double price, long cityId, long categoryId, long userId);
    boolean update (ExperienceModel experienceModel);
    boolean delete (long experienceId);
    Optional<ExperienceModel> getById (long experienceId);
    Optional<Double> getMaxPrice(long categoryId);
    List<ExperienceModel> listAll (String order);
    List<ExperienceModel> listByUserId(long userId, Optional<OrderByModel> order);
    List<ExperienceModel> listByFilter(long categoryId, Double max, long score, Long city, Optional<OrderByModel> order, int page, int page_size);
    Integer countListByFilter(long categoryId, Double max, long score, Long city);
    List<ExperienceModel> listByBestRanked(long categoryId);
    List<ExperienceModel> listFavsByUserId(Long userId, Optional<OrderByModel> order);

    List<ExperienceModel> getByName(String name, int page, int page_size);
    Integer getCountByName(String name);
}
