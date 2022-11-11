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
    List<ExperienceModel> listExperiencesByFilter(CategoryModel category, Double max, Long score, CityModel city, Optional<OrderByModel> order, int page, int pageSize);
    long countListByFilter(CategoryModel categoryId, Double max, Long score, CityModel city);
    List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category, int size);
    List<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, int page, int pageSize);
    long getCountByName(String name);
    List<ExperienceModel> getExperiencesListByUser(String name, UserModel user, Optional<OrderByModel> order, int page, int pageSize);
    long getCountExperiencesByUser(String name, UserModel user);
    //Recommendation methods
    List<ExperienceModel> getRecommendedByFavs(UserModel user, int maxResults);
    List<ExperienceModel> getRecommendedByViews(UserModel user, int maxResults, List<Long> alreadyAdded);
    List<ExperienceModel> getBestRanked(int maxResults, List<Long> alreadyAdded);

    List<Long> reviewedExperiencesId(UserModel user);
    List<ExperienceModel> getRecommendedByReviewsCity(UserModel user, int maxResults, List<Long> alreadyAdded, List<Long> reviewedIds);
    List<ExperienceModel> getRecommendedByReviewsProvider(UserModel user, int maxResults, List<Long> alreadyAdded, List<Long> reviewedIds);
    List<ExperienceModel> getRecommendedByReviewsCategory(UserModel user, int maxResults, List<Long> alreadyAdded, List<Long> reviewedIds);
}
