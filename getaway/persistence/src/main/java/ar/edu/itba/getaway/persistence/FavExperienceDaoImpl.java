package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.interfaces.persistence.FavExperienceDao;
import ar.edu.itba.getaway.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class FavExperienceDaoImpl implements FavExperienceDao {

    @PersistenceContext
    private EntityManager em;
    private static final Logger LOGGER = LoggerFactory.getLogger(FavExperienceDaoImpl.class);



  /*  @Autowired
    public FavExperienceDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("favuserexperience");
    }*/

    @Override
    public void addFav(UserModel user, ExperienceModel experience) {
//        final FavExperienceModel favExperienceModel = new FavExperienceModel(user, experience);

        LOGGER.debug("Setting experience with {} as fav of user with id {}", experience.getExperienceId(), user.getUserId());

        user.addFav(experience);
        em.persist(user);
//        em.persist(favExperienceModel);
//        return favExperienceModel;
    }

    @Override
    public void deleteFav(UserModel user, ExperienceModel experience) {
        LOGGER.debug("Removing experience with {} as fav of user with id {}", experience.getExperienceId(), user.getUserId());

        user.removeFav(experience);
        em.persist(user);
    }

    @Override
    public List<Long> listFavsByUser(UserModel user) {
        List<Long> toRet = new ArrayList<>();
        Collection<ExperienceModel> favExperiences = user.getFavExperiences();
        for (ExperienceModel exp: favExperiences) {
            toRet.add(exp.getExperienceId());
        }

        return toRet;
    }

    @Override
    public boolean isFav(UserModel user, ExperienceModel experience) {
        return user.isFav(experience);
    }
}
