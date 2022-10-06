package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.OrderByModel;
import ar.edu.itba.getaway.models.pagination.Page;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    ExperienceModel createExperience (String name, String address, String description, String email, String url, Double price, Long cityId, Long categoryId, Long userId, byte[] image);
    void updateExperience (ExperienceModel experienceModel, byte[] image);
    void deleteExperience (Long experienceId);
    Optional<ExperienceModel> getExperienceById(Long experienceId);
    List<ExperienceModel> listExperiencesByUserId(Long userId, Long categoryId);
    Page<ExperienceModel> listExperiencesByFilter(Long categoryId, Double max, Long score, Long city, Optional<OrderByModel> order, Integer page);
    List<ExperienceModel> listExperiencesByBestRanked(Long categoryId);
    Optional<Double> getMaxPriceByCategoryId(Long categoryId);
    Page<ExperienceModel> listExperiencesFavsByUserId(Long userId, Optional<OrderByModel> order, Integer page);
    Page<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, Integer page);
    List<List<ExperienceModel>> getExperiencesListByCategories();
    List<List<ExperienceModel>> getExperiencesListByCategoriesByUserId(Long userId);
    boolean hasExperiencesByUserId(Long userId);
    boolean experiencesBelongsToId(Long userId, Long experienceId);
}