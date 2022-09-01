package Interfaces.Necessary.Experience;

import Models.Necessary.ExperienceModel;

import java.util.List;
import java.util.Optional;

public interface ExperienceDao {
    ExperienceModel create (ExperienceModel experienceModel);
    boolean update (long experienceId, ExperienceModel experienceModel);
    boolean delete (long experienceId);
    List<ExperienceModel> list();
    Optional<ExperienceModel> getByID (long experienceId);
    List<ExperienceModel> listByCategory(long categoryId);
    //List<ExperienceModel> listByTags(long [] tagId);
}
