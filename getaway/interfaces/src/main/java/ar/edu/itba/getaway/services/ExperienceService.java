package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ExperienceModel;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    ExperienceModel create (String name, String address, String description, String url, long price, long cityId, long categoryId, long userId);
    boolean update (long experienceId, ExperienceModel experienceModel);
    boolean delete (long experienceId);
    List<ExperienceModel> listAll();
    Optional<ExperienceModel> getById (long experienceId);
    List<ExperienceModel> listByCategory (long categoryId);
    //TODO: agregar metodos para los filtros
    //List<ExperienceModel> listByTags (long [] tagId);

}
