package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.interfaces.persistence.ReviewDao;
import ar.edu.itba.getaway.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    @PersistenceContext
    private EntityManager em;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDaoImpl.class);

    @Override
    public ReviewModel createReview(String title, String description, Long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel) {
        final ReviewModel reviewModel = new ReviewModel(title, description, score, experienceModel, reviewDate, userModel);
        em.persist(reviewModel);
        LOGGER.debug("Created new review with id {} by user with id {}", reviewModel.getReviewId(), userModel.getUserId());
        return reviewModel;
    }

    @Override
    public void updateReview(Long reviewId, ReviewModel reviewModel) {
        LOGGER.debug("Updating review with id: {}", reviewId);
        em.merge(reviewModel);
    }

    @Override
    public void deleteReview(ReviewModel review) {
        LOGGER.debug("Delete review with id {}", review.getReviewId());
        em.remove(review);
    }

    @Override
    public Optional<ReviewModel> getReviewById(Long reviewId) {
        LOGGER.debug("Get review with id {}", reviewId);
        return Optional.ofNullable(em.find(ReviewModel.class, reviewId));
    }

    @Override
    public List<ReviewModel> getReviewsByUser(UserModel user, Integer page, Integer page_size) {
        final TypedQuery<ReviewModel> query = em.createQuery("FROM ReviewModel WHERE user = :user", ReviewModel.class);
        query.setParameter("user", user);
        query.setFirstResult((page - 1) * page_size);
        query.setMaxResults(page_size);
        return query.getResultList();
    }

    @Override
    public Long getReviewByUserCount(UserModel user) {
        final TypedQuery<Long> query = em.createQuery("SELECT COUNT(r.user) FROM ReviewModel r WHERE r.user = :user", Long.class);
        query.setParameter("user", user);
        return query.getSingleResult();
    }

    @Override
    public List<ReviewModel> getReviewsByExperience(ExperienceModel experience, Integer page, Integer page_size) {
        final TypedQuery<ReviewModel> query = em.createQuery("FROM ReviewModel WHERE experience = :experience", ReviewModel.class);
        query.setParameter("experience", experience);
        query.setFirstResult((page - 1) * page_size);
        query.setMaxResults(page_size);
        return query.getResultList();
    }

    @Override
    public Long getReviewByExperienceCount(ExperienceModel experience) {
        final TypedQuery<Long> query = em.createQuery("SELECT COUNT(r.experience) FROM ReviewModel r WHERE r.experience = :experience", Long.class);
        query.setParameter("experience", experience);
        return query.getSingleResult();
    }
}
