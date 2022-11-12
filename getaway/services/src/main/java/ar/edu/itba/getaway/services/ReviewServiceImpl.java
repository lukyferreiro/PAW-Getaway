package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.interfaces.services.EmailService;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.interfaces.persistence.ReviewDao;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private String appBaseUrl;

    private final Locale locale = LocaleContextHolder.getLocale();
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private static final int PAGE_SIZE = 6;
    private static final int USER_PAGE_SIZE = 6;

    @Transactional
    @Override
    public ReviewModel createReview(String title, String description, long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel) {
        LOGGER.debug("Creating review with title {}", title);
        final ReviewModel reviewModel = reviewDao.createReview(title, description, score, experienceModel, reviewDate, userModel);
        sendNewReviewEmail(reviewModel);
        return reviewModel;
    }

    @Transactional
    @Override
    public void updateReview(ReviewModel reviewModel) {
        LOGGER.debug("Updating review with id {}", reviewModel.getReviewId());
        reviewDao.updateReview(reviewModel);
    }

    @Transactional
    @Override
    public void deleteReview(ReviewModel review) {
        LOGGER.debug("Deleting review with id {}", review.getReviewId());
        reviewDao.deleteReview(review);
    }

    @Override
    public Page<ReviewModel> getReviewAndUser(ExperienceModel experience, int page) {
        LOGGER.debug("Retrieving all reviews and user of experience with id {}", experience.getExperienceId());
        LOGGER.debug("Requested page {}", page);
        int totalPages;
        List<ReviewModel> reviewUserModelList = new ArrayList<>();
        final long total = reviewDao.getReviewByExperienceCount(experience);

        if (total > 0) {
            LOGGER.debug("Total pages found: {}", total);

            totalPages = (int) Math.ceil((double) total / PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", totalPages);

            if (page > totalPages) {
                page = totalPages;
            } else if (page < 0) {
                page = 1;
            }
            reviewUserModelList = reviewDao.getReviewsByExperience(experience, page, PAGE_SIZE);
        } else {
            totalPages = 1;
        }

        LOGGER.debug("Max page value service: {}", totalPages);
        return new Page<>(reviewUserModelList, page, totalPages, total);
    }

    @Override
    public Optional<ReviewModel> getReviewById(long reviewId) {
        LOGGER.debug("Retrieving review with id {}", reviewId);
        return reviewDao.getReviewById(reviewId);
    }

    @Override
    public Page<ReviewModel> getReviewsByUser(UserModel user, int page) {
        LOGGER.debug("Retrieving all reviews of user with id {}", user.getUserId());
        LOGGER.debug("Requested page {}", page);

        int totalPages;
        List<ReviewModel> reviewUserModelList = new ArrayList<>();
        final long total = reviewDao.getReviewByUserCount(user);

        if (total > 0) {
            LOGGER.debug("Total reviews found: {}", total);

            totalPages = (int) Math.ceil((double) total / USER_PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", totalPages);

            if (page > totalPages) {
                page = totalPages;
            } else if (page < 0) {
                page = 1;
            }
            reviewUserModelList = reviewDao.getReviewsByUser(user, page, USER_PAGE_SIZE);
        } else {
            totalPages = 1;
        }

        LOGGER.debug("Max page value service: {}", totalPages);
        return new Page<>(reviewUserModelList, page, totalPages, total);
    }

    private void sendNewReviewEmail(ReviewModel reviewModel) {
        try {
            final String categoryName = reviewModel.getExperience().getCategory().getCategoryName();
            final long experienceId = reviewModel.getExperience().getExperienceId();
//            final String url = new URL("http", appBaseUrl, 8080, "/webapp_war/experiences/" + categoryName + '/' + experienceId).toString();
            final String url = new URL("http", appBaseUrl, "/paw-2022b-1/experiences/" + categoryName + '/' + experienceId).toString();
            final Map<String, Object> variables = new HashMap<>();
            variables.put("review", reviewModel);
            variables.put("myExperienceUrl", url);
            variables.put("to", reviewModel.getExperience().getUser().getEmail());
            emailService.sendMail("newReview", messageSource.getMessage("email.newReview", new Object[]{}, locale), variables, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, mail to verify account not sent");
        }
    }
}
