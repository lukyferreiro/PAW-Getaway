package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CityModel;
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
public class CityDaoImpl implements CityDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final Logger LOGGER = LoggerFactory.getLogger(CityDaoImpl.class);

    private static final RowMapper<CityModel> CITY_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new CityModel(rs.getLong("cityId"),
                    rs.getLong("countryId"),
                    rs.getString("cityName"));

    @Autowired
    public CityDaoImpl(final DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("argentinaCities")
                .usingGeneratedKeyColumns("cityId");
    }

    @Override
    public Optional<CityModel> getById(long cityId) {
        final String query = "SELECT * FROM argentinaCities WHERE cityId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{cityId}, CITY_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<CityModel> listAll() {
        final String query = "SELECT * FROM argentinaCities ORDER BY cityname ASC";
        LOGGER.debug("Executing query: {}", query);
        return new ArrayList<>(jdbcTemplate.query(query, CITY_MODEL_ROW_MAPPER));
    }

    @Override
    public List<CityModel> getByCountryId(long countryId) {
        final String query = "SELECT * FROM argentinaCities WHERE countryid = ? ORDER BY cityname ASC";
        LOGGER.debug("Executing query: {}", query);
        return new ArrayList<>(jdbcTemplate.query(query, new Object[]{countryId}, CITY_MODEL_ROW_MAPPER));

    }

    @Override
    public Optional<CityModel> getIdByName(String cityName) {
        final String query = "SELECT * FROM argentinaCities WHERE cityname = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{cityName}, CITY_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }
}