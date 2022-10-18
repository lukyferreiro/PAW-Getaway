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

    @Override
    public ReviewModel createReview(String title, String description, Long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel) {
        LOGGER.debug("Creating review with title {}", title);
        return reviewDao.createReview(title, description, score, experienceModel, reviewDate, userModel);
    }

    @Override
    public List<ReviewModel> getReviewsByExperience(ExperienceModel experience) {
        LOGGER.debug("Retrieving all reviews of experience with id {}", experience.getExperienceId());
        return reviewDao.getReviewsByExperience(experience);
    }

    @Override
    public Long getReviewAverageScore(ExperienceModel experience) {
        LOGGER.debug("Retrieving average score of experience with id {}", experience.getExperienceId());
        return reviewDao.getReviewAverageScore(experience);
    }

    @Override
    public List<Long> getListOfAverageScoreByExperienceList(List<ExperienceModel> experienceModelList) {
        final List<Long> avgReviews = new ArrayList<>();
        LOGGER.debug("Retrieving list of average score of all next's experiences");
        for (ExperienceModel experienceModel : experienceModelList) {
            LOGGER.debug("Added average score of experience with id {}", experienceModel.getExperienceId());
            avgReviews.add(reviewDao.getReviewAverageScore(experienceModel));
        }
        return avgReviews;
    }

    @Override
    public List<List<Long>> getListOfAverageScoreByExperienceListAndCategoryId(List<List<ExperienceModel>> experienceModelList) {
        final List<List<Long>> listExperiencesAvgReviewsByCategory = new ArrayList<>();
        for (int i = 0; i < experienceModelList.size(); i++) {
            listExperiencesAvgReviewsByCategory.add(new ArrayList<>());
            for (ExperienceModel experienceModel : experienceModelList.get(i)) {
                listExperiencesAvgReviewsByCategory.get(i).add(getReviewAverageScore(experienceModel));
            }
        }
        return listExperiencesAvgReviewsByCategory;
    }

    @Override
    public Long getReviewCount(ExperienceModel experience) {
        LOGGER.debug("Retrieving count of reviews of experience with id {}", experience.getExperienceId());
        return reviewDao.getReviewCount(experience);
    }

    @Override
    public List<Long> getListOfReviewCountByExperienceList(List<ExperienceModel> experienceModelList) {
        final List<Long> listReviewsCount = new ArrayList<>();
        LOGGER.debug("Retrieving list of review count of all next's experiences");
        for (ExperienceModel experienceModel : experienceModelList) {
            LOGGER.debug("Added review count of experience with id {}", experienceModel.getExperienceId());
            listReviewsCount.add(reviewDao.getReviewCount(experienceModel));
        }
        return listReviewsCount;
    }

    @Override
    public List<List<Long>> getListOfReviewCountByExperienceListAndCategoryId(List<List<ExperienceModel>> experienceModelList) {
        final List<List<Long>> listExperiencesReviewCountByCategory = new ArrayList<>();
        for (int i = 0; i < experienceModelList.size(); i++) {
            listExperiencesReviewCountByCategory.add(new ArrayList<>());
            for (ExperienceModel experienceModel : experienceModelList.get(i)) {
                listExperiencesReviewCountByCategory.get(i).add(getReviewCount(experienceModel));
            }
        }
        return listExperiencesReviewCountByCategory;
    }

    @Override
    public List<Boolean> getListOfReviewHasImages(List<ReviewModel> reviewUserModelList) {
        final List<Boolean> listReviewsHasImages = new ArrayList<>();
        LOGGER.debug("Retrieving list of whether has images of all next's reviews");
        for (ReviewModel review : reviewUserModelList) {
            LOGGER.debug("Added has image value of review with id {}", review.getUser().getProfileImage());
            final Optional<ImageModel> img = imageService.getImgById(review.getUser().getProfileImage().getImageId());
            if (img.isPresent()) {
                listReviewsHasImages.add(img.get().getImage() != null);
            } else {
                listReviewsHasImages.add(false);
            }
        }
        return listReviewsHasImages;
    }

    @Override
    public Page<ReviewModel> getReviewAndUser(ExperienceModel experience, Integer page) {
        LOGGER.debug("Retrieving all reviews and user of experience with id {}", experience.getExperienceId());
        int total_pages;
        List<ReviewModel> reviewUserModelList = new ArrayList<>();

        LOGGER.debug("Requested page {}", page);

        Long total = reviewDao.getReviewCount(experience);

        if (total > 0) {
            LOGGER.debug("Total pages found: {}", total);

            total_pages = (int) Math.ceil((double) total / PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", total_pages);

            if (page > total_pages) {
                page = total_pages;
            } else if (page < 0) {
                page = 1;
            }
            reviewUserModelList = reviewDao.getReviewAndUser(experience, page, PAGE_SIZE);
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
            LOGGER.debug("Total pages found: {}", total);

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

    @Transactional
    @Override
    public void deleteReview(ReviewModel review) {
        LOGGER.debug("Deleting review with id {}", review.getReviewId());
//        if(reviewDao.deleteReview(review)){
//            LOGGER.debug("Review {} updated", reviewId);
//        } else {
//            LOGGER.warn("Review {} NOT updated", reviewId);
        //   }
        reviewDao.deleteReview(review);
    }

    @Transactional
    @Override
    public void updateReview(Long reviewId, ReviewModel reviewModel) {
        LOGGER.debug("Updating review with id {}", reviewId);
//        if(reviewDao.updateReview(reviewId, reviewModel)){
//            LOGGER.debug("Review {} deleted", reviewId);
//        } else {
//            LOGGER.warn("Review {} NOT deleted", reviewId);
//        }
        reviewDao.updateReview(reviewId, reviewModel);
    }

    //TODO: delete unnecessary method.
    // ReviewModel now internally has its experienceMode
    @Override
    public List<ExperienceModel> getListExperiencesOfReviewsList(List<ReviewModel> reviewModelList) {
        final List<ExperienceModel> listExperiencesOfReviews = new ArrayList<>();
        for (ReviewModel review : reviewModelList) {
            final Optional<ExperienceModel> experienceModel = experienceService.getExperienceById(review.getExperience().getExperienceId());
            experienceModel.ifPresent(listExperiencesOfReviews::add);
        }
        return listExperiencesOfReviews;
    }

}
