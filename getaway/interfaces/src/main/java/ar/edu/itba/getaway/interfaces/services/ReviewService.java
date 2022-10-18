package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    ReviewModel createReview(String title, String description, Long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel);
    List<ReviewModel> getReviewsByExperience(ExperienceModel experience);
    Long getReviewAverageScore(ExperienceModel experience);
    List<Long> getListOfAverageScoreByExperienceList(List<ExperienceModel> experienceModelList);
    List<List<Long>> getListOfAverageScoreByExperienceListAndCategoryId(List<List<ExperienceModel>> experienceModelList);
    Long getReviewCount(ExperienceModel experience);
    List<Long> getListOfReviewCountByExperienceList(List<ExperienceModel> experienceModelList);
    List<List<Long>> getListOfReviewCountByExperienceListAndCategoryId(List<List<ExperienceModel>> experienceModelList);
    List<Boolean> getListOfReviewHasImages(List<ReviewModel> reviewUserModelList);
    Page<ReviewModel> getReviewAndUser(ExperienceModel experience, Integer page);
    Optional<ReviewModel> getReviewById(Long reviewId);
    Page<ReviewModel> getReviewsByUser(UserModel user, Integer page);
    void deleteReview(ReviewModel review);
    void updateReview(Long reviewId, ReviewModel reviewModel);
    List<ExperienceModel> getListExperiencesOfReviewsList(List<ReviewModel> reviewModelList);
}
