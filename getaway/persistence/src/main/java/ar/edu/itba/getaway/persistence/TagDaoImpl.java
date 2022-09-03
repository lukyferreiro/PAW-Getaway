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
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<TagModel> TAG_MODEL_ROW_MAPPER =
            (rs, rowNum) -> new TagModel(rs.getLong("tagId"), rs.getString("tagName"));

    @Autowired
    public TagDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tags")
                .usingGeneratedKeyColumns("tagId");
    }

    @Override
    public TagModel create(TagModel tagModel) {
        final Map<String, Object> args = new HashMap<>();
        args.put("tagName", tagModel.getName());
        final long tagId = jdbcInsert.executeAndReturnKey(args).longValue();
        return new TagModel(tagId, tagModel.getName());
    }

    @Override
    public boolean update(long tagId, TagModel tagModel) {
        return jdbcTemplate.update("UPDATE tags SET tagName = ? WHERE tagId = ?",
                tagModel.getName(), tagId) == 1;
    }

    @Override
    public boolean delete(long tagId) {
        return jdbcTemplate.update("DELETE FROM tags WHERE tagId = ?",
                tagId) == 1;
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