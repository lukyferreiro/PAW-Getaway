package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.interfaces.persistence.FavAndViewExperienceDao;
import ar.edu.itba.getaway.interfaces.services.FavAndViewExperienceService;
import ar.edu.itba.getaway.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FavAndViewExperienceServiceImpl implements FavAndViewExperienceService {

    @Autowired
    private FavAndViewExperienceDao favAndViewExperienceDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(FavAndViewExperienceServiceImpl.class);

    @Transactional
    @Override
    public void addFav(UserModel user, ExperienceModel experience) {
        LOGGER.debug("User with id {} add fav experience with id {}", user.getUserId(), experience.getExperienceId());
        favAndViewExperienceDao.addFav(user, experience);
    }

    @Transactional
    @Override
    public void deleteFav(UserModel user, ExperienceModel experience) {
        LOGGER.debug("User with id {} remove from fav experience with id {}", user.getUserId(), experience.getExperienceId());
        favAndViewExperienceDao.deleteFav(user, experience);
    }

    @Override
    public boolean isFav(UserModel user, ExperienceModel experience) {
        return user.isFav(experience);
    }

    @Transactional
    @Override
    public void setFav(UserModel user, Optional<Boolean> set, Optional<ExperienceModel> experience) {
        if (experience.isPresent() && set.isPresent()) {
            if (set.get()) {
                if (!isFav(user, experience.get())) {
                    addFav(user, experience.get());
                }
            } else {
                deleteFav(user, experience.get());
            }
        }
    }

    @Transactional
    @Override
    public void addViewed(UserModel user, ExperienceModel experience) {
        LOGGER.debug("User with id {} add viewed experience with id {}", user.getUserId(), experience.getExperienceId());
        favAndViewExperienceDao.addViewed(user, experience);
    }

    @Override
    public boolean isViewed(UserModel user, ExperienceModel experience) {
        return user.isViewed(experience);
    }

    @Transactional
    @Override
    public void setViewed(UserModel user, ExperienceModel experience) {
        if (!isViewed(user, experience)) {
            addViewed(user, experience);
        }
    }
}
