package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.interfaces.services.CategoryService;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.interfaces.persistence.ExperienceDao;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.ImageService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
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

    private static final int PAGE_SIZE = 6;
    private static final int RESULT_PAGE_SIZE = 9;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    private static final int CAROUSEL_LENGTH = 6;

    @Override
    @Transactional
    public ExperienceModel createExperience(String name, String address, String description, String email, String url, Double price, CityModel city, CategoryModel category, UserModel user, byte[] image) {
        LOGGER.debug("Creating experience with name {}", name);
        final ImageModel experienceImage = imageService.createImg(image);
        final ExperienceModel experienceModel = experienceDao.createExperience(name, address, description, email, url, price, city, category, user, experienceImage);
        if (!user.hasRole(Roles.PROVIDER.name())) {
            LOGGER.debug("User gains role provider when they creates an experience for first time");
            userService.addRole(user, Roles.PROVIDER);
        }
        return experienceModel;
    }

    @Transactional
    @Override
    public void updateExperience(ExperienceModel experienceModel, byte[] image) {
        LOGGER.debug("Updating experience with id {}", experienceModel.getExperienceId());
        experienceDao.updateExperience(experienceModel);
        imageService.updateImg(image, experienceModel.getExperienceImage());
        LOGGER.debug("Experience {} updated", experienceModel.getExperienceId());
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
    public void deleteExperience(ExperienceModel experienceModel) {
        LOGGER.debug("Deleting experience with id {}", experienceModel.getExperienceId());
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
        Optional<ExperienceModel> maybeExperience = experienceDao.getVisibleExperienceById(experienceId);
        maybeExperience.ifPresent(experienceModel -> experienceModel.setIsFav(user != null && user.isFav(experienceModel)));
        return maybeExperience;
    }

    @Override
    public Page<ExperienceModel> listExperiencesByFilter(CategoryModel category, Double max, Long score, CityModel city, Optional<OrderByModel> order, int page, UserModel user) {
        int totalPages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();

        LOGGER.debug("Requested page {} ", page);
        final long total = experienceDao.countListByFilter(category, max, score, city);
        if (total > 0) {
            LOGGER.debug("Total experiences found: {}", total);

            totalPages = (int) Math.ceil((double) total / PAGE_SIZE);

            LOGGER.debug("Total pages calculated: {}", totalPages);

            if (page > totalPages) {
                page = totalPages;
            } else if (page < 0) {
                page = 1;
            }

            experienceModelList = experienceDao.listExperiencesByFilter(category, max, score, city, order, page, PAGE_SIZE);
        } else {
            totalPages = 1;
        }

        for (ExperienceModel experience : experienceModelList) {
            experience.setIsFav(user != null && user.isFav(experience));
        }

        LOGGER.debug("Max page value service: {}", totalPages);
        return new Page<>(experienceModelList, page, totalPages, total);
    }

    @Override
    public List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category, UserModel user) {
        LOGGER.debug("Retrieving all experiences by best ranked of category with id {}", category.getCategoryId());

        final List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByBestRanked(category, CAROUSEL_LENGTH);

        for (ExperienceModel experience : experienceModelList) {
            experience.setIsFav(user != null && user.isFav(experience));
        }

        return experienceModelList;
    }

    @Override
    public Optional<Double> getMaxPriceByCategory(CategoryModel category) {
        LOGGER.debug("Retrieving max price of category with id {}", category.getCategoryId());
        return experienceDao.getMaxPriceByCategory(category);
    }

    @Override
    public Page<ExperienceModel> listExperiencesFavsByUser(UserModel user, Optional<OrderByModel> order, int page) {
        LOGGER.debug("Requested page {}", page);
        int totalPages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();
        final long total = user.getFavCount();

        if (total > 0) {
            LOGGER.debug("Total pages found: {}", total);

            totalPages = (int) Math.ceil((double) total / RESULT_PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", totalPages);

            if (page > totalPages) {
                page = totalPages;
            } else if (page < 0) {
                page = 1;
            }
            experienceModelList = user.getFavExperiences(page, RESULT_PAGE_SIZE, order);
        } else {
            totalPages = 1;
        }

        for (ExperienceModel experience : experienceModelList) {
            experience.setIsFav(true);
        }

        LOGGER.debug("Max page value service: {}", totalPages);
        return new Page<>(experienceModelList, page, totalPages, total);
    }

    @Override
    public Page<ExperienceModel> listExperiencesSearch(String name, Optional<OrderByModel> order, int page, UserModel user) {
        LOGGER.debug("Requested page {}", page);

        int totalPages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();
        final long total = experienceDao.getCountByName(name);

        if (total > 0) {
            LOGGER.debug("Total pages found: {}", total);

            totalPages = (int) Math.ceil((double) total / RESULT_PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", totalPages);

            if (page > totalPages) {
                page = totalPages;
            } else if (page < 0) {
                page = 1;
            }
            experienceModelList = experienceDao.listExperiencesSearch(name, order, page, RESULT_PAGE_SIZE);

        } else {
            totalPages = 1;
        }

        for (ExperienceModel experience : experienceModelList) {
            experience.setIsFav(user != null && user.isFav(experience));
        }

        LOGGER.debug("Max page value service: {}", totalPages);
        return new Page<>(experienceModelList, page, totalPages, total);
    }

    @Override
    public List<List<ExperienceModel>> getExperiencesListByCategories(UserModel user) {
        final List<List<ExperienceModel>> listExperiencesByCategory = new ArrayList<>();
        LOGGER.debug("Retrieving all experiences listed by categories");
        final List<CategoryModel> categories = categoryService.listAllCategories();

        for (int i = 0; i < categories.size(); i++) {
//            listExperiencesByCategory.add(new ArrayList<>());
            listExperiencesByCategory.add(listExperiencesByBestRanked(categories.get(i), user));
        }
        return listExperiencesByCategory;
    }

    @Override
    public boolean experienceBelongsToUser(UserModel user, ExperienceModel experience) {
        return experience.getUser().equals(user);
    }

    @Override
    public Page<ExperienceModel> listExperiencesListByUser(String name, UserModel user, Optional<OrderByModel> order, int page) {
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
        return new Page<>(experienceModelList, page, totalPages, (long) total);
    }

    @Transactional
    @Override
    public void increaseViews(ExperienceModel experience) {
        experience.increaseViews();
        updateExperienceWithoutImg(experience);
    }

    @Transactional
    @Override
    public void changeVisibility(ExperienceModel experience, boolean obs) {
        experience.setObservable(obs);
        updateExperienceWithoutImg(experience);
    }

    @Override
    public List<List<ExperienceModel>> userLandingPage(UserModel user) {
        final List<List<ExperienceModel>> listExperiencesByCategory = new ArrayList<>();
        List<ExperienceModel> viewedExperiences = user.getViewedExperiences(CAROUSEL_LENGTH);

        //Adding viewed experiences to user
        for (ExperienceModel experience : viewedExperiences) {
            experience.setIsFav(user.isFav(experience));
        }
        listExperiencesByCategory.add(viewedExperiences);

        //Getting recommendedByFavs
        //If we dont have enough recommendations we switch to recommended by views, and then plainly by the best ranked in general
        //If an experience was already added we skip it to avoid duplicates
        List<ExperienceModel> recommendedByFavsFullList = experienceDao.getRecommendedByFavs(user, CAROUSEL_LENGTH);
        List<Long> alreadyRecommended = recommendedByFavsFullList.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList());
        List<ExperienceModel> recommendedByViews;
        List<ExperienceModel> bestRanked;
        if (recommendedByFavsFullList.size() < CAROUSEL_LENGTH) {
            recommendedByViews = experienceDao.getRecommendedByViews(user, CAROUSEL_LENGTH - recommendedByFavsFullList.size(), alreadyRecommended);
            alreadyRecommended.addAll(recommendedByViews.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
            recommendedByFavsFullList.addAll(recommendedByViews);
        }
        if (recommendedByFavsFullList.size() < CAROUSEL_LENGTH) {
            bestRanked = experienceDao.getRecommendedBestRanked(CAROUSEL_LENGTH - recommendedByFavsFullList.size(), recommendedByFavsFullList.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
            alreadyRecommended.addAll(bestRanked.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
            recommendedByFavsFullList.addAll(bestRanked);
        }

        //Adding recommendedByFavs to general list
        for (ExperienceModel experience: recommendedByFavsFullList) {
            experience.setIsFav(user.isFav(experience));
        }
        listExperiencesByCategory.add(recommendedByFavsFullList);

        //Check if user hasReviews to get recommendations
        if (user.hasReviews()){
            //Getting recommendedByReviews
            //Firstly, we look at the city where the user has made the most reviews
            //Secondly, we look at the provider to whom the user has made the most reviews
            //Lastly, we look at the category which the user has made the most reviews
            //If an experience was already added we skip it to avoid duplicates
            List<Long> userReviewedIds = experienceDao.reviewedExperiencesId(user);
            List<ExperienceModel> recommendedByReviewsFullList;
            List<ExperienceModel> recommendedByReviewsProvider;
            List<ExperienceModel> recommendedByReviewsCategory;
            recommendedByReviewsFullList = experienceDao.getRecommendedByReviewsCity(user, CAROUSEL_LENGTH, alreadyRecommended, userReviewedIds);

            if (recommendedByReviewsFullList.size() < CAROUSEL_LENGTH) {
                recommendedByReviewsProvider = experienceDao.getRecommendedByReviewsProvider(user, CAROUSEL_LENGTH - recommendedByReviewsFullList.size(), alreadyRecommended, userReviewedIds);
                recommendedByReviewsFullList.addAll(recommendedByReviewsProvider);
                alreadyRecommended.addAll(recommendedByReviewsProvider.stream().map(ExperienceModel::getExperienceId).collect(Collectors.toList()));
            }

            if (recommendedByReviewsFullList.size() < CAROUSEL_LENGTH) {
                recommendedByReviewsCategory = experienceDao.getRecommendedByReviewsCategory(user, CAROUSEL_LENGTH - recommendedByReviewsFullList.size(), alreadyRecommended, userReviewedIds);
                recommendedByReviewsFullList.addAll(recommendedByReviewsCategory);
            }

            for (ExperienceModel experience: recommendedByReviewsFullList) {
                experience.setIsFav(user.isFav(experience));
            }
            listExperiencesByCategory.add(recommendedByReviewsFullList);
            return listExperiencesByCategory;
        }

        listExperiencesByCategory.add(new ArrayList<>());
        return listExperiencesByCategory;
    }

}
