package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.persistence.ExperienceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private ExperienceDao experienceDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    @Override
    public ExperienceModel create(String name, String address, String description, String url, Double price, long cityId, long categoryId, long userId, boolean hasImage) {
        LOGGER.debug("Creating experience with name {}", name);
        ExperienceModel experienceModel = experienceDao.create(name, address, description, url, price, cityId, categoryId, userId, hasImage);
        LOGGER.debug("Created experience with id {}", experienceModel.getId());
        return experienceModel;
    }

    @Override
    public boolean update(long experienceId, ExperienceModel experienceModel) {
        LOGGER.debug("Updating experience with id {}", experienceId);
        if(experienceDao.update(experienceId, experienceModel)){
            LOGGER.debug("Experience {} updated", experienceId);
            return true;
        } else {
            LOGGER.warn("Experience {} NOT updated", experienceId);
            return false;
        }
    }

    @Override
    public boolean delete(long experienceId) {
        LOGGER.debug("Deleting experience with id {}", experienceId);
        if(experienceDao.delete(experienceId)){
            LOGGER.debug("Experience {} deleted", experienceId);
            return true;
        } else {
            LOGGER.warn("Experience {} NOT deleted", experienceId);
            return false;
        }
    }

    @Override
    public List<ExperienceModel> listAll() {
        LOGGER.debug("Retrieving all experiences");
        return experienceDao.listAll();
    }

    @Override
    public Optional<ExperienceModel> getById(long experienceId) {
        LOGGER.debug("Retrieving experience with id {}", experienceId);
        return experienceDao.getById(experienceId);
    }

    @Override
    public List<ExperienceModel> listByCategory(long categoryId) {
        LOGGER.debug("Retrieving experiences with category id {}", categoryId);
        return experienceDao.listByCategory(categoryId);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndCity(long categoryId, long cityId) {
        LOGGER.debug("Retrieving experiences with category id {} and city id {}", categoryId, cityId);
        return experienceDao.listByCategoryAndCity(categoryId, cityId);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndPrice(long categoryId, Double max) {
        LOGGER.debug("Retrieving experiences with category id {} and max price {}", categoryId, max);
        return experienceDao.listByCategoryAndPrice(categoryId, max);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndCity(long categoryId, Double max, long cityId) {
        LOGGER.debug("Retrieving experiences with category id {}, city id {} and max price {}", categoryId, cityId, max);
        return experienceDao.listByCategoryPriceAndCity(categoryId, max, cityId);
    }

    @Override
    public List<ExperienceModel> getRandom() {
        LOGGER.debug("Retrieving random experiences");
        return experienceDao.getRandom();
    }

    @Override
    public String getCountryCity(long experienceId) {
        LOGGER.debug("Retrieving country and city of experience with id {}", experienceId);
        return experienceDao.getCountryCity(experienceId);
    }

    @Override
    public List<ExperienceModel> getByUserId(long userId) {
        LOGGER.debug("Retrieving experiences created by user with id {}", userId);
        return experienceDao.getByUserId(userId);
    }

    @Override
    public Optional<Long> getAvgReviews(long experienceId) {
        LOGGER.debug("Retrieving average ranking of experience with id {}", experienceId);
        return experienceDao.getAvgReviews(experienceId);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceCityAndScore(long categoryId, Double max, long cityId, long score) {
        return experienceDao.listByCategoryPriceCityAndScore( categoryId,  max,  cityId,  score);
    }

    @Override
    public List<ExperienceModel> listByCategoryCityAndScore(long categoryId, long cityId, long score) {
        return experienceDao.listByCategoryCityAndScore( categoryId,  cityId,  score);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndScore(long categoryId, Double max, long score) {
        return experienceDao.listByCategoryPriceAndScore( categoryId,  max,  score);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndScore(long categoryId, long score) {
        return experienceDao.listByCategoryAndScore( categoryId,  score);
    }

    public Page<ExperienceModel> listByScore(int page, long categoryId) {
        return new Page<>(experienceDao.listByScore(page,categoryId),page,experienceDao.getTotalPagesAllExperiences());
    }
}
//TODO Testear a ver si funciona la paginacion en la landing , osea testearlo en el mainPage.jsp