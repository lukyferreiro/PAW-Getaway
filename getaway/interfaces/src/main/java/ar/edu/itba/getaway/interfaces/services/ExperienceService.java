package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateExperienceException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    ExperienceModel createExperience(String name, String address, String description, String email, String url, Double price, CityModel city, CategoryModel category, UserModel user) throws DuplicateExperienceException;

    void updateExperience(ExperienceModel experienceModel);

    void deleteExperience(ExperienceModel experienceModel);

    Optional<ExperienceModel> getExperienceById(long experienceId);

    Optional<ExperienceModel> getVisibleExperienceById(long experienceId, UserModel user);

    Page<ExperienceModel> listExperiencesByFilter(CategoryModel category, String name, Double max, Long score, CityModel city, OrderByModel order, int page, UserModel user);

    Optional<Double> getMaxPriceByCategoryAndName(CategoryModel category, String name);

    Page<ExperienceModel> listExperiencesByBestRanked(CategoryModel category, UserModel user);

    Page<ExperienceModel> listExperiencesFavsByUser(UserModel user, OrderByModel order, int page);

//    List<List<ExperienceModel>> getExperiencesListByCategories(UserModel user);

    boolean experienceBelongsToUser(UserModel user, ExperienceModel experience);

    Page<ExperienceModel> listExperiencesSearchByUser(String name, UserModel user, OrderByModel order, int page);

    void updateExperienceWithoutImg(ExperienceModel toUpdateExperience);

    void increaseViews(ExperienceModel experience);

    void changeVisibility(ExperienceModel experience, boolean obs);

//    List<List<ExperienceModel>> userLandingPage(UserModel user);
    Page<ExperienceModel> getViewedExperiences(UserModel user);
    Page<ExperienceModel> getRecommendedByFavs(UserModel user);
    Page<ExperienceModel> getRecommendedByReviews(UserModel user);

}