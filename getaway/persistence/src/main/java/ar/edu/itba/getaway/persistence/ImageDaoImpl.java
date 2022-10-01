package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ImageExperienceModel;
import ar.edu.itba.getaway.models.ImageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ImageDaoImpl implements ImageDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert imageSimplejdbcInsert;
    private final SimpleJdbcInsert imageExperienceSimplejdbcInsert;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageDaoImpl.class);

    private static final RowMapper<ImageModel> IMAGE_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new ImageModel(rs.getLong("imgid"),
                    rs.getBytes("imageObject"));

    private static final RowMapper<ImageExperienceModel> IMAGE_EXPERIENCE_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new ImageExperienceModel(rs.getLong("imgid"),
                    rs.getLong("experienceId"),
                    rs.getBoolean("isCover"));

    @Autowired
    public ImageDaoImpl(final DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.imageSimplejdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("images")
                .usingGeneratedKeyColumns("imgid");
        this.imageExperienceSimplejdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("imagesExperiences");
    }

    @Override
    public ImageModel createImg(byte[] image) {
        final Map<String, Object> imageData = new HashMap<>();
        imageData.put("imageObject", image);
        final long imgId;

        imgId = imageSimplejdbcInsert.executeAndReturnKey(imageData).longValue();
        imageData.put("imgId", imgId);

        LOGGER.info("Created new image with id {}", imgId);

        return new ImageModel(imgId, image);
    }

    @Override
    public ImageExperienceModel createExperienceImg(byte[] image, long experienceId, boolean isCover) {
        final ImageModel imageData = createImg(image);

        final Map<String, Object> imageExperienceData = new HashMap<>();
        imageExperienceData.put("experienceId", experienceId);
        imageExperienceData.put("isCover", isCover);
        imageExperienceData.put("imgId", imageData.getId());
        imageExperienceSimplejdbcInsert.execute(imageExperienceData);

        LOGGER.info("Created new image experience with id {}", imageData.getId());

        return new ImageExperienceModel(imageData.getId(), experienceId, isCover);
    }

    @Override
    public boolean updateImg(byte[] image, long imageId) {
        final String query = "UPDATE images SET imageObject = ? WHERE imgid = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.update(query, image, imageId) == 1;
    }

    @Override
    public boolean deleteImg(long imgid) {
        final String query = "DELETE FROM images WHERE imgid = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.update(query, imgid) == 1;
    }

//    @Override
//    public List<ImageModel> listAllImg() {
//        final String query = "SELECT imgid, imageObject FROM images";
//        LOGGER.debug("Executing query: {}", query);
//        return new ArrayList<>(jdbcTemplate.query(query, IMAGE_MODEL_ROW_MAPPER));
//    }
//
//    @Override
//    public List<ImageExperienceModel> listAllExperienceImg() {
//        final String query = "SELECT imgId, experienceId, isCover FROM imagesExperiences";
//        LOGGER.debug("Executing query: {}", query);
//        return new ArrayList<>(jdbcTemplate.query(query, IMAGE_EXPERIENCE_MODEL_ROW_MAPPER));
//    }

    @Override
    public Optional<ImageModel> getImgById(long imageId) {
        final String query = "SELECT imgid, imageObject FROM images WHERE imgid = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{imageId}, IMAGE_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Optional<ImageModel> getImgByExperienceId(long experienceId) {
        final String query = "SELECT imgId, imageObject FROM imagesExperiences NATURAL JOIN images WHERE experienceId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{experienceId}, IMAGE_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

}