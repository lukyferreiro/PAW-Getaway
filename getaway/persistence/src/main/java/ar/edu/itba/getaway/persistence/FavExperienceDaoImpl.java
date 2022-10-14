//package ar.edu.itba.getaway.persistence;
//
//import ar.edu.itba.getaway.models.ExperienceModel;
//import ar.edu.itba.getaway.models.FavExperienceModel;
//import ar.edu.itba.getaway.interfaces.persistence.FavExperienceDao;
//import ar.edu.itba.getaway.models.ReviewModel;
//import ar.edu.itba.getaway.models.UserModel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Repository
//public class FavExperienceDaoImpl implements FavExperienceDao {
//
//    @PersistenceContext
//    private EntityManager em;
//    private static final Logger LOGGER = LoggerFactory.getLogger(FavExperienceDaoImpl.class);
//
//
//
//  /*  @Autowired
//    public FavExperienceDaoImpl(final DataSource ds) {
//        this.jdbcTemplate = new JdbcTemplate(ds);
//        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
//                .withTableName("favuserexperience");
//    }*/
//
//    @Override
//    public FavExperienceModel createFav(UserModel user, ExperienceModel experience) {
//        final FavExperienceModel favExperienceModel = new FavExperienceModel(user, experience);
//
//        LOGGER.info("Setting experience with {} as fav of user with id {}", experience.getExperienceId(), user.getUserId());
//
//        em.persist(favExperienceModel);
//
//        return favExperienceModel;
//    }
//
//    @Override
//    public void deleteFav(UserModel user, ExperienceModel experience) {
//        final String query = "DELETE FROM favuserexperience WHERE experienceId = ? AND userid = ?";
//        LOGGER.debug("Executing query: {}", query);
//
//    }
//
//    @Override
//    public List<Long> listFavsByUser(UserModel user) {
//        final TypedQuery<Long> query = em.createQuery("FROM FavExperienceModel WHERE user =: user ", Long.class);
//        query.setParameter("user", user);
//        return query.getResultList();
//
//
//    }
//
//    @Override
//    public boolean isFav(UserModel user, ExperienceModel experience) {
//        final TypedQuery<FavExperienceModel> query = em.createQuery("FROM favuserexperience WHERE user = :user AND experience = :experience", FavExperienceModel.class);
//        query.setParameter("user", user);
//        query.setParameter("experience", experience);
//        return query.getResultList().stream().findFirst().isPresent();
//    }
//}
