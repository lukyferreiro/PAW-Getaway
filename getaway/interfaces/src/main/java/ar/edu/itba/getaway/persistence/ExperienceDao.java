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
    List<ExperienceModel> getByUserIdOrderBy(long id, String order);
    List<ExperienceModel> getByUserIdOrderByDesc(long id, String order);
    List<ExperienceModel> getOrderByDesc(String order);
    List<ExperienceModel> getOrderBy(String order);


    List<ExperienceModel> listByCategoryOrderBy(long categoryId, String order);
    List<ExperienceModel> listByCategoryOrderByDesc(long categoryId, String order);

    List<ExperienceModel> listByCategoryAndCityOrderBy(long categoryId, long cityId, String order);
    List<ExperienceModel> listByCategoryAndCityOrderByDesc(long categoryId, long cityId, String order);


    List<ExperienceModel> listByCategoryAndPriceOrderBy(long categoryId,Double max, String order);
    List<ExperienceModel> listByCategoryAndPriceOrderByDesc(long categoryId,Double max,String order);

    List<ExperienceModel> listByCategoryPriceAndCityOrderBy(long categoryId, Double max, long cityId,  String order);
    List<ExperienceModel> listByCategoryPriceAndCityOrderByDesc(long categoryId, Double max, long cityId,  String order);

    List<ExperienceModel> listByCategoryPriceCityAndScoreOrderBy(long categoryId,Double max, long cityId, long score,  String order);
    List<ExperienceModel> listByCategoryPriceCityAndScoreOrderByDesc(long categoryId, Double max, long cityId, long score, String order);

    List<ExperienceModel> listByCategoryCityAndScoreOrderBy(long categoryId, long cityId, long score, String order);
    List<ExperienceModel> listByCategoryCityAndScoreOrderByDesc(long categoryId,  long cityId, long score, String order);


    List<ExperienceModel> listByCategoryPriceAndScoreOrderBy(long categoryId, Double max, long score, String order);
    List<ExperienceModel> listByCategoryPriceAndScoreOrderByDesc(long categoryId, Double max, long score, String order);

    List<ExperienceModel> listByCategoryAndScoreOrderBy(long categoryId, long score, String order);
    List<ExperienceModel> listByCategoryAndScoreOrderByDesc(long categoryId, long score, String order);
}
