package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.FavExperienceModel;
import ar.edu.itba.getaway.interfaces.persistence.FavExperienceDao;
import ar.edu.itba.getaway.interfaces.services.FavExperienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavExperienceServiceImpl implements FavExperienceService {

    @Autowired
    private FavExperienceDao favExperienceDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(FavExperienceServiceImpl.class);

    @Override
    public FavExperienceModel createFav(Long userId, Long experienceId) {
        LOGGER.debug("User with id {} fav experience with id {}", userId, experienceId);
        return favExperienceDao.createFav(userId, experienceId);
    }

    @Override
    public void deleteFav(Long userId, Long experienceId) {
        LOGGER.debug("User with id {} remove from fav experience with id {}", userId, experienceId);
        favExperienceDao.deleteFav(userId, experienceId);
    }

    @Override
    public boolean isFav(Long userId, Long experienceId){
        return favExperienceDao.isFav(userId, experienceId);
    }

    @Override
    public List<Long> listFavsByUserId(Long userId) {
        LOGGER.debug("Retrieving all favs of user with id {}", userId);
        return favExperienceDao.listFavsByUserId(userId);
    }

    @Override
    public void setFav(Long userId, Optional<Boolean> set, Optional<Long> experience){
        if (experience.isPresent() && set.isPresent()) {
            if (set.get() ) {
                if(!isFav(userId, experience.get())){
                    createFav(userId, experience.get());
                }
            }
            else {
                deleteFav(userId, experience.get());
            }
        }
    }

}
