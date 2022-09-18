package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
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
public class ExperienceDaoImpl implements ExperienceDao {

    @Autowired
    private DataSource ds;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<ExperienceModel> EXPERIENCE_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new ExperienceModel(rs.getLong("experienceid"),
                    rs.getString("experienceName"),
                    rs.getString("address"),
                    rs.getString("description"),
                    rs.getString("siteUrl"),
                    (rs.getObject("price")==null) ? null : rs.getBigDecimal("price").doubleValue(),
                    rs.getLong("cityId"),
                    rs.getLong("categoryId"),
                    rs.getLong("userId"),
                    rs.getBoolean("hasImage"));

    @Autowired
    public ExperienceDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("experiences")
                .usingGeneratedKeyColumns("experienceid");
    }

    @Override
    public ExperienceModel create(String name, String address, String description, String url, Double price, long cityId, long categoryId, long userId, boolean hasImage) {
        final Map<String, Object> args = new HashMap<>();
        args.put("experienceName", name);
        args.put("address", address);
        args.put("description", description);
        args.put("siteUrl", url);
        args.put("price", price);
        args.put("cityId", cityId);
        args.put("categoryId", categoryId);
        args.put("userId",userId);
        args.put("hasImage", hasImage);
        final long experienceId = jdbcInsert.executeAndReturnKey(args).longValue();
        return new ExperienceModel(experienceId, name, address, description,url, price, cityId, categoryId, userId, hasImage);
    }

    @Override
    public boolean update(long experienceId, ExperienceModel experienceModel) {
        return jdbcTemplate.update("UPDATE experiences " +
                "SET experienceName = ?, " +
                "address = ?, " +
                "description = ?, " +
                "siteUrl = ?, " +
                "price = ?, " +
                "cityId = ?, " +
                "categoryId = ?, " +
                "userId = ?" +
                "hasImage = ?" +
                "WHERE experienceId = ?",
                experienceModel.getName(), experienceModel.getAddress(),
                experienceModel.getDescription(), experienceModel.getSiteUrl(),
                experienceModel.getPrice(),
                experienceModel.getCityId(), experienceModel.getCategoryId(),
                experienceModel.getUserId(), experienceModel.isHasImage(),
                experienceId) == 1;
    }

    @Override
    public boolean delete(long experienceId) {
        return jdbcTemplate.update("DELETE FROM experiences WHERE experienceId = ?",
                experienceId) == 1;
    }

    @Override
    public List<ExperienceModel> listAll() {
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT * FROM experiences",
                EXPERIENCE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<ExperienceModel> getById(long experienceId) {
        return jdbcTemplate.query("SELECT * FROM experiences WHERE experienceId = ?",
                new Object[]{experienceId}, EXPERIENCE_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<ExperienceModel> listByCategory(long categoryId) {
        return jdbcTemplate.query("SELECT * FROM experiences NATURAL JOIN categories WHERE categoryId = ?",
                new Object[]{categoryId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndCity(long categoryId, long cityId) {
        return jdbcTemplate.query("SELECT * FROM experiences NATURAL JOIN categories WHERE categoryId = ? AND cityId = ?",
                new Object[]{categoryId, cityId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndPrice(long categoryId,Double max){
        return jdbcTemplate.query("SELECT * FROM experiences WHERE categoryid = ? AND price <= ? ", new Object[]{categoryId,max}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndCity(long categoryId, Double max, long cityId) {
        return jdbcTemplate.query("SELECT * FROM experiences WHERE categoryid = ? AND price <= ? AND cityid = ?", new Object[]{categoryId,max, cityId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> getRandom(){
        return jdbcTemplate.query("SELECT * FROM experiences ORDER BY RANDOM() LIMIT 5", EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public String getCountryCity(long experienceId){
        return jdbcTemplate.queryForObject("WITH country_city_name AS ( SELECT cityname AS city_name, countryname AS country_name, cityid FROM argentinacities, countries WHERE argentinacities.countryid = countries.countryid )" +
                "SELECT CONCAT(country_name, ', ', city_name) FROM country_city_name WHERE cityid = (SELECT cityid FROM experiences WHERE experienceid = ?)", new Object[] {experienceId}, String.class);
    }
}