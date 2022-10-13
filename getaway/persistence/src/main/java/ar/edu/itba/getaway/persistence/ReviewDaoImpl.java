package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;
import ar.edu.itba.getaway.interfaces.persistence.ReviewDao;
import ar.edu.itba.getaway.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.*;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    @PersistenceContext
    private EntityManager em;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDaoImpl.class);

//    @Autowired
//    public ReviewDaoImpl(final DataSource ds) {
//        this.jdbcTemplate = new JdbcTemplate(ds);
//        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
//                .withTableName("reviews")
//                .usingGeneratedKeyColumns("reviewid");
//    }

    @Override
    public ReviewModel createReview(String title, String description, Long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel) {

        LOGGER.debug("Creating review for an experience");
        final ReviewModel reviewModel = new ReviewModel(title, description, score, experienceModel, reviewDate, userModel);
        em.persist(reviewModel);
        LOGGER.debug("Created new review with id {} by user with id {}", userModel.getUserId());

        return reviewModel;

    }

    @Override
    public List<ReviewModel> getReviewsByExperience(ExperienceModel experience) {
        LOGGER.debug("Get reviews with experience {}", experience.getExperienceName());
        final TypedQuery<ReviewModel> query = em.createQuery("FROM ReviewModel WHERE experience = :experience", ReviewModel.class);
        query.setParameter("experience", experience);
        return query.getResultList();

    }

    @Override
    public Long getReviewAverageScore(ExperienceModel experience) {
        final TypedQuery<Long> query = em.createQuery("SELECT CEILING(AVG(score)) FROM ReviewModel WHERE experience = :experience", Long.class);
        query.setParameter("experience", experience);
        return query.getSingleResult();
    }

    @Override
    public Integer getReviewCount(ExperienceModel experience) {
        final TypedQuery<Integer> query = em.createQuery("SELECT COUNT (*) FROM ReviewModel WHERE experience = :experience", Integer.class);
        query.setParameter("experience", experience);
        return query.getSingleResult();

    }

    //TODO paginacion + hibernate
//    @Override
//    public List<ReviewUserModel> getReviewAndUser(ExperienceModel experience, Integer page, Integer page_size) {
//        final String query = "SELECT * FROM reviews NATURAL JOIN users WHERE experienceId = ? ORDER BY reviewid DESC LIMIT ? OFFSET ? ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{experienceId, page_size, (page - 1) * page_size}, REVIEW_USER_ROW_MAPPER);
//    }

    @Override
    public Optional<ReviewModel> getReviewById(Long reviewId) {
        LOGGER.debug("Get review with id {}", reviewId);
        return Optional.ofNullable(em.find(ReviewModel.class, reviewId));

    }

    //TODO paginacion + hibernate
//    @Override
//    public List<ReviewUserModel> getReviewsByUser(UserModel user, Integer page, Integer page_size) {
//        final String query = "SELECT * FROM reviews  NATURAL JOIN users WHERE userid = ?  ORDER BY reviewid DESC LIMIT ? OFFSET ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{userId, page_size, (page - 1) * page_size}, REVIEW_USER_ROW_MAPPER);
//    }

    @Override
    public Integer getReviewByUserCount(UserModel user) {
        final TypedQuery<Integer> query = em.createQuery("SELECT COUNT (*) FROM ReviewModel WHERE user = :user", Integer.class);
        query.setParameter("user", user);

        return query.getSingleResult();

    }

    @Override
    public void deleteReview(ReviewModel review) {
        LOGGER.debug("Delete review with id {}", review.getReviewId());
        em.remove(review);
    }

    @Override
    public void updateReview(Long reviewId, ReviewModel reviewModel) {
        LOGGER.debug("Updating review with id: {}", reviewId);
        em.merge(reviewModel);
    }
}
