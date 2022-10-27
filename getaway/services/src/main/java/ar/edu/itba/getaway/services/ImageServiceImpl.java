package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.interfaces.persistence.ImageDao;
import ar.edu.itba.getaway.interfaces.services.ImageService;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public ImageModel createImg(byte[] image) {
        final ImageModel img = imageDao.createImg(image);
        LOGGER.info("Created image with id {}", img.getImageId());
        return img;
    }

    @Transactional
    @Override
    public void updateImg(byte[] image, ImageModel imageModel) {
        LOGGER.debug("Updating image with id {}", imageModel.getImageId());
        imageDao.updateImg(image, imageModel);
    }

    @Override
    public void deleteImg(ImageModel imageModel) {
        LOGGER.debug("Delete image with id {}", imageModel.getImageId());
        imageDao.deleteImg(imageModel);
    }

    @Override
    public Optional<ImageModel> getImgById(Long imageId) {
        LOGGER.debug("Retrieving image with id {}", imageId);
        return imageDao.getImgById(imageId);
    }
}