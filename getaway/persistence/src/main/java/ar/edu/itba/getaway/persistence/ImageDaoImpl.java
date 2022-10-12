package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.interfaces.persistence.ImageDao;
import ar.edu.itba.getaway.models.ImageModel;
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
//    public ImageExperienceModel createExperienceImg(byte[] image, Long experienceId, boolean isCover) {
//        final ImageModel imageData = createImg(image);
//
//        final Map<String, Object> imageExperienceData = new HashMap<>();
//        imageExperienceData.put("experienceId", experienceId);
//        imageExperienceData.put("isCover", isCover);
//        imageExperienceData.put("imgId", imageData.getImageId());
//        imageExperienceSimplejdbcInsert.execute(imageExperienceData);
//
//        LOGGER.info("Created new image experience with id {}", imageData.getImageId());
//
//        return new ImageExperienceModel(imageData.getImageId(), experienceId, isCover);
//    }

    @Override
    public void updateImg(byte[] image, Long imageId) {
        LOGGER.debug("Delete image with id {}", imageId);
        final ImageModel imageModel = new ImageModel(image, imageId);
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
        final TypedQuery<ImageModel> query = em.createQuery("FROM ImageModel WHERE imgId = :imgId", ImageModel.class);
        query.setParameter("imgId", imageId);
        em.find(ImageModel.class, imageId);
        return query.getResultList().stream().findFirst();
    }

//    @Override
//    public Optional<ImageModel> getImgByExperienceId(Long experienceId) {
//        final String query = "SELECT imgId, imageObject FROM imagesExperiences NATURAL JOIN images WHERE experienceId = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{experienceId}, IMAGE_MODEL_ROW_MAPPER)
//                .stream().findFirst();
//    }

}