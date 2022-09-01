package Interfaces.Necessary.Country;
@Autowired
public CountryDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("countries").usingGeneratedKeyColumns("categoryId");
        }

@Override
public CountryModel create(CountryModel countryModel) {
final Map<String, Object> args = new HashMap<>();
        args.put("countryName", countryModel.getName());
final int countryId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new CountryModel(countryId, countryModel.getName());
        }

@Override
public boolean update(int countryId, CountryModel countryModel) {
        return jdbcTemplate.update("UPDATE countries " +
        "SET countryName = ?" +
        "WHERE countryId = ?", new Object[]{countryModel.getName(), countryId}) == 1;
        }

@Override
public boolean delete(int countryId) {
        return jdbcTemplate.update("DELETE FROM countries WHERE countryId = ?", new Object[]{countryId}) == 1;
        }

@Override
public Optional<CountryModel> getById(long countryId) {
        return jdbcTemplate.query("SELECT * FROM countries WHERE id = ?",new Object[]{countryId},COUNTRY_MODEL_ROW_MAPPER).stream().findFirst();
        }

@Override
public List<CountryModel> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM countries", COUNTRY_MODEL_ROW_MAPPER));
        }
        }

        import Models.Necessary.CategoryModel;
        import Models.Necessary.CountryModel;

        import java.util.HashMap;
        import java.util.Map;

@Repository
public class CountryDaoImpl implements CountryDao{
    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<CountryModel> COUNTRY_MODEL_ROW_MAPPER = (rs, rowNum) -> new CountryModel(rs.getId(),rs.getName());

