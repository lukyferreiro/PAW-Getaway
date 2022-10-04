package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.ImageService;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;
import ar.edu.itba.getaway.interfaces.persistence.ReviewDao;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Long> getListOfAverageScoreByExperienceList(List<ExperienceModel> experienceModelList){
        final List<Long> avgReviews = new ArrayList<>();
        LOGGER.debug("Retrieving list of average score of all next's experiences");
        for (ExperienceModel experienceModel : experienceModelList) {
            LOGGER.debug("Added average score of experience with id {}", experienceModel.getExperienceId());
            avgReviews.add(reviewDao.getReviewAverageScore(experienceModel.getExperienceId()));
        }
        return avgReviews;
    }

    @Override
    public List<List<Long>> getListOfAverageScoreByExperienceListAndCategoryId(List<List<ExperienceModel>> experienceModelList) {
        final List<List<ExperienceModel>> listExperiencesByCategory = experienceService.getExperiencesListByCategories();
        final List<List<Long>> listExperiencesAvgReviewsByCategory = new ArrayList<>();
        for (int i = 0; i < listExperiencesByCategory.size(); i++) {
            listExperiencesAvgReviewsByCategory.add(new ArrayList<>());
            for (ExperienceModel experienceModel : listExperiencesByCategory.get(i)) {
                listExperiencesAvgReviewsByCategory.get(i).add(getReviewAverageScore(experienceModel.getExperienceId()));
            }
        }
        return listExperiencesAvgReviewsByCategory;
    }

    @Override
    public Integer getReviewCount(Long experienceId) {
        LOGGER.debug("Retrieving count of reviews of experience with id {}", experienceId);
        return reviewDao.getReviewCount(experienceId);
    }

    @Override
    public List<Integer> getListOfReviewCountByExperienceList(List<ExperienceModel> experienceModelList){
        final List<Integer> listReviewsCount = new ArrayList<>();
        LOGGER.debug("Retrieving list of review count of all next's experiences");
        for (ExperienceModel experienceModel : experienceModelList) {
            LOGGER.debug("Added review count of experience with id {}", experienceModel.getExperienceId());
            listReviewsCount.add(reviewDao.getReviewCount(experienceModel.getExperienceId()));
        }
        return listReviewsCount;
    }

    @Override
    public List<List<Integer>> getListOfReviewCountByExperienceListAndCategoryId(List<List<ExperienceModel>> experienceModelList) {
        final List<List<ExperienceModel>> listExperiencesByCategory = experienceService.getExperiencesListByCategories();
        final List<List<Integer>> listExperiencesReviewCountByCategory = new ArrayList<>();
        for (int i = 0; i < listExperiencesByCategory.size(); i++) {
            listExperiencesReviewCountByCategory.add(new ArrayList<>());
            for (ExperienceModel experienceModel : listExperiencesByCategory.get(i)) {
                listExperiencesReviewCountByCategory.get(i).add(getReviewCount(experienceModel.getExperienceId()));
            }
        }
        return listExperiencesReviewCountByCategory;
    }

    @Override
    public List<Boolean> getListOfReviewHasImages(List<ReviewUserModel> reviewUserModelList) {
        final List<Boolean> listReviewsHasImages = new ArrayList<>();
        LOGGER.debug("Retrieving list of whether has images of all next's reviews");
        for (ReviewUserModel review : reviewUserModelList) {
            LOGGER.debug("Added has image value of review with id {}", review.getImgId());
            final Optional<ImageModel> img = imageService.getImgById(review.getImgId());
            if (img.isPresent()) {
                listReviewsHasImages.add(img.get().getImage() != null);
            } else {
                listReviewsHasImages.add(false);
            }
        } return listReviewsHasImages;
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
