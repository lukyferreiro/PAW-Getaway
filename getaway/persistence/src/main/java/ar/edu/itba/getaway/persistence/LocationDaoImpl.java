package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;
import ar.edu.itba.interfaces.persistence.LocationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LocationDaoImpl implements LocationDao {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationDao.class);

    private static final RowMapper<CityModel> CITY_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new CityModel(rs.getLong("cityId"),
                    rs.getLong("countryId"),
                    rs.getString("cityName"));

    private static final RowMapper<CountryModel> COUNTRY_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new CountryModel(rs.getLong("countryId"),
                    rs.getString("countryName"));

    @Autowired
    public LocationDaoImpl(final DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<CityModel> listAllCities() {
        final String query = "SELECT * FROM cities ORDER BY cityname ASC";
        LOGGER.debug("Executing query: {}", query);
        return new ArrayList<>(jdbcTemplate.query(query, CITY_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<CityModel> getCityById(Long cityId) {
        final String query = "SELECT * FROM cities WHERE cityId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{cityId}, CITY_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<CityModel> getCitiesByCountryId(Long countryId) {
        final String query = "SELECT * FROM cities WHERE countryid = ? ORDER BY cityname ASC";
        LOGGER.debug("Executing query: {}", query);
        return new ArrayList<>(jdbcTemplate.query(query, new Object[]{countryId}, CITY_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<CityModel> getCityByName(String cityName) {
        final String query = "SELECT * FROM cities WHERE cityname = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{cityName}, CITY_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<CountryModel> listAllCountries() {
        final String query = "SELECT * FROM countries ORDER BY countryname ASC";
        LOGGER.debug("Executing query: {}", query);
        return new ArrayList<>(jdbcTemplate.query(query, COUNTRY_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<CountryModel> getCountryById(Long countryId) {
        final String query = "SELECT * FROM countries WHERE countryId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{countryId}, COUNTRY_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Optional<CountryModel> getCountryByName(String countryName) {
        final String query = "SELECT * FROM countries WHERE countryname = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{countryName}, COUNTRY_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }
}
