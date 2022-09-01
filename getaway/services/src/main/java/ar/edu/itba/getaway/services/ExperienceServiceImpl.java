package Interfaces.Necessary.Experience;

import Models.Necessary.CategoryModel;
import Models.Necessary.ExperienceModel;

import java.util.List;
import java.util.Optional;

public class ExperienceServiceImpl {
    @Autowired
    private ExperienceDao experienceDao;

    @Override
    public ExperienceDao create (ExperienceModel experienceModel){
        return experienceDao.create(experienceModel);
    }

    @Override
    public boolean update(long experienceId, ExperienceModel experienceModel){
        return experienceDao.update(experienceId, experienceModel);
    }

    @Override
    public boolean delete (long experienceId){
        return experienceDao.delete(experienceId);
    }

    @Override
    public List<ExperienceModel> list() {
        return experienceDao.list();
    }

    @Override
    public Optional<ExperienceModel> getById (long experienceId){
        return experienceDao.getByID(experienceId);
    }

    @Override
    List<ExperienceModel> listByCategory(long categoryId) { return experienceDao.listByCategory(categoryId); }



}
