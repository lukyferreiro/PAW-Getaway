package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ImageExperienceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ImageExperienceDaoImpl implements ImageExperienceDao {

    @Autowired
    private DataSource ds;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

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
        return new ImageExperienceModel(imageId, experienceId, isCover);
    }

    @Override
    public boolean update(long imageId, ImageExperienceModel imageExperienceModel) {
        return jdbcTemplate.update("UPDATE imagesExperiences SET experienceId = ?, isCover = ? WHERE imgId = ?",
                imageExperienceModel.getExperienceId(), imageExperienceModel.isCover(), imageId) == 1;
    }
    @Override
    public boolean delete(long imageId) {
        return jdbcTemplate.update("DELETE FROM imagesExperiences WHERE imgId = ?",
                imageId) == 1;
    }

    @Override
    public List<ImageExperienceModel> listAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT imgId, experienceId, isCover FROM imagesExperiences",
                IMAGE_EXPERIENCE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<ImageExperienceModel> getById(long imageId) {
        return jdbcTemplate.query("SELECT imgId, experienceId, isCover FROM imagesExperiences WHERE imgId = ?",
                new Object[]{imageId}, IMAGE_EXPERIENCE_MODEL_ROW_MAPPER).stream().findFirst();
    }
}