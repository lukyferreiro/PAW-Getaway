package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.persistence.ExperienceDao;
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

    //TODO: limit page number to total_pages amount
    private static final int PAGE_SIZE = 6;
    private static final int RESULT_PAGE_SIZE = 9;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    @Override
    public ExperienceModel create(String name, String address, String description, String email, String url, Double price, long cityId, long categoryId, long userId, byte[] image) {
        LOGGER.debug("Creating experience with name {}", name);
        ExperienceModel experienceModel = experienceDao.create(name, address, description, email, url, price, cityId, categoryId, userId);
        ImageExperienceModel imageExperienceModel = imageService.createExperienceImg(image, experienceModel.getExperienceId(), true);
        experienceModel.setHasImage(image != null);
        experienceModel.setImageExperienceId(imageExperienceModel.getImageId());
        UserModel usermodel = userService.getUserById(experienceModel.getUserId()).get();
        if(!usermodel.getRoles().contains(Roles.PROVIDER)){
            userService.addRole(userId, Roles.PROVIDER);
        }
        return experienceModel;
    }

    @Override
    public boolean update(ExperienceModel experienceModel, byte[] image) {
        LOGGER.debug("Updating experience with id {}", experienceModel.getExperienceId());
        if (experienceDao.update(experienceModel)) {
            imageService.updateImg(image, experienceModel.getImageExperienceId());
            LOGGER.debug("Experience {} updated", experienceModel.getExperienceId());
            return true;
        } else {
            LOGGER.warn("Fail to update experience {}", experienceModel.getExperienceId());
            return false;
        }
    }

    @Override
    public boolean delete(long experienceId) {
        LOGGER.debug("Deleting experience with id {}", experienceId);
        Optional<ExperienceModel> experienceModelOptional = getById(experienceId);
        if (experienceModelOptional.isPresent()) {
            experienceDao.delete(experienceId);
            imageService.deleteImg(experienceModelOptional.get().getImageExperienceId());
            LOGGER.debug("Experience {} deleted", experienceId);
            return true;
        } else {
            LOGGER.warn("Experience {} NOT deleted", experienceId);
            return false;
        }
    }

    @Override
    public List<ExperienceModel> listAll(String order) {
        LOGGER.debug("Retrieving all experiences");
        return experienceDao.listAll(order);
    }

    @Override
    public Optional<ExperienceModel> getById(long experienceId) {
        LOGGER.debug("Retrieving experience with id {}", experienceId);
        return experienceDao.getById(experienceId);
    }

    @Override
    public List<ExperienceModel> listByUserId(long userId, Optional<OrderByModel> order) {
        LOGGER.debug("Retrieving experiences created by user with id {}", userId);
        return experienceDao.listByUserId(userId, order);
    }

    @Override
    public Page<ExperienceModel> listByFilter(long categoryId, Double max, long score, Long city, Optional<OrderByModel> order, int page) {
        int total_pages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();

        LOGGER.debug("Requested page {} ", page);
        try{
            int total = experienceDao.countListByFilter(categoryId, max, score, city);
            total_pages = (int) Math.ceil( (double) total/ PAGE_SIZE);

            LOGGER.debug("Total pages calculated: {}", total_pages);

            if (page <= total_pages){
                experienceModelList  = experienceDao.listByFilter(categoryId, max, score, city, order, page, PAGE_SIZE);
            }
            else {
                page = total_pages;
                experienceModelList  = experienceDao.listByFilter(categoryId, max, score, city, order, total_pages, PAGE_SIZE);
            }
        } catch (EmptyResultDataAccessException e) {
            total_pages = 1;
        }

        LOGGER.debug("Max page value service: {}", total_pages);
        return new Page<>(experienceModelList, page, total_pages);
    }

    @Override
    public List<ExperienceModel> listByBestRanked(long categoryId) {
        LOGGER.debug("Retrieving all experiences by best ranked of category with id {}", categoryId);
        return experienceDao.listByBestRanked(categoryId);
    }

    @Override
    public Optional<Double> getMaxPrice(long categoryId) {
        LOGGER.debug("Retrieving max price of category with id {}", categoryId);
        return experienceDao.getMaxPrice(categoryId);
    }

    @Override
    public List<ExperienceModel> listFavsByUserId(Long userId, Optional<OrderByModel> order) {
        LOGGER.debug("Retrieving all favs of user with id {}", userId);
        return experienceDao.listFavsByUserId(userId, order);
    }

    @Override
    public Page<ExperienceModel> getByName(String name, int page){
        int total_pages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();

        LOGGER.debug("REQUESTED PAGE {}", page);

        int total = experienceDao.getCountByName(name);

        if(total>0){
            LOGGER.debug("TOTAL FOUND {}", total);

            total_pages = (int) Math.ceil( (double) total/ RESULT_PAGE_SIZE);

            LOGGER.debug("MAXPAGE CALCULATED {}", total_pages);

            if (page <= total_pages){
                experienceModelList  = experienceDao.getByName(name, page, RESULT_PAGE_SIZE);
            }
            else {
                page = total_pages;
                experienceModelList  = experienceDao.getByName(name, page, RESULT_PAGE_SIZE);
            }
        }
        else {
            total_pages = 1;
        }

        LOGGER.debug("MAXPAGE VALUE SERVICE {}", total_pages);
        return new Page<>(experienceModelList, page, total_pages);
    }

}
