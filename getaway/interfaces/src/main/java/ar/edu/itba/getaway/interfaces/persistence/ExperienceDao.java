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
//    List<ExperienceModel> listExperiencesByFilter(Long categoryId, Double max, Long score, Long city, Optional<OrderByModel> order, Integer page, Integer page_size);
//    Integer countListByFilter(Long categoryId, Double max, Long score, Long city);
//    List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category);
//    List<ExperienceModel> listExperiencesFavsByUserId(UserModel user, Optional<OrderByModel> order, Integer page, Integer page_size);
//    Integer getCountExperiencesFavsByUser(UserModel user);
//    List<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, Integer page, Integer page_size);
//    Integer getCountByName(String name);
//    boolean hasExperiencesByUserId(UserModel user);
//    boolean experiencesBelongsToId(UserModel user, ExperienceModel experience);
}
