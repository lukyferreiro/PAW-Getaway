package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;
import ar.edu.itba.getaway.persistence.ReviewDao;
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
    public ReviewModel create(String title, String description, long score, long experienceId, Date reviewDate, long userId) {
        LOGGER.debug("Creating review with title {}", title);
        ReviewModel reviewModel = reviewDao.create(title, description, score, experienceId, reviewDate, userId);
        LOGGER.debug("Created title with id {}", reviewModel.getReviewId());
        return reviewModel;
    }

    @Override
    public List<ReviewModel> getReviewsFromId(long experienceId) {
        LOGGER.debug("Retrieving all reviews of experience with id {}", experienceId);
        return reviewDao.getReviewsFromId(experienceId);
    }

    @Override
    public Long getAverageScore(long experienceId) {
        LOGGER.debug("Retrieving average score of experience with id {}", experienceId);
        return reviewDao.getAverageScore(experienceId);
    }

    @Override
    public Integer getReviewCount(long experienceId) {
        LOGGER.debug("Retrieving count of reviews of experience with id {}", experienceId);
        return reviewDao.getReviewCount(experienceId);
    }

//    @Override
//    Page<ReviewUserModel> getReviewAndUser(long experienceId, int page){
//      List<ReviewUserModel> reviewUserModelList=reviewDao.getReviewAndUser(experienceId,page,PAGE_SIZE_EXP);
//    return new Page<>(reviewUserModelList, page, getReviewCountExp(experienceId));
//      }

    @Override
    public List<ReviewUserModel> getReviewAndUser(long experienceId) {
        LOGGER.debug("Retrieving all reviews and user of experience with id {}", experienceId);
        return reviewDao.getReviewAndUser(experienceId);
    }

    @Override
    public Optional<ReviewModel> getById(long reviewId) {
        LOGGER.debug("Retrieving review with id {}", reviewId);
        return reviewDao.getById(reviewId);
    }

    //    @Override
//    Page<ReviewUserModel> getByUserId(long userId, int page){
//      List<ReviewUserModel> reviewUserModelList=reviewDao.getByUserId(experienceId,page,PAGE_SIZE_USER);
//    return new Page<>(reviewUserModelList, page, getReviewCountUser(userId));
//      }

    @Override
    public List<ReviewUserModel> getByUserId(long userId) {
        LOGGER.debug("Retrieving all reviews of user with id {}", userId);
        return reviewDao.getByUserId(userId);
    }

    @Override
    public boolean delete(long reviewId) {
        LOGGER.debug("Updating review with id {}", reviewId);
        if(reviewDao.delete(reviewId)){
            LOGGER.debug("Review {} updated", reviewId);
            return true;
        } else {
            LOGGER.warn("Review {} NOT updated", reviewId);
            return false;
        }
    }

    @Override
    public boolean update(long reviewId, ReviewModel reviewModel) {
        LOGGER.debug("Deleting review with id {}", reviewId);
        if(reviewDao.update(reviewId, reviewModel)){
            LOGGER.debug("Review {} deleted", reviewId);
            return true;
        } else {
            LOGGER.warn("Review {} NOT deleted", reviewId);
            return false;
        }
    }

}
