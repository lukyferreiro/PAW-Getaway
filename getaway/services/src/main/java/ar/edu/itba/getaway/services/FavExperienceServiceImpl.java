package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.FavExperienceModel;
import ar.edu.itba.getaway.persistence.FavExperienceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavExperienceServiceImpl implements FavExperienceService{

    @Autowired
    FavExperienceDao favExperienceDao;

    @Override
    public FavExperienceModel create(long userId, long experienceId) {
        return favExperienceDao.create(userId, experienceId);
    }

    @Override
    public boolean delete(long userId, long experienceId) {
        return favExperienceDao.delete(userId,experienceId);
    }

    @Override
    public List<FavExperienceModel> getByExperienceId(long experienceId) {
        return favExperienceDao.getByExperienceId(experienceId);
    }

    @Override
    public List<FavExperienceModel> listAll() {
        return favExperienceDao.listAll();
    }

    @Override
    public List<Long> listByUserId(Long id) {
        return favExperienceDao.listByUserId(id);
    }
}
