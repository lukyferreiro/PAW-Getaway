package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;

import java.util.List;
import java.util.Optional;

public interface ExperienceDao {
    ExperienceModel create (String name, String address, String description, String url, Double price, long cityId, long categoryId, long userId, boolean hasImage);
    boolean update (long experienceId, ExperienceModel experienceModel);
    boolean delete (long experienceId);
    List<ExperienceModel> listAll ();
    Optional<ExperienceModel> getById (long experienceId);
    List<ExperienceModel> listByCategory (long categoryId);
    List<ExperienceModel> listByCategoryAndCity (long categoryId, long cityId);
    List<ExperienceModel> listByCategoryAndPrice (long categoryId, Double max);
    List<ExperienceModel> listByCategoryPriceAndCity (long categoryId, Double max, long cityId);
    List<ExperienceModel> getRandom();
    String getCountryCity(long experienceId);
    List<ExperienceModel> getByUserId(long userId);
    Optional<Long> getAvgReviews(long experienceId);
    List<ExperienceModel> listByCategoryPriceCityAndScore (long categoryId, Double max, long cityId, long score);
    List<ExperienceModel> listByCategoryCityAndScore (long categoryId, long cityId, long score);
    List<ExperienceModel> listByCategoryPriceAndScore (long categoryId, Double max, long score);
    List<ExperienceModel> listByCategoryAndScore (long categoryId, long score);
    List<ExperienceModel> getByUserIdOrderByRankingDesc(long id);
    List<ExperienceModel> getByUserIdOrderByRankingAsc(long id);
    List<ExperienceModel> getByUserIdOrderByNameDesc(long id);
    List<ExperienceModel> getByUserIdOrderByNameAsc(long id);
    List<ExperienceModel> getByUserIdOrderByPriceDesc(long id);
    List<ExperienceModel> getByUserIdOrderByPriceAsc(long id);
    List<ExperienceModel> getOrderByRankingDesc();
    List<ExperienceModel> getOrderByRankingAsc();
    List<ExperienceModel> getOrderByNameDesc();
    List<ExperienceModel> getOrderByNameAsc();
    List<ExperienceModel> getOrderByPriceDesc();
    List<ExperienceModel> getOrderByPriceAsc();

}
