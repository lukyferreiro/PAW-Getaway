package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.persistence.ImageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public ImageModel create(byte[] image) {
        final ImageModel img = imageDao.create(image);
        LOGGER.info("Created image with id {}", img.getId());
        return img;
    }

    @Override
    public boolean update(long imageId, ImageModel imageModel) {
        LOGGER.debug("Updating image with id {}", imageId);
        return imageDao.update(imageId, imageModel);
    }

    @Override
    public boolean delete(long imageId) {
        LOGGER.debug("Delete image with id {}", imageId);
        return imageDao.delete(imageId);
    }

    @Override
    public List<ImageModel> listAll() {
        LOGGER.debug("Listing all images");
        return imageDao.listAll();
    }

    @Override
    public Optional<ImageModel> getById(long imageId) {
        LOGGER.debug("Looking for image by id {}", imageId);
        return imageDao.getById(imageId);
    }

    @Override
    public Optional<ImageModel> getByExperienceId(long experienceId) {
        LOGGER.debug("Looking for image of experience with id {}", experienceId);
        return imageDao.getByExperienceId(experienceId);
    }

}