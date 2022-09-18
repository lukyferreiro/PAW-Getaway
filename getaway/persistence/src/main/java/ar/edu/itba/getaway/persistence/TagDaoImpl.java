package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.TagModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class TagDaoImpl implements TagDao {

    @Autowired
    private DataSource ds;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<TagModel> TAG_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new TagModel(rs.getLong("tagId"),
                    rs.getString("tagName"));

    @Autowired
    public TagDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tags")
                .usingGeneratedKeyColumns("tagId");
    }

    @Override
    public List<TagModel> listAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT tagId, tagName FROM tags",
                TAG_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<TagModel> getById(long tagId) {
        return jdbcTemplate.query("SELECT tagId, tagName FROM tags WHERE tagId = ?",
                new Object[]{tagId}, TAG_MODEL_ROW_MAPPER).stream().findFirst();
    }
}