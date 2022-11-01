package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.*;

import java.util.List;
import java.util.Optional;

public interface ExperienceDao {
    ExperienceModel createExperience(String name, String address, String description, String email, String url, Double price, CityModel cityId, CategoryModel categoryId, UserModel userId, ImageModel experienceImage);
    void updateExperience(ExperienceModel experienceModel);
    void deleteExperience(ExperienceModel experienceModel);
    Optional<ExperienceModel> getExperienceById (Long experienceId);
    Optional<ExperienceModel> getVisibleExperienceById(Long experienceId, UserModel user);
    Optional<Double> getMaxPriceByCategory(CategoryModel category);
    List<ExperienceModel> listExperiencesByFilter(CategoryModel category, Double max, Long score, CityModel city, Optional<OrderByModel> order, Integer page, Integer pageSize, UserModel user);
    Long countListByFilter(CategoryModel categoryId, Double max, Long score, CityModel city, UserModel user);
    List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category);
    List<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, UserModel user);
/*    Long getCountByName(String name, UserModel user);*/
/*    Long getCountExperiencesByUser(String name, UserModel user);*/
    List<ExperienceModel> getExperiencesListByUser(String name, UserModel user, Optional<OrderByModel> order);
    List<ExperienceModel> getRecommendedByFavs(UserModel user);
    List<ExperienceModel> getRecommendedByViews(UserModel user);
}
