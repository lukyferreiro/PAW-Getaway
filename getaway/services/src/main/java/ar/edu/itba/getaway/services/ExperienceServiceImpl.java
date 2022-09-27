package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.exceptions.DuplicateImageException;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.persistence.ExperienceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceServiceImpl implements ExperienceService {
    @Autowired
    private ExperienceDao experienceDao;

    private static final int PAGE_SIZE=2;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    @Override
    public ExperienceModel create(String name, String address, String description, String email, String url, Double price, long cityId, long categoryId, long userId, byte[] image) throws DuplicateImageException {
        LOGGER.debug("Creating experience with name {}", name);
        ExperienceModel experienceModel = experienceDao.create(name, address, description, email, url, price, cityId, categoryId, userId, image);
        LOGGER.debug("Created experience with id {}", experienceModel.getExperienceId());
        return experienceModel;
    }

    @Override
    public boolean update(ExperienceModel experienceModel, byte[] image) {
        LOGGER.debug("Updating experience with id {}", experienceModel.getExperienceId());
        if(experienceDao.update(experienceModel, image)){
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
    public Page<ExperienceModel> listByFilterWithCity(long categoryId, Double max, long cityId, long score, int page) {
        List<ExperienceModel> experienceModelList = experienceDao.listByFilterWithCity(categoryId, max, cityId, score, page, PAGE_SIZE);
        return new Page<>(experienceModelList, page, getTotalPageCount(experienceModelList.size()));
    }

    @Override
    public Page<ExperienceModel> listByFilterWithCityAsc(long categoryId, Double max, long cityId, long score, String order, int page) {
        List<ExperienceModel> experienceModelList = experienceDao.listByFilterWithCityAsc(categoryId, max, cityId, score, order, page, PAGE_SIZE);
        return new Page<>(experienceModelList, page, getTotalPageCount(experienceModelList.size()));
    }

    @Override
    public Page<ExperienceModel> listByFilterWithCityDesc(long categoryId, Double max, long cityId, long score, String order, int page) {
        List<ExperienceModel> experienceModelList = experienceDao.listByFilterWithCityDesc(categoryId, max, cityId, score, order, page, PAGE_SIZE);
        return new Page<>(experienceModelList, page, getTotalPageCount(experienceModelList.size()));
    }

    @Override
    public Page<ExperienceModel> listByFilter(long categoryId, Double max, long score, int page) {
        List<ExperienceModel> experienceModelList =  experienceDao.listByFilter(categoryId, max, score, page, PAGE_SIZE);
        return new Page<>(experienceModelList, page, getTotalPageCount(experienceModelList.size()));
    }

    @Override
    public Page<ExperienceModel> listByFilterAsc(long categoryId, Double max, long score, String order, int page) {
        List<ExperienceModel> experienceModelList= experienceDao.listByFilterAsc(categoryId, max, score, order, page, PAGE_SIZE);
        return new Page<>(experienceModelList, page, getTotalPageCount(experienceModelList.size()));
    }

    @Override
    public Page<ExperienceModel> listByFilterDesc(long categoryId, Double max, long score, String order, int page) {
        List<ExperienceModel> experienceModelList = experienceDao.listByFilterDesc(categoryId, max, score, order, page, PAGE_SIZE);
        return new Page<>(experienceModelList, page, getTotalPageCount(experienceModelList.size()));
    }

    @Override
    public Optional<Double> getMaxPrice(long categoryId) {
        return experienceDao.getMaxPrice(categoryId);
    }

    @Override
    public List<ExperienceModel> getByUserIdOrderBy(long id, String order) {
        return experienceDao.getByUserIdOrderBy(id,order);
    }

    @Override
    public List<ExperienceModel> getByUserIdOrderByDesc(long id, String order) {
        return experienceDao.getByUserIdOrderByDesc(id,order);
    }

    @Override
    public List<ExperienceModel> getOrderByDesc(String order) {
        return experienceDao.getOrderByDesc(order);
    }

    @Override
    public List<ExperienceModel> getOrderBy(String order) {
        return experienceDao.getOrderBy(order);
    }

    private int getTotalPageCount(int rowsCount){
        int total = (int) Math.ceil(rowsCount/PAGE_SIZE);
        return total==0?1:total;
    }
}
