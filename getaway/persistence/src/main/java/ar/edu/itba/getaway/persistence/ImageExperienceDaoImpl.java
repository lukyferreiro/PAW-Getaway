package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ImageExperienceModel;
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
public class ImageExperienceDaoImpl implements ImageExperienceDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageExperienceDaoImpl.class);

    private static final RowMapper<ImageExperienceModel> IMAGE_EXPERIENCE_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new ImageExperienceModel(rs.getLong("imgId"),
                    rs.getLong("experienceId"),
                    rs.getBoolean("isCover"));

    @Autowired
    public ImageExperienceDaoImpl(final DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("imagesExperiences");
    }

    @Override
    public ImageExperienceModel create(long imageId, long experienceId, boolean isCover) {
        final Map<String, Object> imageExperienceData = new HashMap<>();
        imageExperienceData.put("experienceId", experienceId);
        imageExperienceData.put("isCover", isCover);
        imageExperienceData.put("imgId", imageId);
        jdbcInsert.execute(imageExperienceData);

        LOGGER.info("Created new image experience with id {}", imageId);

        return new ImageExperienceModel(imageId, experienceId, isCover);
    }

    @Override
    public boolean update(long imageId, ImageExperienceModel imageExperienceModel) {
        final String query = "UPDATE imagesExperiences SET experienceId = ?, isCover = ? WHERE imgId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.update(query, imageExperienceModel.getExperienceId(), imageExperienceModel.isCover(), imageId) == 1;
    }

    @Override
    public boolean delete(long imageId) {
        final String query = "DELETE FROM imagesExperiences WHERE imgId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.update(query, imageId) == 1;
    }

    @Override
    public List<ImageExperienceModel> listAll() {
        final String query = "SELECT imgId, experienceId, isCover FROM imagesExperiences";
        LOGGER.debug("Executing query: {}", query);
        return new ArrayList<>(jdbcTemplate.query(query, IMAGE_EXPERIENCE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<ImageExperienceModel> getById(long imageId) {
        final String query = "SELECT imgId, experienceId, isCover FROM imagesExperiences WHERE imgId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{imageId}, IMAGE_EXPERIENCE_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }
}