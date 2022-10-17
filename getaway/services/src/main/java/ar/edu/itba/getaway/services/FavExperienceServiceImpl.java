package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.interfaces.persistence.FavExperienceDao;
import ar.edu.itba.getaway.interfaces.services.FavExperienceService;
import ar.edu.itba.getaway.models.UserModel;
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
    public void addFav(UserModel user, ExperienceModel experience) {
        LOGGER.debug("User with id {} fav experience with id {}", user.getUserId(), experience.getExperienceId());
        favExperienceDao.addFav(user, experience);
    }

    @Override
    public void deleteFav(UserModel user, ExperienceModel experience) {
        LOGGER.debug("User with id {} remove from fav experience with id {}", user.getUserId(), experience.getExperienceId());
        favExperienceDao.deleteFav(user, experience);
    }

    @Override
    public boolean isFav(UserModel user, ExperienceModel experience){
        return favExperienceDao.isFav(user, experience);
    }

    @Override
    public List<Long> listFavsByUser(UserModel user) {
            LOGGER.debug("Retrieving all favs of user with id {}", user.getUserId());
        return favExperienceDao.listFavsByUser(user);
    }

    @Override
    public void setFav(UserModel user, Optional<Boolean> set, Optional<ExperienceModel> experience){
        if (experience.isPresent() && set.isPresent()) {
            if (set.get() ) {
                if(!isFav(user, experience.get())){
                    addFav(user, experience.get());
                }
            }
            else {
                deleteFav(user, experience.get());
            }
        }
    }

}
