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
import org.springframework.stereotype.Service;

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
    public ExperienceModel createExperience (String name, String address, String description, String email, String url, Double price, Long cityId, Long categoryId, Long userId, byte[] image) {
        LOGGER.debug("Creating experience with name {}", name);
        final ExperienceModel experienceModel = experienceDao.createExperience(name, address, description, email, url, price, cityId, categoryId, userId);
        final ImageExperienceModel imageExperienceModel = imageService.createExperienceImg(image, experienceModel.getExperienceId(), true);
        experienceModel.setHasImage(image != null);
        experienceModel.setImageExperienceId(imageExperienceModel.getImageId());
        final UserModel usermodel = userService.getUserById(experienceModel.getUserId()).get();
        if(!usermodel.getRoles().contains(Roles.PROVIDER)){
            LOGGER.debug("User gains role provider when creates an experience for first time");
            userService.addRole(userId, Roles.PROVIDER);
        }
        return experienceModel;
    }

    @Override
    public void updateExperience(ExperienceModel experienceModel, byte[] image) {
        LOGGER.debug("Updating experience with id {}", experienceModel.getExperienceId());
        if (experienceDao.updateExperience(experienceModel)) {
            imageService.updateImg(image, experienceModel.getImageExperienceId());
            LOGGER.debug("Experience {} updated", experienceModel.getExperienceId());
        } else {
            LOGGER.warn("Fail to update experience {}", experienceModel.getExperienceId());
        }
    }

    @Override
    public void deleteExperience(Long experienceId) {
        LOGGER.debug("Deleting experience with id {}", experienceId);
        final Optional<ExperienceModel> experienceModelOptional = getExperienceById(experienceId);
        if (experienceModelOptional.isPresent()) {
            experienceDao.deleteExperience(experienceId);
            imageService.deleteImg(experienceModelOptional.get().getImageExperienceId());
            LOGGER.debug("Experience {} deleted", experienceId);
        } else {
            LOGGER.warn("Experience {} NOT deleted", experienceId);
        }
    }

    @Override
    public Optional<ExperienceModel> getExperienceById(Long experienceId) {
        LOGGER.debug("Retrieving experience with id {}", experienceId);
        return experienceDao.getExperienceById(experienceId);
    }

    @Override
    public List<ExperienceModel> listExperiencesByUserId(Long userId, Long categoryId) {
        LOGGER.debug("Retrieving experiences created by user with id {}", userId);
        return experienceDao.listExperiencesByUserId(userId,  categoryId);
    }

    @Override
    public Page<ExperienceModel> listExperiencesByFilter(Long categoryId, Double max, Long score, Long city, Optional<OrderByModel> order, Integer page) {
        int total_pages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();

        LOGGER.debug("Requested page {} ", page);
        try{
            int total = experienceDao.countListByFilter(categoryId, max, score, city);
            total_pages = (int) Math.ceil( (double) total/ PAGE_SIZE);

            LOGGER.debug("Total pages calculated: {}", total_pages);

            if (page > total_pages) {
                page = total_pages;
            }
            else if (page < 0){
                page = 1;
            }

            experienceModelList  = experienceDao.listExperiencesByFilter(categoryId, max, score, city, order, page, PAGE_SIZE);
        } catch (EmptyResultDataAccessException e) {
            total_pages = 1;
        }

        LOGGER.debug("Max page value service: {}", total_pages);
        return new Page<>(experienceModelList, page, total_pages);
    }

    @Override
    public List<ExperienceModel> listExperiencesByBestRanked(Long categoryId) {
        LOGGER.debug("Retrieving all experiences by best ranked of category with id {}", categoryId);
        return experienceDao.listExperiencesByBestRanked(categoryId);
    }

    @Override
    public Optional<Double> getMaxPriceByCategoryId(Long categoryId) {
        LOGGER.debug("Retrieving max price of category with id {}", categoryId);
        return experienceDao.getMaxPriceByCategoryId(categoryId);
    }

    @Override
    public Page<ExperienceModel> listExperiencesFavsByUserId(Long userId, Optional<OrderByModel> order, Integer page) {
        int total_pages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();

        LOGGER.debug("Requested page {}", page);

        Integer total = experienceDao.getCountExperiencesFavsByUserId(userId);

        if(total>0){
            LOGGER.debug("Total pages found: {}", total);

            total_pages = (int) Math.ceil( (double) total/ RESULT_PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", total_pages);

            if (page > total_pages) {
                page = total_pages;
            }
            else if (page < 0){
                page = 1;
            }
            experienceModelList  = experienceDao.listExperiencesFavsByUserId(userId, order, page, RESULT_PAGE_SIZE);
        }
        else {
            total_pages = 1;
        }

        LOGGER.debug("Max page value service: {}", total_pages);
        return new Page<>(experienceModelList, page, total_pages);
    }

    @Override
    public Page<ExperienceModel> listExperiencesByName(String name, Optional<OrderByModel> order, Integer page){
        int total_pages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();

        LOGGER.debug("Requested page {}", page);

        int total = experienceDao.getCountByName(name);

        if(total>0){
            LOGGER.debug("Total pages found: {}", total);

            total_pages = (int) Math.ceil( (double) total/ RESULT_PAGE_SIZE);

            LOGGER.debug("Max page calculated: {}", total_pages);

            if (page > total_pages) {
                page = total_pages;
            }
            else if (page < 0){
                page = 1;
            }
            experienceModelList  = experienceDao.listExperiencesByName(name, order, page, RESULT_PAGE_SIZE);
        }
        else {
            total_pages = 1;
        }

        LOGGER.debug("Max page value service: {}", total_pages);
        return new Page<>(experienceModelList, page, total_pages);
    }

    @Override
    public List<List<ExperienceModel>> getExperiencesListByCategories() {
        final List<List<ExperienceModel>> listExperiencesByCategory = new ArrayList<>();
        for (int i = 0; i < categoryService.getCategoriesCount(); i++) {
            listExperiencesByCategory.add(new ArrayList<>());
            listExperiencesByCategory.get(i).addAll(listExperiencesByBestRanked((long) (i + 1)));
        }
        return listExperiencesByCategory;
    }

    @Override
    public List<List<ExperienceModel>> getExperiencesListByCategoriesByUserId(Long userId) {
        final List<List<ExperienceModel>> listExperiencesByCategory = new ArrayList<>();
        for (int i = 0; i < categoryService.getCategoriesCount(); i++) {
            listExperiencesByCategory.add(new ArrayList<>());
            listExperiencesByCategory.get(i).addAll(listExperiencesByUserId(userId, (long) (i + 1)));
        }
        return listExperiencesByCategory;
    }

}
