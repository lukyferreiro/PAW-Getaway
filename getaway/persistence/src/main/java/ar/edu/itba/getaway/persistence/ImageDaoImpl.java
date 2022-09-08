package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Repository
public class ImageDaoImpl implements ImageDao {

    @Autowired
    private DataSource ds;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<ImageModel> IMAGE_MODEL_ROW_MAPPER =
            (rs, rowNum) -> new ImageModel(rs.getLong("imgid"),
                    (MultipartFile) rs.getObject("image"));
    //TODO check MultipartFile

    @Autowired
    public ImageDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("images")
                .usingGeneratedKeyColumns("imgid");
    }

    @Override
    public ImageModel create(MultipartFile image) throws Exception {
        final Map<String, Object> args = new HashMap<>();

        byte [] byteArr= new byte[0];
        try {
            byteArr = image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream inputStream = new ByteArrayInputStream(byteArr);

        if (inputStream != null){
            args.put("image", inputStream);
        }
        else{
            throw new Exception("Fallo carga de imagen");
        }


        final long imgid = jdbcInsert.executeAndReturnKey(args).longValue();
        return new ImageModel(imgid, image);
    }
    @Override
    public boolean update(long imgid, ImageModel imageModel) {
        return jdbcTemplate.update("UPDATE images SET image = ? WHERE imgid = ?",
                imageModel.getImage(), imgid) == 1;
    }

    @Override
    public boolean delete(long imageId) {
        return jdbcTemplate.update("DELETE FROM images WHERE imgid = ?",
                imageId) == 1;
    }

    @Override
    public List<ImageModel> listAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT imgid, image FROM images",
                IMAGE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<ImageModel> getById(long imgid) {
        return jdbcTemplate.query("SELECT imgid, image FROM images WHERE imgid = ?",
                new Object[]{imgid}, IMAGE_MODEL_ROW_MAPPER).stream().findFirst();
    }
}