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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //TODO: limit page number to total_pages amount
    private static final int PAGE_SIZE = 6;
    private static final int RESULT_PAGE_SIZE = 9;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    @Override
    @Transactional
    public ExperienceModel createExperience(String name, String address, String description, String email, String url, Double price, CityModel city, CategoryModel category, UserModel user, byte[] image) {
        LOGGER.debug("Creating experience with name {}", name);
        final ImageModel experienceImage = imageService.createImg(image);
        final ExperienceModel experienceModel = experienceDao.createExperience(name, address, description, email, url, price, city, category, user, experienceImage);
//        final ImageExperienceModel imageExperienceModel = imageService.createExperienceImg(image, experienceModel, true);
//        experienceModel.setHasImage(image != null);
//        experienceModel.setImageExperienceId(imageExperienceModel.getImageId());
//        final UserModel usermodel = userService.getUserById(experienceModel.getUserId()).get();
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
    public void deleteExperience(ExperienceModel experienceModel) {
        LOGGER.debug("Deleting experience with id {}", experienceModel.getExperienceId());
//        ImageModel toDeleteImg = imageService.getImgByExperience(experienceModel).get().getImage();
        ImageModel toDeleteImg = experienceModel.getExperienceImage();
        experienceDao.deleteExperience(experienceModel);
        imageService.deleteImg(toDeleteImg);
//        imageService.deleteImg();
//        final Optional<ExperienceModel> experienceModelOptional = getExperienceById(experienceId);
//        if (experienceModelOptional.isPresent()) {
//            experienceDao.deleteExperience(experienceId);
//            imageService.deleteImg(experienceModelOptional.get().getImageExperienceId());
//            LOGGER.debug("Experience {} deleted", experienceId);
//        } else {
//            LOGGER.warn("Experience {} NOT deleted", experienceId);
//        }
    }

    @Override
    public Optional<ExperienceModel> getExperienceById(Long experienceId) {
        LOGGER.debug("Retrieving experience with id {}", experienceId);
        return experienceDao.getExperienceById(experienceId);
    }

    @Override
    public List<ExperienceModel> listExperiencesByUser(UserModel user, CategoryModel category) {
        LOGGER.debug("Retrieving experiences of category {} created by user with id {}", category.getCategoryId(), user.getUserId());
        return experienceDao.listExperiencesByUser(user, category);
    }

    @Override
    public Page<ExperienceModel> listExperiencesByFilter(CategoryModel category, Double max, Long score, CityModel city, Optional<OrderByModel> order, Integer page) {
        int total_pages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();

        LOGGER.debug("Requested page {} ", page);
        Long total = experienceDao.countListByFilter(category, max, score, city);
        if (total > 0){
            LOGGER.debug("Total experiences found: {}", total);

            total_pages = (int) Math.ceil((double) total / PAGE_SIZE);

            LOGGER.debug("Total pages calculated: {}", total_pages);

            if (page > total_pages) {
                page = total_pages;
            } else if (page < 0) {
                page = 1;
            }

            experienceModelList = experienceDao.listExperiencesByFilter(category, max, score, city, order, page, PAGE_SIZE);
        } else {
            total_pages = 1;
        }

        LOGGER.debug("Max page value service: {}", total_pages);
        return new Page<>(experienceModelList, page, total_pages);
    }

    @Override
    public List<ExperienceModel> listExperiencesByBestRanked(CategoryModel category) {
        LOGGER.debug("Retrieving all experiences by best ranked of category with id {}", category.getCategoryId());
        return experienceDao.listExperiencesByBestRanked(category);
    }

    @Override
    public Optional<Double> getMaxPriceByCategory (CategoryModel category) {
        LOGGER.debug("Retrieving max price of category with id {}", category.getCategoryId());
        return experienceDao.getMaxPriceByCategory (category);
    }

    @Override
    public Page<ExperienceModel> listExperiencesFavsByUser(UserModel user, Optional<OrderByModel> order, Integer page) {
        int total_pages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();

        LOGGER.debug("Requested page {}", page);

        Integer total = experienceDao.getCountExperiencesFavsByUser(user);

        if (total > 0) {
            LOGGER.debug("Total pages found: {}", total);

            total_pages = (int) Math.ceil((double) total / RESULT_PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", total_pages);

            if (page > total_pages) {
                page = total_pages;
            } else if (page < 0) {
                page = 1;
            }
            experienceModelList = experienceDao.listExperiencesFavsByUser(user, order, page, RESULT_PAGE_SIZE);
        } else {
            total_pages = 1;
        }

        LOGGER.debug("Max page value service: {}", total_pages);
        return new Page<>(experienceModelList, page, total_pages);
    }

    @Override
    public Page<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, Integer page) {
        int total_pages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();

        LOGGER.debug("Requested page {}", page);

        Long total = experienceDao.getCountByName(name);

        if (total > 0) {
            LOGGER.debug("Total pages found: {}", total);

            total_pages = (int) Math.ceil((double) total / RESULT_PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", total_pages);

            if (page > total_pages) {
                page = total_pages;
            } else if (page < 0) {
                page = 1;
            }
            experienceModelList = experienceDao.listExperiencesByName(name, order, page, RESULT_PAGE_SIZE);
        } else {
            total_pages = 1;
        }

        LOGGER.debug("Max page value service: {}", total_pages);
        return new Page<>(experienceModelList, page, total_pages);
    }

    @Override
    public List<List<ExperienceModel>> getExperiencesListByCategories() {
        final List<List<ExperienceModel>> listExperiencesByCategory = new ArrayList<>();
        LOGGER.debug("Retrieving all experiences listed by categories");
        for (int i = 0; i < categoryService.getCategoriesCount(); i++) {
            listExperiencesByCategory.add(new ArrayList<>());
            listExperiencesByCategory.get(i).addAll(listExperiencesByBestRanked(categoryService.getCategoryById((long)(i + 1)).get()));
        }
        return listExperiencesByCategory;
    }

    @Override
    public List<List<ExperienceModel>> getExperiencesListByCategoriesByUserId(UserModel user) {
        final List<List<ExperienceModel>> listExperiencesByCategory = new ArrayList<>();
        LOGGER.debug("Retrieving all experiences listed by categories of the user with id {}", user.getUserId());
        for (int i = 0; i < categoryService.getCategoriesCount(); i++) {
            listExperiencesByCategory.add(new ArrayList<>());
            listExperiencesByCategory.get(i).addAll(listExperiencesByUser(user, categoryService.getCategoryById((long)(i + 1)).get()));
        }
        return listExperiencesByCategory;
    }

    @Override
    public boolean hasExperiencesByUser(UserModel user) {
        LOGGER.debug("Retrieving whether the user with id {} has experiences", user.getUserId());
        return experienceDao.hasExperiencesByUser(user);
    }

    @Override
    public boolean experienceBelongsToUser(UserModel user, ExperienceModel experience) {
        return experienceDao.experienceBelongsToUser(user, experience);
    }

    @Override
    public Page<ExperienceModel> getExperiencesListByUserId(UserModel user, Optional<OrderByModel> order, Integer page) {
        int total_pages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();

        LOGGER.debug("Requested page {}", page);

        Integer total = experienceDao.getCountExperiencesByUser(user);

        if (total > 0) {
            LOGGER.debug("Total pages found: {}", total);

            total_pages = (int) Math.ceil((double) total / RESULT_PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", total_pages);

            if (page > total_pages) {
                page = total_pages;
            } else if (page < 0) {
                page = 1;
            }
            experienceModelList = experienceDao.getExperiencesListByUserId(user, order, page, RESULT_PAGE_SIZE);
        } else {
            total_pages = 1;
        }

        LOGGER.debug("Max page value service: {}", total_pages);
        return new Page<>(experienceModelList, page, total_pages);
//        return experienceDao.getExperiencesListByUserId(user, order, page);
    }

}
