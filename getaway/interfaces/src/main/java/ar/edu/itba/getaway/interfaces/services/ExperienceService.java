package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.OrderByModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    ExperienceModel createExperience (String name, String address, String description, String email, String url, Double price, Long cityId, Long categoryId, Long userId, byte[] image);
    void updateExperience (ExperienceModel experienceModel, byte[] image);
    void deleteExperience (ExperienceModel experienceModel);
    Optional<ExperienceModel> getExperienceById(Long experienceId);
    List<ExperienceModel> listExperiencesByUser(UserModel user, CategoryModel category);
    Page<ExperienceModel> listExperiencesByFilter(Long categoryId, Double max, Long score, Long city, Optional<OrderByModel> order, Integer page);
    List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category);
    Optional<Double> getMaxPriceByCategory(CategoryModel category);
    Page<ExperienceModel> listExperiencesFavsByUser(UserModel user, Optional<OrderByModel> order, Integer page);
    Page<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, Integer page);
    List<List<ExperienceModel>> getExperiencesListByCategories();
    List<List<ExperienceModel>> getExperiencesListByCategoriesByUserId(UserModel user);
    boolean hasExperiencesByUserId(UserModel user);
    boolean experiencesBelongsToId(UserModel user, ExperienceModel experience);
}