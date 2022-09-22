package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CategoryModel;
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
public class CategoryDaoImpl implements CategoryDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDaoImpl.class);

    private static final RowMapper<CategoryModel> CATEGORY_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new CategoryModel(rs.getLong("categoryId"),
                    rs.getString("categoryName"));

    @Autowired
    public CategoryDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("categories")
                .usingGeneratedKeyColumns("categoryId");
    }

    @Override
    public List<CategoryModel> listAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT categoryId, categoryName FROM categories",
                CATEGORY_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<CategoryModel> getById(long categoryId) {
        return jdbcTemplate.query("SELECT categoryId, categoryName FROM categories WHERE categoryId = ?",
                new Object[]{categoryId}, CATEGORY_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<CategoryModel> getByName(String categoryName){
        return jdbcTemplate.query("SELECT categoryId, categoryName FROM categories WHERE categoryName = ?",
                new Object[]{categoryName}, CATEGORY_MODEL_ROW_MAPPER).stream().findFirst();
    }


}
