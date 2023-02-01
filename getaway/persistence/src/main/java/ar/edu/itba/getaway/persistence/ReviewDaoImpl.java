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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    @PersistenceContext
    private EntityManager em;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDaoImpl.class);

    @Override
    public ReviewModel createReview(String title, String description, long score, ExperienceModel experienceModel, LocalDate reviewDate, UserModel userModel) {
        final ReviewModel reviewModel = new ReviewModel(title, description, score, experienceModel, reviewDate, userModel);
        em.persist(reviewModel);
        LOGGER.debug("Created new review with id {} by user with id {}", reviewModel.getReviewId(), userModel.getUserId());
        return reviewModel;
    }

    @Override
    public void updateReview(ReviewModel reviewModel) {
        LOGGER.debug("Updating review with id: {}", reviewModel.getReviewId());
        em.merge(reviewModel);
    }

    @Override
    public void deleteReview(ReviewModel review) {
        LOGGER.debug("Delete review with id {}", review.getReviewId());
        em.remove(review);
    }

    @Override
    public Optional<ReviewModel> getReviewById(long reviewId) {
        LOGGER.debug("Get review with id {}", reviewId);
        return Optional.ofNullable(em.find(ReviewModel.class, reviewId));
    }

    @Override
    public List<ReviewModel> getReviewsByUser(UserModel user, int page, int pageSize) {
        LOGGER.debug("List reviews of user with id {}", user.getUserId());

        Query queryForIds = em.createNativeQuery(
                "SELECT reviewId \n" +
                    "FROM reviews JOIN experiences ON experiences.experienceid = reviews.experienceid\n " +
                    "WHERE observable = true AND reviews.userId = :userId"
        );

        queryForIds.setParameter("userId", user.getUserId());

        List<Number> resultingIds = (List<Number>) queryForIds.getResultList();

        List<Long> idList = resultingIds.stream().map(Number::longValue).collect(Collectors.toList());
        final TypedQuery<ReviewModel> queryForReviews;
        if (idList.size() > 0) {
            LOGGER.debug("Selecting experiences contained in ");
            queryForReviews = em.createQuery("SELECT rev FROM ReviewModel rev WHERE rev.reviewId IN (:idList) ", ReviewModel.class);
            queryForReviews.setParameter("idList", idList);
            queryForReviews.setMaxResults(pageSize);
            queryForReviews.setFirstResult((page - 1) * pageSize);
            return queryForReviews.getResultList();
        }

        LOGGER.debug("User with id {} has no reviews yet", user.getUserId());
        return new ArrayList<>();
    }

    @Override
    public long getReviewByUserCount(UserModel user) {
        Query query = em.createNativeQuery(
                "SELECT COUNT(reviewid) \n" +
                        "FROM reviews JOIN experiences ON experiences.experienceid = reviews.experienceid\n" +
                        "WHERE observable = true AND reviews.userid = :userId"
        );

        query.setParameter("userId", user.getUserId());
        return ((BigInteger) query.getSingleResult()).intValue();
    }

    @Override
    public List<ReviewModel> getReviewsByExperience(ExperienceModel experience, int page, int pageSize) {
            LOGGER.debug("List reviews of experience with id {}", experience.getExperienceId());

            Query queryForIds = em.createNativeQuery(
                    "SELECT reviewId \n" +
                            "FROM reviews JOIN experiences ON experiences.experienceid = reviews.experienceid\n " +
                            "WHERE observable = true AND reviews.experienceId = :experienceId"
            );

            queryForIds.setParameter("experienceId", experience.getExperienceId());

            List<Number> resultingIds = (List<Number>) queryForIds.getResultList();

            List<Long> idList = resultingIds.stream().map(Number::longValue).collect(Collectors.toList());
            final TypedQuery<ReviewModel> queryForReviews;
            if (idList.size() > 0) {
                LOGGER.debug("Selecting reviews contained in ");
                queryForReviews = em.createQuery("SELECT  rev FROM ReviewModel rev WHERE rev.reviewId IN (:idList) ", ReviewModel.class);
                queryForReviews.setParameter("idList", idList);
                queryForReviews.setMaxResults(pageSize);
                queryForReviews.setFirstResult((page - 1) * pageSize);
                return queryForReviews.getResultList();
            }

            LOGGER.debug("Experience with id {} has no reviews yet", experience.getExperienceId());
            return new ArrayList<>();
    }

    @Override
    public long getReviewByExperienceCount(ExperienceModel experience) {
        Query query = em.createNativeQuery(
                "SELECT COUNT(reviewId) \n" +
                        "FROM reviews JOIN experiences ON experiences.experienceid = reviews.experienceid\n " +
                        "WHERE observable = true AND reviews.experienceId = :experienceId"
        );

        query.setParameter("experienceId", experience.getExperienceId());
        return ((BigInteger) query.getSingleResult()).intValue();
    }
}
