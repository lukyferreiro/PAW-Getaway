package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewService {


    ReviewModel createReview(String title, String description, Long score, ExperienceModel experienceModel, Date reviewDate, UserModel userModel);

    List<ReviewModel> getReviewsByExperienceId(ExperienceModel experience);

    Long getReviewAverageScore(ExperienceModel experience);

    List<Long> getListOfAverageScoreByExperienceList(List<ExperienceModel> experienceModelList);

    List<List<Long>> getListOfAverageScoreByExperienceListAndCategoryId(List<List<ExperienceModel>> experienceModelList);

    Integer getReviewCount(ExperienceModel experience);

    List<Integer> getListOfReviewCountByExperienceList(List<ExperienceModel> experienceModelList);

    List<List<Integer>> getListOfReviewCountByExperienceListAndCategoryId(List<List<ExperienceModel>> experienceModelList);

    List<Boolean> getListOfReviewHasImages(List<ReviewUserModel> reviewUserModelList);

    Page<ReviewUserModel> getReviewAndUser(Long experienceId, Integer page);

    Optional<ReviewModel> getReviewById(Long reviewId);

    Page<ReviewUserModel> getReviewsByUserId(Long userId, Integer page);

    void deleteReview(ReviewModel review);

    void updateReview(Long reviewId, ReviewModel reviewModel);

    List<ExperienceModel> getListExperiencesOfReviewsList(List<ReviewUserModel> reviewModelList);

}
