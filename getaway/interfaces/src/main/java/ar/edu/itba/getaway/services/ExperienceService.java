package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ExperienceModel;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    ExperienceModel create (ExperienceModel experienceModel);
    boolean update (long experienceId, ExperienceModel experienceModel);
    boolean delete (long experienceId);
    List<ExperienceModel> listAll();
    Optional<ExperienceModel> getById (long experienceId);

    //TODO implementar para ordenar por categoria
//    List<ExperienceModel> listByCategory (long categoryId);

}
