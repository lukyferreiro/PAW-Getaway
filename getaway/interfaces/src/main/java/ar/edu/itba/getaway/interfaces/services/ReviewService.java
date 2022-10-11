//package ar.edu.itba.getaway.interfaces.services;
//
//import ar.edu.itba.getaway.models.ExperienceModel;
//import ar.edu.itba.getaway.models.ReviewModel;
//import ar.edu.itba.getaway.models.ReviewUserModel;
//import ar.edu.itba.getaway.models.pagination.Page;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//public interface ReviewService {
//    ReviewModel createReview (String title, String description, Long score, Long experienceId, Date reviewDate, Long userId);
//    List<ReviewModel> getReviewsByExperienceId (Long experienceId);
//    Long getReviewAverageScore (Long experienceId);
//    List<Long> getListOfAverageScoreByExperienceList(List<ExperienceModel> experienceModelList);
//    List<List<Long>> getListOfAverageScoreByExperienceListAndCategoryId(List<List<ExperienceModel>> experienceModelList);
//    Integer getReviewCount (Long experienceId);
//    List<Integer> getListOfReviewCountByExperienceList(List<ExperienceModel> experienceModelList);
//    List<List<Integer>> getListOfReviewCountByExperienceListAndCategoryId(List<List<ExperienceModel>> experienceModelList);
//    List<Boolean> getListOfReviewHasImages(List<ReviewUserModel> reviewUserModelList);
//    Page<ReviewUserModel> getReviewAndUser (Long experienceId, Integer page);
//    Optional<ReviewModel> getReviewById (Long reviewId);
//    Page<ReviewUserModel> getReviewsByUserId (Long userId, Integer page);
//    void deleteReview (Long reviewId);
//    void updateReview (Long reviewId, ReviewModel reviewModel);
//    List<ExperienceModel> getListExperiencesOfReviewsList(List<ReviewUserModel> reviewModelList);
//
//}
