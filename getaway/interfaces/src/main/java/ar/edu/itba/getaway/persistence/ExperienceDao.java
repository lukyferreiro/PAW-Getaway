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
    //TODO: agregar metodos para los filtros
    //List<ExperienceModel> listByTags (long [] tagId);
    List<ExperienceModel> listByCategoryAndCity (long categoryId, long cityId);
    List<ExperienceModel> listByCategoryAndPrice (long categoryId, Double max);
    List<ExperienceModel> listByCategoryPriceAndCity (long categoryId, Double max, long cityId);
    List<ExperienceModel> getRandom();
    String getCountryCity(long experienceId);
}
