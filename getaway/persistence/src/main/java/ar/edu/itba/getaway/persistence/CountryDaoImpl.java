package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CountryModel;
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
public class CountryDaoImpl implements CountryDao{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<CountryModel> COUNTRY_MODEL_ROW_MAPPER =
            (rs, rowNum) -> new CountryModel(rs.getLong("countryId"),rs.getString("countryName"));

    @Autowired
    public CountryDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("countries")
                .usingGeneratedKeyColumns("countryId");
    }

    @Override
    public CountryModel create(CountryModel countryModel) {
        final Map<String, Object> args = new HashMap<>();
        args.put("countryName", countryModel.getName());
        final long countryId = jdbcInsert.executeAndReturnKey(args).longValue();
        return new CountryModel(countryId, countryModel.getName());
    }

    @Override
    public boolean update(long countryId, CountryModel countryModel) {
        return jdbcTemplate.update("UPDATE countries SET countryName = ? WHERE countryId = ?",
                countryModel.getName(), countryId) == 1;
    }

    @Override
    public boolean delete(long countryId) {
        return jdbcTemplate.update("DELETE FROM countries WHERE countryId = ?",
                countryId) == 1;
    }

    @Override
    public Optional<CountryModel> getById(long countryId) {
        return jdbcTemplate.query("SELECT * FROM countries WHERE id = ?",
                new Object[]{countryId},COUNTRY_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<CountryModel> listAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM countries",
                COUNTRY_MODEL_ROW_MAPPER));
    }

}

