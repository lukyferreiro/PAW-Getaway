package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.OrderByModel;

import java.util.List;
import java.util.Optional;

public interface ExperienceDao {
    ExperienceModel createExperience(String name, String address, String description, String email, String url, Double price, Long cityId, Long categoryId, Long userId);
    boolean updateExperience(ExperienceModel experienceModel);
    boolean deleteExperience(Long experienceId);
    Optional<ExperienceModel> getExperienceById (Long experienceId);
    List<ExperienceModel> listExperiencesByUserId(Long userId, Long categoryId);
    Optional<Double> getMaxPriceByCategoryId(Long categoryId);
    List<ExperienceModel> listExperiencesByFilter(Long categoryId, Double max, Long score, Long city, Optional<OrderByModel> order, Integer page, Integer page_size);
    Integer countListByFilter(Long categoryId, Double max, Long score, Long city);
    List<ExperienceModel> listExperiencesByBestRanked(Long categoryId);
    List<ExperienceModel> listExperiencesFavsByUserId(Long userId, Optional<OrderByModel> order, Integer page, Integer page_size);
    Integer getCountExperiencesFavsByUserId(Long userId);
    List<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, Integer page, Integer page_size);
    Integer getCountByName(String name);
}
