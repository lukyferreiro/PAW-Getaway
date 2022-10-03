package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.OrderByModel;
import ar.edu.itba.getaway.models.pagination.Page;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    ExperienceModel create (String name, String address, String description, String email, String url, Double price, long cityId, long categoryId, long userId, byte[] image);
    boolean update (ExperienceModel experienceModel, byte[] image);
    boolean delete (long experienceId);
    Optional<Double> getMaxPrice(long categoryId);
    Optional<ExperienceModel> getById(long experienceId);
    List<ExperienceModel> listAll(String order);
    List<ExperienceModel> listByUserId(long userId, Optional<OrderByModel> order);
    Page<ExperienceModel> listByFilter(long categoryId, Double max, long score, Long city, Optional<OrderByModel> order, int page);
    List<ExperienceModel> listByBestRanked(long categoryId);
    List<ExperienceModel> listFavsByUserId(Long userId, Optional<OrderByModel> order);
}

