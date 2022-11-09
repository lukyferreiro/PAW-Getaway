package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    ExperienceModel createExperience (String name, String address, String description, String email, String url, Double price, CityModel city, CategoryModel category, UserModel user, byte[] image);
    void updateExperience (ExperienceModel experienceModel, byte[] image);
    void deleteExperience (ExperienceModel experienceModel);
    Optional<ExperienceModel> getExperienceById(long experienceId);
    Optional<ExperienceModel> getVisibleExperienceById(long experienceId, UserModel user);
    //TODO VER ACA
    Page<ExperienceModel> listExperiencesByFilter(CategoryModel category, Double max, Long score, CityModel city, Optional<OrderByModel> order, int page, UserModel user);
    List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category, UserModel user);
    Optional<Double> getMaxPriceByCategory(CategoryModel category);
    Page<ExperienceModel> listExperiencesFavsByUser(UserModel user, Optional<OrderByModel> order, int page);
    Page<ExperienceModel> listExperiencesSearch(String name, Optional<OrderByModel> order, int page, UserModel user);
    List<List<ExperienceModel>> getExperiencesListByCategories(UserModel user);
    boolean experienceBelongsToUser(UserModel user, ExperienceModel experience);
    Page<ExperienceModel> listExperiencesListByUser(String name, UserModel user, Optional<OrderByModel> order, int page);
    void updateExperienceWithoutImg(ExperienceModel toUpdateExperience);
    void increaseViews(ExperienceModel experience);
    void changeVisibility(ExperienceModel experience, boolean obs);
}