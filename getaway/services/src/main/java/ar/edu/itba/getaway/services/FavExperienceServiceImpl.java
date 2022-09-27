package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.FavExperienceModel;
import ar.edu.itba.getaway.persistence.FavExperienceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavExperienceServiceImpl implements FavExperienceService{

    @Autowired
    private FavExperienceDao favExperienceDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(FavExperienceServiceImpl.class);

    @Override
    public FavExperienceModel create(long userId, long experienceId) {
        LOGGER.debug("User with id {} fav experience with id {}", userId, experienceId);
        return favExperienceDao.create(userId, experienceId);
    }

    @Override
    public boolean delete(long userId, long experienceId) {
        LOGGER.debug("User with id {} remove from fav experience with id {}", userId, experienceId);
        return favExperienceDao.delete(userId,experienceId);
    }

    @Override
    public List<FavExperienceModel> getByExperienceId(long experienceId) {
        LOGGER.debug("Retrieving all favs of experience with id {}", experienceId);
        return favExperienceDao.getByExperienceId(experienceId);
    }

    @Override
    public List<FavExperienceModel> listAll() {
        LOGGER.debug("Retrieving all favs");
        return favExperienceDao.listAll();
    }

    @Override
    public List<Long> listByUserId(Long userId) {
        LOGGER.debug("Retrieving all favs of user with id {}", userId);
        return favExperienceDao.listByUserId(userId);
    }
}
