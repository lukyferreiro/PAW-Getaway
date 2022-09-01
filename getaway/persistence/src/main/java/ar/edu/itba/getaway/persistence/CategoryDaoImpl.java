package Interfaces.Necessary.Category;

import Models.Necessary.CategoryModel;

import java.util.HashMap;
import java.util.Map;
@Repository
public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert jdbcInsertCategory;

    private static final RowMapper<CategoryModel> CATEGORY_MODEL_ROW_MAPPER = (rs, rowNum) -> {
        return new CategoryModel(rs.getId(), rs.getName());
    };

    @Autowired
    public CategoryDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("categories").usingGeneratedKeyColumns("categoryId");
    }

    @Override
    public CategoryModel create(CategoryModel categoryModel) {
        final Map<String, Object> args = new HashMap<>();
        args.put("categoryName", categoryModel.getName());
        final int categoryId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new CategoryModel(categoryId, categoryModel.getName());
    }

    @Override
    public boolean update(long categoryId, CategoryModel categoryModel) {
        return jdbcTemplate.update("UPDATE categories " +
                "SET categoryName = ?" +
                "WHERE categoryId = ?", new Object[]{categoryModel.getName(), categoryId}) == 1;
    }

    @Override
    public boolean delete(long categoryId) {
        return jdbcTemplate.update("DELETE FROM categories WHERE categoryId = ?", new Object[]{categoryId}) == 1;
    }

    @Override
    public List<CategoryModel> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT categoryId,name FROM categories", CATEGORY_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<CategoryModel> getById(long categoryId) {
        return jdbcTemplate.query("SELECT categoryId, name FROM categories categoryId = ?", new Object[]{categoryId}, CATEGORY_MODEL_ROW_MAPPER).stream().findFirst();
    }
}
