package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.exceptions.DuplicateImageException;
import ar.edu.itba.getaway.models.ImageExperienceModel;
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
    public ImageModel createImg(byte[] image) throws DuplicateImageException {
        final ImageModel img = imageDao.createImg(image);
        LOGGER.info("Created image with id {}", img.getId());
        return img;
    }

    @Override
    public boolean updateImg(byte[] image, long imageId) {
        LOGGER.debug("Updating image with id {}", imageId);
        return imageDao.updateImg(image, imageId);
    }

    @Override
    public boolean deleteImg(long imageId) {
        LOGGER.debug("Delete image with id {}", imageId);
        return imageDao.deleteImg(imageId);
    }

    @Override
    public List<ImageModel> listAllImg() {
        LOGGER.debug("Retrieving all images");
        return imageDao.listAllImg();
    }

    @Override
    public Optional<ImageModel> getImgById(long imageId) {
        LOGGER.debug("Retrieving image with id {}", imageId);
        return imageDao.getImgById(imageId);
    }

    @Override
    public ImageExperienceModel createExperienceImg(byte[] image, long experienceId, boolean isCover) throws DuplicateImageException {
        final ImageExperienceModel imgExp = imageDao.createExperienceImg(image, experienceId, isCover);
        LOGGER.debug("Creating image {} to experience with id {}", imgExp.getImageId(), experienceId);
        return imgExp;
    }

    @Override
    public List<ImageExperienceModel> listAllExperienceImg() {
        LOGGER.debug("Retrieving all images");
        return imageDao.listAllExperienceImg();
    }

    @Override
    public Optional<ImageModel> getImgByExperienceId(long experienceId) {
        LOGGER.debug("Retrieving image of experience with id {}", experienceId);
        return imageDao.getImgByExperienceId(experienceId);
    }

}