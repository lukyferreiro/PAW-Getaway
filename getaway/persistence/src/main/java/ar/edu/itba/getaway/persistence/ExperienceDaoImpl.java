package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceDaoImpl.class);

    private static final RowMapper<Long> REVIEWS_EXPERIENCE_ROW_MAPPER = (rs,rowNum) ->
            rs.getLong("avg");

    private static final RowMapper<ExperienceModel> EXPERIENCE_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new ExperienceModel(rs.getLong("experienceid"),
                    rs.getString("experienceName"),
                    rs.getString("address"),
                    rs.getString("description"),
                    rs.getString("siteUrl"),
                    (rs.getObject("price") == null) ? null : rs.getBigDecimal("price").doubleValue(),
                    rs.getLong("cityId"),
                    rs.getLong("categoryId"),
                    rs.getLong("userId"),
                    rs.getBoolean("hasImage"));

    @Autowired
    public ExperienceDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("experiences")
                .usingGeneratedKeyColumns("experienceid");
    }

    @Override
    public ExperienceModel create(String name, String address, String description, String url,
                                  Double price, long cityId, long categoryId, long userId, boolean hasImage) {
        final Map<String, Object> experienceData = new HashMap<>();
        experienceData.put("experienceName", name);
        experienceData.put("address", address);
        experienceData.put("description", description);
        experienceData.put("siteUrl", url);
        experienceData.put("price", price);
        experienceData.put("cityId", cityId);
        experienceData.put("categoryId", categoryId);
        experienceData.put("userId",userId);
        experienceData.put("hasImage", hasImage);
        final long experienceId = jdbcInsert.executeAndReturnKey(experienceData).longValue();

        LOGGER.info("Created new experience with id {}", experienceId);

        return new ExperienceModel(experienceId, name, address, description,url, price, cityId, categoryId, userId, hasImage);
    }

    @Override
    public boolean update(long experienceId, ExperienceModel experienceModel) {
        LOGGER.debug("Executing query to update experience with id: {}", experienceId);
        return jdbcTemplate.update("UPDATE experiences " +
                "SET experienceName = ?, " +
                "address = ?, " +
                "description = ?, " +
                "siteUrl = ?, " +
                "price = ?, " +
                "cityId = ?, " +
                "categoryId = ?, " +
                "userId = ?," +
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
        final String query = "DELETE FROM experiences WHERE experienceId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.update(query, experienceId) == 1;
    }

    @Override
    public List<ExperienceModel> listAll() {
        final String query = "SELECT * FROM experiences ";
        LOGGER.debug("Executing query: {}", query);
        return new ArrayList<>(jdbcTemplate.query(query, EXPERIENCE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<ExperienceModel> getById(long experienceId) {
        final String query = "SELECT * FROM experiences WHERE experienceId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{experienceId}, EXPERIENCE_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<ExperienceModel> listByCategory(long categoryId,int page) {
        final String query = "SELECT * FROM experiences NATURAL JOIN categories WHERE categoryId = ? LIMIT ? OFFSET ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndCity(long categoryId, long cityId,int page) {
        final String query = "SELECT * FROM experiences NATURAL JOIN categories WHERE categoryId = ? AND cityId = ? LIMIT ? OFFSET ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, cityId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndPrice(long categoryId, Double max,int page) {
        final String query = "SELECT * FROM experiences WHERE categoryid = ? AND price <= ? LIMIT ? OFFSET ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, max}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndCity(long categoryId, Double max, long cityId,int page) {
        final String query = "SELECT * FROM experiences WHERE categoryid = ? AND price <= ? AND cityid = ? LIMIT ? OFFSET ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, max, cityId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> getRandom() {
        final String query = "SELECT * FROM experiences ORDER BY RANDOM() LIMIT 10";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public String getCountryCity(long experienceId){
        LOGGER.debug("Executing query to getCountryCity of experience with id: {}", experienceId);
        return jdbcTemplate.queryForObject("WITH country_city_name AS (" +
                " SELECT cityname AS city_name, countryname AS country_name, cityid FROM argentinacities, countries" +
                " WHERE argentinacities.countryid = countries.countryid )" +
                " SELECT CONCAT(country_name, ', ', city_name) FROM country_city_name WHERE cityid =" +
                " (SELECT cityid FROM experiences WHERE experienceid = ?)",
                new Object[] {experienceId}, String.class);
    }

    @Override
    public List<ExperienceModel> getByUserId(long userId,int page) {
        final String query = "SELECT * FROM experiences WHERE userid = ? LIMIT ? OFFSET ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{userId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public Optional<Long> getAvgReviews(long experienceId) {
        final String query = "SELECT avg(score) FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE experiences.experienceid = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{experienceId}, REVIEWS_EXPERIENCE_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceCityAndScore(long categoryId, Double max, long cityId, long score,int page) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND price <=? AND cityid = ? GROUP BY experiences.experienceid, reviews.reviewid HAVING avg(score)=? LIMIT ? OFFSET ? ",
                new Object[]{categoryId, max, cityId, score}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryCityAndScore(long categoryId, long cityId, long score,int page) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND cityid = ? GROUP BY experiences.experienceid, reviews.reviewid HAVING avg(score)=? LIMIT ? OFFSET ? ",
                new Object[]{categoryId, cityId, score}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndScore(long categoryId, Double max, long score,int page) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND price <=? GROUP BY experiences.experienceid, reviews.reviewid HAVING avg(score)=? LIMIT ? OFFSET ? ",
                new Object[]{categoryId, max, score}, EXPERIENCE_MODEL_ROW_MAPPER);    }

    @Override
    public List<ExperienceModel> listByCategoryAndScore(long categoryId, long score,int page) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? GROUP BY experiences.experienceid, reviews.reviewid HAVING avg(score)=? LIMIT ? OFFSET ? ",
                new Object[]{categoryId, score}, EXPERIENCE_MODEL_ROW_MAPPER);    }

    @Override
    public List<ExperienceModel> listByScore(int page,long categoryId){
        return jdbcTemplate.query("SELECT *  FROM experiences ORDER BY experienceName LIMIT ? OFFSET ? ",new Object[]{PAGE_SIZE, (page-1)*PAGE_SIZE},EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public int getTotalPagesAllExperiences() {
        int rowsCount = jdbcTemplate.queryForObject("SELECT count(*) AS experiencesCount FROM experiences" , Integer.class);
        int total = (int) Math.ceil(rowsCount/PAGE_SIZE);
        return total==0?1:total;
    }
}



