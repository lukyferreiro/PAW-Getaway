package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    ExperienceModel createExperience (String name, String address, String description, String email, String url, Double price, CityModel city, CategoryModel category, UserModel user, byte[] image);
    void updateExperience (ExperienceModel experienceModel, byte[] image);
    void deleteExperience (ExperienceModel experienceModel);
    Optional<ExperienceModel> getExperienceById(Long experienceId);
    List<ExperienceModel> listExperiencesByUser(UserModel user, CategoryModel category);
    Page<ExperienceModel> listExperiencesByFilter(CategoryModel category, Double max, Long score, CityModel city, Optional<OrderByModel> order, Integer page);
    List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category);
    Optional<Double> getMaxPriceByCategory(CategoryModel category);
    Page<ExperienceModel> listExperiencesFavsByUser(UserModel user, Optional<OrderByModel> order, Integer page);
    Page<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, Integer page);
    List<List<ExperienceModel>> getExperiencesListByCategories();
    List<List<ExperienceModel>> getExperiencesListByCategoriesByUserId(UserModel user);
    boolean hasExperiencesByUser(UserModel user);
    boolean experienceBelongsToUser(UserModel user, ExperienceModel experience);
    Page<ExperienceModel> getExperiencesListByUser(String name, UserModel user, Optional<OrderByModel> order, Integer page);
    void updateExperienceWithoutImg(ExperienceModel toUpdateExperience);
//    void increaseView(ExperienceModel experience);
}