package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private DataSource ds;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<CategoryModel> CATEGORY_MODEL_ROW_MAPPER =
            (rs, rowNum) -> new CategoryModel(rs.getLong("categoryId"), rs.getString("categoryName"));

    @Autowired
    public CategoryDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("categories")
                .usingGeneratedKeyColumns("categoryId");
    }

    @Override
    public CategoryModel create(CategoryModel categoryModel) {
        final Map<String, Object> args = new HashMap<>();
        args.put("categoryName", categoryModel.getName());
        final long categoryId = jdbcInsert.executeAndReturnKey(args).longValue();
        return new CategoryModel(categoryId, categoryModel.getName());
    }

    @Override
    public boolean update(long categoryId, CategoryModel categoryModel) {
        return jdbcTemplate.update("UPDATE categories SET categoryName = ? WHERE categoryId = ?",
                categoryModel.getName(), categoryId) == 1;
    }

    @Override
    public boolean delete(long categoryId) {
        return jdbcTemplate.update("DELETE FROM categories WHERE categoryId = ?",
                categoryId) == 1;
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
        return jdbcTemplate.query("SELECT categoryId, categoryName FROM categories WHERE categoryName = ?", new Object[]{categoryName}, CATEGORY_MODEL_ROW_MAPPER).stream().findFirst();
    }


}
