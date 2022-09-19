package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CountryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;


@Repository
public class CountryDaoImpl implements CountryDao{

    @Autowired
    private DataSource ds;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<CountryModel> COUNTRY_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new CountryModel(rs.getLong("countryId"),
                    rs.getString("countryName"));

    @Autowired
    public CountryDaoImpl(final DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("countries")
                .usingGeneratedKeyColumns("countryId");
    }

    @Override
    public Optional<CountryModel> getById(long countryId) {
        return jdbcTemplate.query("SELECT * FROM countries WHERE countryId = ?",
                new Object[]{countryId},COUNTRY_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<CountryModel> listAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM countries ORDER BY countryname ASC",
                COUNTRY_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<CountryModel> getIdByCountryName(String country){
     return jdbcTemplate.query("SELECT countryId FROM countries WHERE countryName = ?",
             new Object[]{country},COUNTRY_MODEL_ROW_MAPPER).stream().findFirst();
    }

}

