package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.pagination.Page;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    ExperienceModel create (String name, String address, String description, String url, Double price, long cityId, long categoryId, long userId, boolean hasImage);
    boolean update (long experienceId, ExperienceModel experienceModel);
    boolean delete (long experienceId);
    List<ExperienceModel> listAll ();
    Optional<ExperienceModel> getById (long experienceId);
    Page<ExperienceModel> listByCategory (long categoryId,int page);
    Page<ExperienceModel> listByCategoryAndCity (long categoryId, long cityId,int page);
    Page<ExperienceModel> listByCategoryAndPrice (long categoryId, Double max,int page);
    Page<ExperienceModel> listByCategoryPriceAndCity (long categoryId, Double max, long cityId,int page);
//    Page<ExperienceModel> getRandom();
    String getCountryCity(long experienceId);
    Page<ExperienceModel> getByUserId(long userId,int page);
    Optional<Long> getAvgReviews(long experienceId);
    Page<ExperienceModel> listByCategoryPriceCityAndScore (long categoryId, Double max, long cityId, long score,int page);
    Page<ExperienceModel> listByCategoryCityAndScore (long categoryId, long cityId, long score,int page);
    Page<ExperienceModel> listByCategoryPriceAndScore (long categoryId, Double max, long score,int page);
    Page<ExperienceModel> listByCategoryAndScore (long categoryId, long score,int page);
    Page<ExperienceModel> listByScore(int page, long categoryId);

}
