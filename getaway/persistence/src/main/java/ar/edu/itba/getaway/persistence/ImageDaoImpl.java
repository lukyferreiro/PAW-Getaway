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

//    @Override
//    public ImageExperienceModel createExperienceImg(byte[] image, ExperienceModel experience, boolean isCover) {
//        final ImageModel imageData = createImg(image);
//
//        final ImageExperienceModel imageExperienceModel = new ImageExperienceModel(imageData, experience, isCover);
//        em.persist(imageExperienceModel);
//        LOGGER.info("Created new image experience with id {}", imageData.getImageId());
//
//        return imageExperienceModel;
//    }

    @Override
    public void updateImg(byte[] image, ImageModel imageModel) {
        LOGGER.debug("Update image with id {}", imageModel.getImageId());
//        final ImageModel imageModel = new ImageModel(image, imageId);
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

    @Override
    public Optional<ImageModel> getImgByExperience (ExperienceModel experience) {
        LOGGER.debug("Get image for experience with id {}", experience.getExperienceId());
        final TypedQuery<ImageModel> query = em.createQuery("FROM ImageModel WHERE experience = :experience", ImageModel.class);
        query.setParameter("experience", experience);
        return query.getResultList().stream().findFirst();
    }

}