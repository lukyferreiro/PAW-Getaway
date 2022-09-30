package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ImageExperienceModel;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.persistence.ExperienceDao;
import ar.edu.itba.getaway.persistence.ImageDao;
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
    private ImageDao imageDao;

    //TODO: limit page number to total_pages amount
    private static final int PAGE_SIZE = 6;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    @Override
    public ExperienceModel create(String name, String address, String description, String email, String url, Double price, long cityId, long categoryId, long userId, byte[] image) {
        LOGGER.debug("Creating experience with name {}", name);
        ExperienceModel experienceModel = experienceDao.create(name, address, description, email, url, price, cityId, categoryId, userId);
        ImageExperienceModel imageExperienceModel = imageDao.createExperienceImg(image, experienceModel.getExperienceId(), true);
        experienceModel.setHasImage(image != null);
        experienceModel.setImageExperienceId(imageExperienceModel.getImageId());
        LOGGER.debug("Created experience with id {}", experienceModel.getExperienceId());
        return experienceModel;
    }

    @Override
    public boolean update(ExperienceModel experienceModel, byte[] image) {
        LOGGER.debug("Updating experience with id {}", experienceModel.getExperienceId());

        imageDao.updateImg(image, experienceModel.getImageExperienceId());
        if (experienceDao.update(experienceModel)) {
            imageDao.updateImg(image, experienceModel.getImageExperienceId());
            LOGGER.debug("Experience {} updated", experienceModel.getExperienceId());
            return true;
        } else {
            LOGGER.warn("Experience {} NOT updated", experienceModel.getExperienceId());
            return false;
        }
    }

    @Override
    public boolean delete(long experienceId) {
        LOGGER.debug("Deleting experience with id {}", experienceId);
        Optional<ExperienceModel> experienceModelOptional = getById(experienceId);
        if (experienceModelOptional.isPresent()) {
            experienceDao.delete(experienceId);
            imageDao.deleteImg(experienceModelOptional.get().getImageExperienceId());
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
    public List<ExperienceModel> listByUserId(long userId, String order) {
        LOGGER.debug("Retrieving experiences created by user with id {}", userId);
        return experienceDao.listByUserId(userId, order);
    }

    @Override
    public Page<ExperienceModel> listByFilter(long categoryId, Double max, long score, String city, String order, int page) {
        int total_pages;
        List<ExperienceModel> experienceModelList = new ArrayList<>();

        LOGGER.debug("REQUESTED PAGE {}", page);
        try{
            int total = experienceDao.countListByFilter(categoryId, max, score, city);
            total_pages = (int) Math.ceil( (double) total/ PAGE_SIZE);

            LOGGER.debug("MAXPAGE CALCULATED {}", total_pages);

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

        LOGGER.debug("MAXPAGE VALUE SERVICE {}", total_pages);
        return new Page<>(experienceModelList, page, total_pages);
    }

    @Override
    public List<ExperienceModel> listByBestRanked(long categoryId) {
        return experienceDao.listByBestRanked(categoryId);
    }

    @Override
    public Optional<Double> getMaxPrice(long categoryId) {
        return experienceDao.getMaxPrice(categoryId);
    }

}
