package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.persistence.ExperienceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceDao experienceDao;

    @Autowired
    public ExperienceServiceImpl(ExperienceDao experienceDao){
        this.experienceDao = experienceDao;
    }

    @Override
    public ExperienceModel create (ExperienceModel experienceModel){
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
    public List<ExperienceModel> listAll() {
        return experienceDao.listAll();
    }

    @Override
    public Optional<ExperienceModel> getById (long experienceId){
        return experienceDao.getById(experienceId);
    }

    @Override
    public List<ExperienceModel> listByCategory(long categoryId) {
        return experienceDao.listByCategory(categoryId);
    }

}
