package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.persistence.ExperienceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private ExperienceDao experienceDao;

    @Autowired
    public ExperienceServiceImpl(ExperienceDao experienceDao) {
        this.experienceDao = experienceDao;
    }

    @Override
    public ExperienceModel create(String name, String address, String description, String url, Double price, long cityId, long categoryId, long userId, boolean hasImage) {
        return experienceDao.create(name, address, description, url, price, cityId, categoryId, userId, hasImage);
    }

    @Override
    public boolean update(long experienceId, ExperienceModel experienceModel) {
        return experienceDao.update(experienceId, experienceModel);
    }

    @Override
    public boolean delete(long experienceId) {
        return experienceDao.delete(experienceId);
    }

    @Override
    public List<ExperienceModel> listAll() {
        return experienceDao.listAll();
    }

    @Override
    public Optional<ExperienceModel> getById(long experienceId) {
        return experienceDao.getById(experienceId);
    }

    @Override
    public List<ExperienceModel> listByCategory(long categoryId) {
        return experienceDao.listByCategory(categoryId);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndCity(long categoryId, long cityId) {
        return experienceDao.listByCategoryAndCity(categoryId, cityId);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndPrice(long categoryId, Double max) {
        return experienceDao.listByCategoryAndPrice(categoryId, max);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndCity(long categoryId, Double max, long cityId) {
        return experienceDao.listByCategoryPriceAndCity(categoryId, max, cityId);
    }

    @Override
    public List<ExperienceModel> getRandom() {
        return experienceDao.getRandom();
    }

    @Override
    public String getCountryCity(long experienceId) {
        return experienceDao.getCountryCity(experienceId);
    }

    @Override
    public List<ExperienceModel> getByUserId(long userId) {
        return experienceDao.getByUserId(userId);
    }

    @Override
    public Optional<Long> getAvgReviews(long experienceId) {
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
}
