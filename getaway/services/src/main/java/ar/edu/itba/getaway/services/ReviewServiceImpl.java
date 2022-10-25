package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.ImageService;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.interfaces.persistence.ReviewDao;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ExperienceService experienceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private static final Integer PAGE_SIZE = 6;
    private static final Integer USER_PAGE_SIZE = 12;

    @Transactional
    @Override
    public ReviewModel createReview(String title, String description, Long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel) {
        LOGGER.debug("Creating review with title {}", title);
        return reviewDao.createReview(title, description, score, experienceModel, reviewDate, userModel);
    }

    @Transactional
    @Override
    public void updateReview(Long reviewId, ReviewModel reviewModel) {
        LOGGER.debug("Updating review with id {}", reviewId);
        reviewDao.updateReview(reviewId, reviewModel);
    }

    @Transactional
    @Override
    public void deleteReview(ReviewModel review) {
        LOGGER.debug("Deleting review with id {}", review.getReviewId());
        reviewDao.deleteReview(review);
    }

    @Override
    public Page<ReviewModel> getReviewAndUser(ExperienceModel experience, Integer page) {
        LOGGER.debug("Retrieving all reviews and user of experience with id {}", experience.getExperienceId());
        int total_pages;
        List<ReviewModel> reviewUserModelList = new ArrayList<>();

        LOGGER.debug("Requested page {}", page);

        Long total = reviewDao.getReviewByExperienceCount(experience);
//        Long total = experience.getReviewCount();

        if (total > 0) {
            LOGGER.debug("Total pages found: {}", total);

            total_pages = (int) Math.ceil((double) total / PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", total_pages);

            if (page > total_pages) {
                page = total_pages;
            } else if (page < 0) {
                page = 1;
            }
//            reviewUserModelList = experience.getExperienceReviews(page, PAGE_SIZE);
            reviewUserModelList = reviewDao.getReviewsByExperience(experience, page, PAGE_SIZE);
        } else {
            total_pages = 1;
        }

        LOGGER.debug("Max page value service: {}", total_pages);
        return new Page<>(reviewUserModelList, page, total_pages);
    }

    @Override
    public Optional<ReviewModel> getReviewById(Long reviewId) {
        LOGGER.debug("Retrieving review with id {}", reviewId);
        return reviewDao.getReviewById(reviewId);
    }

    @Override
    public Page<ReviewModel> getReviewsByUser(UserModel user, Integer page) {
        LOGGER.debug("Retrieving all reviews of user with id {}", user.getUserId());
        int total_pages;
        List<ReviewModel> reviewUserModelList = new ArrayList<>();

        LOGGER.debug("Requested page {}", page);

        Long total = reviewDao.getReviewByUserCount(user);

        if (total > 0) {
            LOGGER.debug("Total reviews found: {}", total);

            total_pages = (int) Math.ceil((double) total / USER_PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", total_pages);

            if (page > total_pages) {
                page = total_pages;
            } else if (page < 0) {
                page = 1;
            }
            reviewUserModelList = reviewDao.getReviewsByUser(user, page, USER_PAGE_SIZE);
        } else {
            total_pages = 1;
        }

        LOGGER.debug("Max page value service: {}", total_pages);
        return new Page<>(reviewUserModelList, page, total_pages);
    }
}
