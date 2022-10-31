package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.interfaces.persistence.FavAndViewExperienceDao;
import ar.edu.itba.getaway.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FavAndViewExperienceDaoImpl implements FavAndViewExperienceDao {
    @PersistenceContext
    private EntityManager em;
    private static final Logger LOGGER = LoggerFactory.getLogger(FavAndViewExperienceDaoImpl.class);

    @Override
    public void addFav(UserModel user, ExperienceModel experience) {
        LOGGER.debug("Setting experience with {} as fav of user with id {}", experience.getExperienceId(), user.getUserId());
        user.addFav(experience);
        em.merge(user);
    }

    @Override
    public void deleteFav(UserModel user, ExperienceModel experience) {
        LOGGER.debug("Removing experience with {} as fav of user with id {}", experience.getExperienceId(), user.getUserId());
        user.removeFav(experience);
        em.merge(user);
    }

    @Override
    public void addViewed(UserModel user, ExperienceModel experience) {
        LOGGER.debug("Adding experience with {} to viewed by user with id {}", experience.getExperienceId(), user.getUserId());
        user.addViewed(experience);
        em.merge(user);
    }

}
