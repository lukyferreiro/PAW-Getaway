package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;

import java.util.List;
import java.util.Optional;

public interface ExperienceDao {

    int PAGE_SIZE = 2;
    ExperienceModel create (String name, String address, String description, String url, Double price, long cityId, long categoryId, long userId, boolean hasImage);
    boolean update (long experienceId, ExperienceModel experienceModel);
    boolean delete (long experienceId);
    List<ExperienceModel> listAll ();
    Optional<ExperienceModel> getById (long experienceId);
    List<ExperienceModel> listByCategory (long categoryId,int page);
    List<ExperienceModel> listByCategoryAndCity (long categoryId, long cityId,int page);
    List<ExperienceModel> listByCategoryAndPrice (long categoryId, Double max,int page);
    List<ExperienceModel> listByCategoryPriceAndCity (long categoryId, Double max, long cityId ,int page);
    List<ExperienceModel> getRandom();
    String getCountryCity(long experienceId);
    List<ExperienceModel> getByUserId(long userId ,int page);
    Optional<Long> getAvgReviews(long experienceId);
    List<ExperienceModel> listByCategoryPriceCityAndScore (long categoryId, Double max, long cityId, long score ,int page);
    List<ExperienceModel> listByCategoryCityAndScore (long categoryId, long cityId, long score,int page );
    List<ExperienceModel> listByCategoryPriceAndScore (long categoryId, Double max, long score ,int page);
    List<ExperienceModel> listByCategoryAndScore (long categoryId, long score,int page);
    List<ExperienceModel> listByScore(int page, long categoryId);
    int getTotalPagesAllExperiences();
}
