package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CityDaoImpl implements CityDao {

    @Autowired
    private DataSource ds;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<CityModel> CITY_MODEL_ROW_MAPPER =
            (rs, rowNum) -> new CityModel(rs.getLong("cityId"), rs.getLong("countryId"),rs.getString("cityName"));

    @Autowired
    public CityDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cities")
                .usingGeneratedKeyColumns("cityId");
    }

    @Override
    public CityModel create(CityModel cityModel) {
        final Map<String, Object> args = new HashMap<>();
        args.put("countryId", cityModel.getCountryId());
        args.put("cityName", cityModel.getName());
        final long cityId = jdbcInsert.executeAndReturnKey(args).longValue();
        return new CityModel(cityId, cityModel.getCountryId(), cityModel.getName());
    }

    @Override
    public boolean update(long cityId, CityModel cityModel) {
        return jdbcTemplate.update("UPDATE cities SET cityName = ?, countryId = ? WHERE countryId = ?",
                cityModel.getName(), cityModel.getCountryId(), cityId) == 1;
    }

    @Override
    public boolean delete(long cityId) {
        return jdbcTemplate.update("DELETE FROM cities WHERE cityId = ?",
                cityId) == 1;
    }

    @Override
    public Optional<CityModel> getById(long cityId) {
        return jdbcTemplate.query("SELECT * FROM cities WHERE cityId = ?",
                new Object[]{cityId},CITY_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<CityModel> listAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM cities ORDER BY cityname ASC ", CITY_MODEL_ROW_MAPPER));
    }

    @Override
    public List<CityModel> getByCountryId(long countryId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM cities WHERE countryid = ? ORDER BY cityname ASC",new Object[]{countryId}, CITY_MODEL_ROW_MAPPER));

    }
}