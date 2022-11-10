package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.*;

import java.util.List;
import java.util.Optional;

public interface ExperienceDao {
    ExperienceModel createExperience(String name, String address, String description, String email, String url, Double price, CityModel cityId, CategoryModel categoryId, UserModel userId, ImageModel experienceImage);

    void updateExperience(ExperienceModel experienceModel);

    void deleteExperience(ExperienceModel experienceModel);

    Optional<ExperienceModel> getExperienceById(long experienceId);

    Optional<ExperienceModel> getVisibleExperienceById(long experienceId);

    Optional<Double> getMaxPriceByCategory(CategoryModel category);

    //TODO VER ACA
    List<ExperienceModel> listExperiencesByFilter(CategoryModel category, Double max, Long score, CityModel city, Optional<OrderByModel> order, int page, int pageSize);

    long countListByFilter(CategoryModel categoryId, Double max, Long score, CityModel city);

    List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category);

    List<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, int page, int pageSize);

    long getCountByName(String name);

    List<ExperienceModel> getExperiencesListByUser(String name, UserModel user, Optional<OrderByModel> order, int page, int pageSize);

    long getCountExperiencesByUser(String name, UserModel user);

    List<ExperienceModel> getRecommendedByFavs(UserModel user);

    List<ExperienceModel> getRecommendedByReviewsCity(UserModel user);

    List<ExperienceModel> getRecommendedByReviewsProvider(UserModel user);

    List<ExperienceModel> getRecommendedByReviewsCategory(UserModel user);

    List<ExperienceModel> getRecommendedByViews(UserModel user);
}
