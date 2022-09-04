package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ImageDaoImpl implements ImageDao {

    @Autowired
    private DataSource ds;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<ImageModel> IMAGE_MODEL_ROW_MAPPER =
            (rs, rowNum) -> new ImageModel(rs.getLong("imageId"),
                    (MultipartFile) rs.getObject("image"));
    //TODO check MultipartFile

    @Autowired
    public ImageDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("images")
                .usingGeneratedKeyColumns("imageId");
    }

    @Override
    public ImageModel create(ImageModel imageModel) {
        final Map<String, Object> args = new HashMap<>();
        args.put("image", imageModel.getImage());
        final long imageId = jdbcInsert.executeAndReturnKey(args).longValue();
        return new ImageModel(imageId, imageModel.getImage());
    }
    @Override
    public boolean update(long imageId, ImageModel imageModel) {
        return jdbcTemplate.update("UPDATE images SET image = ? WHERE imageId = ?",
                imageModel.getImage(), imageId) == 1;
    }

    @Override
    public boolean delete(long imageId) {
        return jdbcTemplate.update("DELETE FROM images WHERE imageId = ?",
                imageId) == 1;
    }

    @Override
    public List<ImageModel> listAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT imageId, image FROM images",
                IMAGE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<ImageModel> getById(long imageId) {
        return jdbcTemplate.query("SELECT imageId, image FROM images WHERE imageId = ?",
                new Object[]{imageId}, IMAGE_MODEL_ROW_MAPPER).stream().findFirst();
    }
}