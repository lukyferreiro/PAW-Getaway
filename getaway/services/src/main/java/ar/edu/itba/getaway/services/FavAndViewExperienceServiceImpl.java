package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.interfaces.persistence.FavAndViewExperienceDao;
import ar.edu.itba.getaway.interfaces.services.FavAndViewExperienceService;
import ar.edu.itba.getaway.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavAndViewExperienceServiceImpl implements FavAndViewExperienceService {

    @Autowired
    private FavAndViewExperienceDao favAndViewExperienceDao;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private UserService userService;

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
    public boolean isFav(long userId, long experienceId) {
        final UserModel user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
        final ExperienceModel experience = experienceService.getVisibleExperienceById(experienceId, user).orElseThrow(ExperienceNotFoundException::new);
        return isFav(user, experience);
    }

    private boolean isFav(UserModel user, ExperienceModel experience) {
        return user.isFav(experience);
    }

    @Transactional
    @Override
    public void setFav(long userId, boolean set,  long experienceId) {
        final UserModel user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
        final ExperienceModel experience = experienceService.getVisibleExperienceById(experienceId, user).orElseThrow(ExperienceNotFoundException::new);

        if (set) {
            if (!isFav(user, experience)) {
                addFav(user, experience);
            }
        } else {
            deleteFav(user, experience);
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
    public void setViewed(UserModel user, boolean view, ExperienceModel experience) {
        if (view && user != null) {
            if (!isViewed(user, experience)) {
                addViewed(user, experience);
            }
        }
    }
}
