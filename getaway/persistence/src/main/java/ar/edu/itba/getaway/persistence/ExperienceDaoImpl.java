package Interfaces.Necessary.Experience;
import Models.Necessary.ExperienceModel;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ExperienceDaoImpl implements ExperienceDao {
    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert jdbcInsertCategory;

    private static final RowMapper<ExperienceModel> EXPERIENCE_MODEL_ROW_MAPPER = (rs, rowNum) -> {
        return new ExperienceModel(rs.getId(), rs.getName(), rs.getAddress(), rs.getDescription(), rs.getPrice(), rs.getCityId(), rs.getCategoryId(), rs.getUserId());
    };

    @Autowired
    public ExperienceDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("experiences").usingGeneratedKeyColumns("experienceId");
    }

    @Override
    public ExperienceModel create(ExperienceModel experienceModel) {
        final Map<String, Object> args = new HashMap<>();
        args.put("experienceName", experienceModel.getName());
        args.put("address", experienceModel.getAddress());
        args.put("description", experienceModel.getDescription());
        args.put("cityId", experienceModel.getCityId());
        args.put("categoryId", experienceModel.getCategoryId());
        args.put("price", experienceModel.getPrice());
        args.put("userId",experienceModel.getUserId());
        final int experienceId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new ExperienceModel(experienceId,experienceModel.getName(), experienceModel.getAddress(), experienceModel.getDescription(),  experienceModel.getPrice(),experienceModel.getCityId(), experienceModel.getCategoryId(), experienceModel.getUserId());
    }

    @Override
    public boolean update(long experienceId, ExperienceModel experienceModel) {
        return jdbcTemplate.update("UPDATE experiences " +
                "SET experienceName = ?, " +
                "address = ?, " +
                "description = ?, " +
                "cityId = ?, " +
                "categoryId = ?, " +
                "price = ?, " +
                "userId = ?" +
                "WHERE experienceId = ?", new Object[]{experienceModel.getName(), experienceModel.getDescription(), experienceModel.getCityId(), experienceModel.getCategoryId(), experienceModel.getPrice(), experienceModel.getUserId(), experienceId}) == 1;
    }

    @Override
    public boolean delete(long experienceId) {
        return jdbcTemplate.update("DELETE FROM experiences WHERE experienceId = ?", new Object[]{experienceId}) == 1;
    }

    @Override
    public List<ExperienceModel> list() {
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT experienceId, experienceName, address, description, cityId, categoryId, price, userId " +
                        "FROM experiences", EXPERIENCE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<ExperienceModel> getById(long experienceId) {
        return jdbcTemplate.query("SELECT experienceId, experienceName, address, description, cityId, categoryId, price, userId FROM experiences WHERE experienceId = ?", new Object[]{experienceId}, EXPERIENCE_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<ExperienceModel> listByCategory(long categoryId) {
        return jdbcTemplate.query("SELECT experienceId, experienceName, address, description, cityId, categoryId, price, userId FROM experiences NATURAL JOIN categories WHERE categoryId = ?", new Object[]{categoryId}, EXPERIENCE_MODEL_ROW_MAPPER).stream().findFirst();
    }
}