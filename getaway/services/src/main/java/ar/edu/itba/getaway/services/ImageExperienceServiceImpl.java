package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ImageExperienceModel;
import ar.edu.itba.getaway.persistence.ImageExperienceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageExperienceServiceImpl implements ImageExperienceService {

    @Autowired
    private ImageExperienceDao imageExperienceDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageExperienceServiceImpl.class);

    @Override
    public ImageExperienceModel create(long imageId, long experienceId, boolean isCover) {
        return imageExperienceDao.create(imageId, experienceId, isCover);
    }

    @Override
    public boolean update(long imageId, ImageExperienceModel imageExperienceModel) {
        return imageExperienceDao.update(imageId, imageExperienceModel);
    }

    @Override
    public boolean delete(long imageId) {
        return imageExperienceDao.delete(imageId);
    }

    @Override
    public List<ImageExperienceModel> listAll() {
        return imageExperienceDao.listAll();
    }

    @Override
    public Optional<ImageExperienceModel> getById(long imageId) {
        return imageExperienceDao.getById(imageId);
    }
}