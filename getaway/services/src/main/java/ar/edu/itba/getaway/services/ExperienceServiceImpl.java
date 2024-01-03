package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.interfaces.exceptions.CategoryNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.DuplicateExperienceException;
import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.interfaces.services.*;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.interfaces.persistence.ExperienceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExperienceServiceImpl implements ExperienceService {
    @Autowired
    private ExperienceDao experienceDao;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private FavAndViewExperienceService favAndViewExperienceService;

    private static final int PAGE_SIZE = 6;
    private static final int RESULT_PAGE_SIZE = 9;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    private static final int CAROUSEL_LENGTH = 6;

    @Override
    @Transactional
    public ExperienceModel createExperience(String name, String address, String description, String email, String url, Double price, long cityLong, long categoryLong, UserModel user) throws DuplicateExperienceException {
        LOGGER.debug("Creating experience with name {}", name);
        final ImageModel experienceImage = imageService.createImg(null, null);
        final CityModel city = locationService.getCityById(cityLong).orElseThrow(CityNotFoundException::new);
        final CategoryModel category = categoryService.getCategoryById(categoryLong).orElseThrow(CategoryNotFoundException::new);

//        final ExperienceModel experienceModel = experienceDao.createExperience(name, address, description, email, url, price, city, category, user, experienceImage);

        ExperienceModel experienceModel;
        try {
            experienceModel = experienceDao.createExperience(name, address, description, email, url, price, city, category, user, experienceImage);
        } catch (DuplicateExperienceException e) {
            LOGGER.error("Error in createExperience, there is already an experience with this name, address and city");
            throw new DuplicateExperienceException();
        }

        if (!user.hasRole(Roles.PROVIDER.name())) {
            LOGGER.debug("User gains role provider when they create an experience for first time");
            userService.addRole(user, Roles.PROVIDER);
        }
        return experienceModel;
    }

    @Transactional
    @Override
    public void updateExperience(Long id, String name, String address, String description, String mail, String url, Double price, long cityLong, long categoryLong, UserModel user) {
        LOGGER.debug("Updating experience with id {}", id);

        final ExperienceModel experience = getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);
        final CityModel cityModel = locationService.getCityById(cityLong).orElseThrow(CityNotFoundException::new);
        final CategoryModel categoryModel = categoryService.getCategoryById(categoryLong).orElseThrow(CategoryNotFoundException::new);

        final ExperienceModel toUpdateExperience = new ExperienceModel(
                id, name, address, description,
                mail, url, price, cityModel,
                categoryModel, user, experience.getExperienceImage(), experience.getObservable(), experience.getViews());

        experienceDao.updateExperience(toUpdateExperience);
        LOGGER.debug("Experience {} updated", id);
    }

    @Transactional
    @Override
    public void updateExperienceWithoutImg(ExperienceModel toUpdateExperience) {
        LOGGER.debug("Updating experience with id {}", toUpdateExperience.getExperienceId());
        experienceDao.updateExperience(toUpdateExperience);
        LOGGER.debug("Experience {} updated", toUpdateExperience.getExperienceId());
    }

    @Transactional
    @Override
    public void deleteExperience(Long id) {
        LOGGER.debug("Deleting experience with id {}", id);
        final ExperienceModel experienceModel = getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);

        final ImageModel toDeleteImg = experienceModel.getExperienceImage();
        experienceDao.deleteExperience(experienceModel);
        imageService.deleteImg(toDeleteImg);
    }

    @Override
    public Optional<ExperienceModel> getExperienceById(long experienceId) {
        LOGGER.debug("Retrieving experience with id {}", experienceId);
        return experienceDao.getExperienceById(experienceId);
    }

    @Override
    public Optional<ExperienceModel> getVisibleExperienceById(long experienceId, UserModel user) {
        LOGGER.debug("Retrieving experience with id {}", experienceId);
        return experienceDao.getVisibleExperienceById(experienceId, user);
    }

    @Override
    public Page<ExperienceModel> listExperiencesByFilter(CategoryModel category, String name, Double max, Long score, CityModel city, OrderByModel order, int page, UserModel user) {
        int totalPages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();

        LOGGER.debug("Requested page {} ", page);
        final long total = experienceDao.countListByFilter(category, name, max, score, city);

        if (total > 0) {
            LOGGER.debug("Total experiences found: {}", total);

            totalPages = (int) Math.ceil((double) total / PAGE_SIZE);

            LOGGER.debug("Total pages calculated: {}", totalPages);

            if (page > totalPages) {
                page = totalPages;
            } else if (page < 0) {
                page = 1;
            }

            experienceModelList = experienceDao.listExperiencesByFilter(category, name, max, score, city, order, page, PAGE_SIZE);
        } else {
            totalPages = 1;
        }

        LOGGER.debug("Max page value service: {}", totalPages);
        return new Page<>(experienceModelList, page, totalPages, total);
    }

    @Override
    public Optional<Double> getMaxPriceByCategoryAndName(CategoryModel categoryModel, String name) {
        return experienceDao.getMaxPriceByCategoryAndName(categoryModel, name);
    }

    @Override
    public Page<ExperienceModel> listExperiencesByBestRanked(CategoryModel category, UserModel user) {
        LOGGER.debug("Retrieving all experiences by best ranked of category with id {}", category.getCategoryId());

        final List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByBestRanked(category, CAROUSEL_LENGTH);

        return new Page<>(experienceModelList, 1, 1, CAROUSEL_LENGTH);
    }

    @Override
    public Page<ExperienceModel> listExperiencesFavsByUser(UserModel user, OrderByModel order, int page) {
        LOGGER.debug("Requested page {}", page);
        int totalPages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();
        final long total = experienceDao.getCountListExperiencesFavsByUser(user);

        if (total > 0) {
            LOGGER.debug("Total pages found: {}", total);

            totalPages = (int) Math.ceil((double) total / RESULT_PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", totalPages);

            if (page > totalPages) {
                page = totalPages;
            } else if (page < 0) {
                page = 1;
            }
            experienceModelList = experienceDao.listExperiencesFavsByUser(user, order, page, RESULT_PAGE_SIZE);
        } else {
            totalPages = 1;
        }

        LOGGER.debug("Max page value service: {}", totalPages);
        return new Page<>(experienceModelList, page, totalPages, total);
    }



//    @Override
//    public List<List<ExperienceModel>> getExperiencesListByCategories(UserModel user) {
//        final List<List<ExperienceModel>> listExperiencesByCategory = new ArrayList<>();
//        LOGGER.debug("Retrieving all experiences listed by categories");
//        final List<CategoryModel> categories = categoryService.listAllCategories();
//
//        for (CategoryModel category : categories) {
//            listExperiencesByCategory.add(listExperiencesByBestRanked(category, user));
//        }
//        return listExperiencesByCategory;
//    }

    @Override
    public boolean experienceBelongsToUser(UserModel user, ExperienceModel experience) {
        return experience.getUser().equals(user);
    }

    @Override
    public Page<ExperienceModel> listExperiencesSearchByUser(String name, UserModel user, OrderByModel order, int page) {
        LOGGER.debug("Requested page {}", page);

        int totalPages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();
        final long total = experienceDao.getCountExperiencesByUser(name, user);

        if (total > 0) {
            LOGGER.debug("Total pages found: {}", total);

            totalPages = (int) Math.ceil((double) total / RESULT_PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", totalPages);

            if (page > totalPages) {
                page = totalPages;
            } else if (page < 0) {
                page = 1;
            }
            experienceModelList = experienceDao.listExperiencesSearchByUser(name, user, order, page, RESULT_PAGE_SIZE);

        } else {
            totalPages = 1;
        }

        LOGGER.debug("Max page value service: {}", totalPages);
        return new Page<>(experienceModelList, page, totalPages, total);
    }

    @Transactional
    @Override
    public ExperienceModel getExperienceAndIncreaseViews(UserModel user, boolean view, long id) {
        final ExperienceModel experience = getVisibleExperienceById(id, user).orElseThrow(ExperienceNotFoundException::new);

        if (view && user != null && !experience.getUser().equals(user)) {
            experience.increaseViews();
            updateExperienceWithoutImg(experience);
        }

        favAndViewExperienceService.setViewed(user, view, experience);

        return experience;
    }

    @Transactional
    @Override
    public void changeVisibility(Long experienceId, UserModel user, boolean obs) {
        final ExperienceModel experience = getVisibleExperienceById(experienceId, user).orElseThrow(ExperienceNotFoundException::new);
        experience.setObservable(obs);
        updateExperienceWithoutImg(experience);
    }

//    private List<ExperienceModel> getViewedExperiences(UserModel user) {
//        List<ExperienceModel> auxList = new ArrayList<>();
//        List<ExperienceModel> userViews = user.getViewedExperiences();
//        for (ExperienceModel exp : userViews) {
//            if (exp.getObservable()) {
//                auxList.add(exp);
//            }
//        }
//        int toIndex = auxList.size();
//        int fromIndex = Math.max((toIndex - CAROUSEL_LENGTH), 0);
//        return auxList.subList(fromIndex, toIndex);
//    }

//    @Override
//    public List<List<ExperienceModel>> userLandingPage(UserModel user) {
//        final List<List<ExperienceModel>> listExperiencesByCategory = new ArrayList<>();
//        List<ExperienceModel> viewedExperiences = getViewedExperiences(user);
//
//        //Adding viewed experiences to user
//        for (ExperienceModel experience : viewedExperiences) {
//            experience.setIsFav(user.isFav(experience));
//        }
//        listExperiencesByCategory.add(viewedExperiences);
//
//        //Getting recommendedByFavs
//        //If we dont have enough recommendations we switch to recommended by views, and then plainly by the best ranked in general
//        //If an experience was already added we skip it to avoid duplicates
//        List<ExperienceModel> recommendedByFavsFullList = experienceDao.getRecommendedByFavs(user, CAROUSEL_LENGTH);
//        List<Long> alreadyRecommended = recommendedByFavsFullList.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList());
//        List<ExperienceModel> recommendedByViews;
//        List<ExperienceModel> bestRanked;
//        if (recommendedByFavsFullList.size() < CAROUSEL_LENGTH) {
//            recommendedByViews = experienceDao.getRecommendedByViews(user, CAROUSEL_LENGTH - recommendedByFavsFullList.size(), alreadyRecommended);
//            alreadyRecommended.addAll(recommendedByViews.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
//            recommendedByFavsFullList.addAll(recommendedByViews);
//        }
//        if (recommendedByFavsFullList.size() < CAROUSEL_LENGTH) {
//            bestRanked = experienceDao.getRecommendedBestRanked(CAROUSEL_LENGTH - recommendedByFavsFullList.size(), recommendedByFavsFullList.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
//            alreadyRecommended.addAll(bestRanked.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
//            recommendedByFavsFullList.addAll(bestRanked);
//        }
//
//        //Adding recommendedByFavs to general list
//        for (ExperienceModel experience : recommendedByFavsFullList) {
//            experience.setIsFav(user.isFav(experience));
//        }
//        listExperiencesByCategory.add(recommendedByFavsFullList);
//
//        //Check if user hasReviews to get recommendations
//        if (user.hasReviews()) {
//            //Getting recommendedByReviews
//            //Firstly, we look at the city where the user has made the most reviews
//            //Secondly, we look at the provider to whom the user has made the most reviews
//            //Lastly, we look at the category which the user has made the most reviews
//            //If an experience was already added we skip it to avoid duplicates
//            List<Long> userReviewedIds = experienceDao.reviewedExperiencesId(user);
//            List<ExperienceModel> recommendedByReviewsFullList;
//            List<ExperienceModel> recommendedByReviewsProvider;
//            List<ExperienceModel> recommendedByReviewsCategory;
//            recommendedByReviewsFullList = experienceDao.getRecommendedByReviewsCity(user, CAROUSEL_LENGTH, alreadyRecommended, userReviewedIds);
//            alreadyRecommended.addAll(recommendedByReviewsFullList.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
//
//            if (recommendedByReviewsFullList.size() < CAROUSEL_LENGTH) {
//                recommendedByReviewsProvider = experienceDao.getRecommendedByReviewsProvider(user, CAROUSEL_LENGTH - recommendedByReviewsFullList.size(), alreadyRecommended, userReviewedIds);
//                recommendedByReviewsFullList.addAll(recommendedByReviewsProvider);
//                alreadyRecommended.addAll(recommendedByReviewsProvider.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
//            }
//
//            if (recommendedByReviewsFullList.size() < CAROUSEL_LENGTH) {
//                recommendedByReviewsCategory = experienceDao.getRecommendedByReviewsCategory(user, CAROUSEL_LENGTH - recommendedByReviewsFullList.size(), alreadyRecommended, userReviewedIds);
//                recommendedByReviewsFullList.addAll(recommendedByReviewsCategory);
//            }
//
//            for (ExperienceModel experience : recommendedByReviewsFullList) {
//                experience.setIsFav(user.isFav(experience));
//            }
//            listExperiencesByCategory.add(recommendedByReviewsFullList);
//            return listExperiencesByCategory;
//        }
//
//        listExperiencesByCategory.add(Collections.emptyList());
//        return listExperiencesByCategory;
//    }

    @Override
    public Page<ExperienceModel> getViewedExperiences(UserModel user){
        List<ExperienceModel> auxList = new ArrayList<>();
        List<ExperienceModel> userViews = user.getViewedExperiences();

        if (userViews.isEmpty()) {
            return new Page<>(Collections.emptyList(), 1, 1, 0);
        }

        for (ExperienceModel exp : userViews) {
            if (exp.getObservable()) {
                auxList.add(exp);
            }
        }

        int toIndex = auxList.size();
        int fromIndex = Math.max((toIndex - CAROUSEL_LENGTH), 0);
        return new Page<>(auxList.subList(fromIndex, toIndex), 1, 1, CAROUSEL_LENGTH) ;
    }

    @Override
    public Page<ExperienceModel> getRecommendedByFavs(UserModel user){
        List<ExperienceModel> recommendedByFavsFullList = experienceDao.getRecommendedByFavs(user, CAROUSEL_LENGTH);
        List<ExperienceModel> toReturnList = new ArrayList<>(recommendedByFavsFullList);

        List<Long> alreadyRecommended = recommendedByFavsFullList.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList());
        List<ExperienceModel> recommendedByViews;
        List<ExperienceModel> bestRanked;
        if (recommendedByFavsFullList.size() < CAROUSEL_LENGTH) {
            recommendedByViews = experienceDao.getRecommendedByViews(user, CAROUSEL_LENGTH - recommendedByFavsFullList.size(), alreadyRecommended);
            alreadyRecommended.addAll(recommendedByViews.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
            toReturnList.addAll(recommendedByViews);
        }
        if (recommendedByFavsFullList.size() < CAROUSEL_LENGTH) {
            bestRanked = experienceDao.getRecommendedBestRanked(CAROUSEL_LENGTH - recommendedByFavsFullList.size(), recommendedByFavsFullList.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
            alreadyRecommended.addAll(bestRanked.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
            toReturnList.addAll(bestRanked);
        }

        return new Page<>(toReturnList, 1, 1, CAROUSEL_LENGTH);
    }


    public Page<ExperienceModel> getRecommendedByReviews(UserModel user){
        if (user.hasReviews()) {
            //Getting recommendedByReviews
            //Firstly, we look at the city where the user has made the most reviews
            //Secondly, we look at the provider to whom the user has made the most reviews
            //Lastly, we look at the category which the user has made the most reviews
            //If an experience was already added we skip it to avoid duplicates
            List<Long> alreadyRecommended = experienceDao.reviewedExperiencesId(user);

            List<ExperienceModel> recommendedByReviewsCity;
            List<ExperienceModel> recommendedByReviewsProvider;
            List<ExperienceModel> recommendedByReviewsCategory;
            recommendedByReviewsCity = experienceDao.getRecommendedByReviewsCity(user, CAROUSEL_LENGTH, alreadyRecommended);
            List<ExperienceModel> toReturnList = new ArrayList<>(recommendedByReviewsCity);

            alreadyRecommended.addAll(recommendedByReviewsCity.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));

            if (toReturnList.size() < CAROUSEL_LENGTH) {
                recommendedByReviewsProvider = experienceDao.getRecommendedByReviewsProvider(user, CAROUSEL_LENGTH - toReturnList.size(), alreadyRecommended);
                toReturnList.addAll(recommendedByReviewsProvider);
                alreadyRecommended.addAll(recommendedByReviewsProvider.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
            }

            if (toReturnList.size() < CAROUSEL_LENGTH) {
                recommendedByReviewsCategory = experienceDao.getRecommendedByReviewsCategory(user, CAROUSEL_LENGTH - toReturnList.size(), alreadyRecommended);
                toReturnList.addAll(recommendedByReviewsCategory);
            }

            return new Page<>(toReturnList, 1, 1, CAROUSEL_LENGTH);
        }
        return new Page<>(Collections.emptyList(), 1, 1, 0);

    }

}
