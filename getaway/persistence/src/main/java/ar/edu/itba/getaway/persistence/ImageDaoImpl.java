package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.interfaces.persistence.ImageDao;
import ar.edu.itba.getaway.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class ImageDaoImpl implements ImageDao {
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationDaoImpl.class);

    @Override
    public ImageModel createImg(byte[] image) {
        final ImageModel imageModel = new ImageModel(image);
        em.persist(imageModel);
        return imageModel;
    }

    @Override
    public void updateImg(byte[] image, ImageModel imageModel) {
        LOGGER.debug("Update image with id {}", imageModel.getImageId());
        imageModel.setImage(image);
        em.merge(imageModel);
    }

    @Override
    public void deleteImg(ImageModel imageModel) {
        LOGGER.debug("Delete image with id {}", imageModel.getImageId());
        em.remove(imageModel);
    }

    @Override
    public Optional<ImageModel> getImgById(Long imageId) {
        LOGGER.debug("Get image with id {}", imageId);
        return Optional.ofNullable(em.find(ImageModel.class, imageId));
    }
}