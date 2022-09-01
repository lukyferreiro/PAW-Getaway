package Interfaces.Necessary.City;

import Models.Necessary.CategoryModel;
import Models.Necessary.CityModel;
import Models.Necessary.CountryModel;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CityDaoImpl {
    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<CountryModel> CITY_MODEL_ROW_MAPPER = (rs, rowNum) -> new CityModel(rs.getId(), rs.getCountryId(),rs.getName());

    @Autowired
    public CityDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("cities").usingGeneratedKeyColumns("cityId");
    }

    @Override
    public CityModel create(CityModel cityModel) {
        final Map<String, Object> args = new HashMap<>();
        args.put("countryId", cityModel.getCountryId());
        args.put("cityName", cityModel.getName());
        final int cityId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new CityModel(cityId, cityModel.getCountryId(), cityModel.getName());
    }

    @Override
    public boolean update(int cityId, CityModel cityModel) {
        return jdbcTemplate.update("UPDATE cities " +
                "SET cityName = ?, countryId = ?" +
                "WHERE countryId = ?", new Object[]{cityModel.getName(), cityModel.getCountryId(), cityId}) == 1;
    }

    @Override
    public boolean delete(int cityId) {
        return jdbcTemplate.update("DELETE FROM cities WHERE cityId = ?", new Object[]{cityId}) == 1;
    }

    @Override
    public Optional<CityModel> getById(long cityId) {
        return jdbcTemplate.query("SELECT * FROM countries WHERE cityId = ?",new Object[]{cityId},CITY_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<CityModel> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM countries", CITY_MODEL_ROW_MAPPER));
    }
}