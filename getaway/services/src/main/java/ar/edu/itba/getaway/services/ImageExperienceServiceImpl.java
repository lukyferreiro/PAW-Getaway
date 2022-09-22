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
        LOGGER.debug("Creating image {} to experience with id {}", imageId, experienceId);
        return imageExperienceDao.create(imageId, experienceId, isCover);
    }

    @Override
    public boolean update(long imageId, ImageExperienceModel imageExperienceModel) {
        LOGGER.debug("Updating image experience with id {}", imageId);
        if(imageExperienceDao.update(imageId, imageExperienceModel)){
            LOGGER.debug("Image experience {} updated", imageId);
            return true;
        } else {
            LOGGER.warn("Image experience {} NOT updated", imageId);
            return false;
        }
    }

    @Override
    public boolean delete(long imageId) {
        LOGGER.debug("Deleting image experience with id {}", imageId);
        if(imageExperienceDao.delete(imageId)){
            LOGGER.debug("Image experience {} deleted", imageId);
            return true;
        } else {
            LOGGER.warn("Image experience {} NOT deleted", imageId);
            return false;
        }
    }

    @Override
    public List<ImageExperienceModel> listAll() {
        LOGGER.debug("Retrieving all images");
        return imageExperienceDao.listAll();
    }

    @Override
    public Optional<ImageExperienceModel> getById(long imageId) {
        LOGGER.debug("Retrieving images with id {}", imageId);
        return imageExperienceDao.getById(imageId);
    }
}