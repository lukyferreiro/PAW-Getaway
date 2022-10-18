package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.*;

import java.util.List;
import java.util.Optional;

public interface ExperienceDao {
    ExperienceModel createExperience(String name, String address, String description, String email, String url, Double price, CityModel cityId, CategoryModel categoryId, UserModel userId);
    void updateExperience(ExperienceModel experienceModel);
    void deleteExperience(ExperienceModel experienceModel);
    Optional<ExperienceModel> getExperienceById (Long experienceId);
    List<ExperienceModel> listExperiencesByUser(UserModel user, CategoryModel category);
    Optional<Double> getMaxPriceByCategory(CategoryModel category);

    // Capaz podría dejarse como el sql
    List<ExperienceModel> listExperiencesByFilter(CategoryModel category, Double max, Long score, CityModel city, Optional<OrderByModel> order, Integer page, Integer page_size);

    Long countListByFilter(CategoryModel categoryId, Double max, Long score, CityModel city);
    List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category);
    List<ExperienceModel> listExperiencesFavsByUser(UserModel user, Optional<OrderByModel> order, Integer page, Integer page_size);
    Integer getCountExperiencesFavsByUser(UserModel user);
    List<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, Integer page, Integer page_size);
    Integer getCountByName(String name);
    boolean hasExperiencesByUser(UserModel user);
    boolean experienceBelongsToUser(UserModel user, ExperienceModel experience);
}
