package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.LocationService;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.interfaces.persistence.ImageDao;
import ar.edu.itba.getaway.interfaces.services.ImageService;
import ar.edu.itba.getaway.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ExperienceService experienceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public ImageModel createImg(byte[] image, String mimeType) {
        final ImageModel img = imageDao.createImg(image, mimeType);
        LOGGER.info("Created image with id {}", img.getImageId());
        return img;
    }

    @Transactional
    @Override
    public void updateImg(byte[] image, String mimeType, Long id) {
        final ExperienceModel experience = experienceService.getExperienceById(id).orElseThrow(ExperienceNotFoundException::new);
        ImageModel imageModel = experience.getExperienceImage();

        LOGGER.debug("Updating image with id {}", imageModel.getImageId());
        imageDao.updateImg(image, mimeType, imageModel);
    }

    @Transactional
    @Override
    public void updateUserImg(byte[] image, String mimeType, UserModel userModel) {
        ImageModel imageModel = userModel.getProfileImage();

        LOGGER.debug("Updating image with id {}", imageModel.getImageId());
        imageDao.updateImg(image, mimeType, imageModel);
    }

    @Override
    public void deleteImg(ImageModel imageModel) {
        LOGGER.debug("Delete image with id {}", imageModel.getImageId());
        imageDao.deleteImg(imageModel);
    }

    @Override
    public Optional<ImageModel> getImgById(long imageId) {
        LOGGER.debug("Retrieving image with id {}", imageId);
        return imageDao.getImgById(imageId);
    }
}