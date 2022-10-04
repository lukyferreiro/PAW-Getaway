package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;
import ar.edu.itba.getaway.interfaces.persistence.ReviewDao;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Override
    public ReviewModel createReview(String title, String description, Long score, Long experienceId, Date reviewDate, Long userId) {
        LOGGER.debug("Creating review with title {}", title);
        return reviewDao.createReview(title, description, score, experienceId, reviewDate, userId);
    }

    @Override
    public List<ReviewModel> getReviewsByExperienceId(Long experienceId) {
        LOGGER.debug("Retrieving all reviews of experience with id {}", experienceId);
        return reviewDao.getReviewsByExperienceId(experienceId);
    }

    @Override
    public Long getReviewAverageScore(Long experienceId) {
        LOGGER.debug("Retrieving average score of experience with id {}", experienceId);
        return reviewDao.getReviewAverageScore(experienceId);
    }

    @Override
    public Integer getReviewCount(Long experienceId) {
        LOGGER.debug("Retrieving count of reviews of experience with id {}", experienceId);
        return reviewDao.getReviewCount(experienceId);
    }

    @Override
    public List<ReviewUserModel> getReviewAndUser(Long experienceId) {
        LOGGER.debug("Retrieving all reviews and user of experience with id {}", experienceId);
        return reviewDao.getReviewAndUser(experienceId);
    }

    @Override
    public Optional<ReviewModel> getReviewById(Long reviewId) {
        LOGGER.debug("Retrieving review with id {}", reviewId);
        return reviewDao.getReviewById(reviewId);
    }

    @Override
    public List<ReviewUserModel> getReviewsByUserId(Long userId) {
        LOGGER.debug("Retrieving all reviews of user with id {}", userId);
        return reviewDao.getReviewsByUserId(userId);
    }

    @Override
    public void deleteReview(Long reviewId) {
        LOGGER.debug("Updating review with id {}", reviewId);
        if(reviewDao.deleteReview(reviewId)){
            LOGGER.debug("Review {} updated", reviewId);
        } else {
            LOGGER.warn("Review {} NOT updated", reviewId);
        }
    }

    @Override
    public void updateReview(Long reviewId, ReviewModel reviewModel) {
        LOGGER.debug("Deleting review with id {}", reviewId);
        if(reviewDao.updateReview(reviewId, reviewModel)){
            LOGGER.debug("Review {} deleted", reviewId);
        } else {
            LOGGER.warn("Review {} NOT deleted", reviewId);
        }
    }

}
