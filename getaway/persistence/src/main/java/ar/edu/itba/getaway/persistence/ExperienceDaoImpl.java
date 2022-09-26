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
            rs.getLong("avg_score");

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
        Optional<ExperienceModel> experienceModelOptional = getById(experienceId);
        deleteImgOfExperience(experienceModelOptional.get().getId());
        return jdbcTemplate.update(query, experienceId) == 1;
    }

    private void deleteImgOfExperience(long imgId){
        final String query = "DELETE FROM images WHERE imgId = ?";
        LOGGER.debug("Executing query: {}", query);
        jdbcTemplate.update(query, imgId);
    }

    @Override
    public List<ExperienceModel> listAll() {
        final String query = "SELECT * FROM experiences";
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
    public List<ExperienceModel> listByCategory(long categoryId) {
        final String query = "SELECT * FROM experiences NATURAL JOIN categories WHERE categoryId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndCity(long categoryId, long cityId) {
        final String query = "SELECT * FROM experiences NATURAL JOIN categories WHERE categoryId = ? AND cityId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, cityId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndPrice(long categoryId, Double max) {
        final String query = "SELECT * FROM experiences WHERE categoryid = ? AND price <= ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, max}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndCity(long categoryId, Double max, long cityId) {
        final String query = "SELECT * FROM experiences WHERE categoryid = ? AND price <= ? AND cityid = ?";
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
    public List<ExperienceModel> getByUserId(long userId) {
        final String query = "SELECT * FROM experiences WHERE userid = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{userId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public Optional<Long> getAvgReviews(long experienceId) {
        final String query = "SELECT AVG(score) as avg_score FROM reviews WHERE experienceid = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{experienceId}, REVIEWS_EXPERIENCE_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<ExperienceModel> listByCategoryAndScore(long categoryId, long score) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? GROUP BY experiences.experienceid HAVING AVG(score)>=? ",
                new Object[]{categoryId, score}, EXPERIENCE_MODEL_ROW_MAPPER);
    }


    @Override
    public List<ExperienceModel> getByUserIdOrderBy(long id, String order) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE experiences.userid = ?  GROUP BY experiences.experienceid ORDER BY ? ", new Object[]{id, order }, EXPERIENCE_MODEL_ROW_MAPPER);
    }


    @Override
    public List<ExperienceModel> getByUserIdOrderByDesc(long id, String order) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE experiences.userid = ?  GROUP BY experiences.experienceid ORDER BY ? DESC ", new Object[]{id, order }, EXPERIENCE_MODEL_ROW_MAPPER);
    }


    @Override
    public List<ExperienceModel> getOrderByDesc(String order) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid GROUP BY experiences.experienceid ORDER BY ? DESC", new Object[]{order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> getOrderBy(String order) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid  GROUP BY experiences.experienceid ORDER BY ?", new Object[]{order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryOrderBy(long categoryId, String order) {
        final String query = "SELECT * FROM experiences NATURAL JOIN categories WHERE categoryId = ? ORDER BY ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryOrderByDesc(long categoryId, String order) {
        final String query = "SELECT * FROM experiences NATURAL JOIN categories WHERE categoryId = ? ORDER BY ? DESC";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, order}, EXPERIENCE_MODEL_ROW_MAPPER);        }

    @Override
    public List<ExperienceModel> listByCategoryAndCityOrderBy(long categoryId, long cityId, String order) {
        final String query = "SELECT * FROM experiences NATURAL JOIN categories WHERE categoryId = ? AND cityId = ? ORDER BY ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, cityId, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndCityOrderByDesc(long categoryId, long cityId, String order) {
        final String query = "SELECT * FROM experiences NATURAL JOIN categories WHERE categoryId = ? AND cityId = ? ORDER BY ? DESC";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, cityId, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndPriceOrderBy(long categoryId, Double max, String order) {
        final String query = "SELECT * FROM experiences WHERE categoryid = ? AND price <= ? ORDER BY ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, max, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndPriceOrderByDesc(long categoryId, Double max, String order) {
        final String query = "SELECT * FROM experiences WHERE categoryid = ? AND price <= ? ORDER BY ? DESC ";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, max, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndCityOrderBy(long categoryId, Double max, long cityId, String order) {
        final String query = "SELECT * FROM experiences WHERE categoryid = ? AND price <= ? AND cityid = ? ORDER BY ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, max, cityId, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndCityOrderByDesc(long categoryId, Double max, long cityId, String order) {
        final String query = "SELECT * FROM experiences WHERE categoryid = ? AND price <= ? AND cityid = ? ORDER BY ? DESC ";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{categoryId, max, cityId, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceCityAndScoreOrderBy(long categoryId, Double max, long cityId, long score, String order) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND price <=? AND cityid = ? GROUP BY experiences.experienceid HAVING AVG(score)>=? ORDER BY ?",
                new Object[]{categoryId, max, cityId, score, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceCityAndScoreOrderByDesc(long categoryId, Double max, long cityId, long score, String order) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND price <=? AND cityid = ? GROUP BY experiences.experienceid HAVING AVG(score)>=? ORDER BY ? DESC ",
                new Object[]{categoryId, max, cityId, score, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryCityAndScoreOrderBy(long categoryId, long cityId, long score, String order) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND cityid = ? GROUP BY experiences.experienceid HAVING AVG(score)>=? ORDER BY ?",
                new Object[]{categoryId, cityId, score, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryCityAndScoreOrderByDesc(long categoryId, long cityId, long score, String order) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND cityid = ? GROUP BY experiences.experienceid HAVING AVG(score)>=? ORDER BY ? DESC",
                new Object[]{categoryId, cityId, score, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndScoreOrderBy(long categoryId, Double max, long score, String order) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND price <=? GROUP BY experiences.experienceid HAVING AVG(score)>=? ORDER BY ?",
                new Object[]{categoryId, max, score, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndScoreOrderByDesc(long categoryId, Double max, long score, String order) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND price <=? GROUP BY experiences.experienceid HAVING AVG(score)>=? ORDER BY ? DESC ",
                new Object[]{categoryId, max, score, order}, EXPERIENCE_MODEL_ROW_MAPPER);    }

    @Override
    public List<ExperienceModel> listByCategoryAndScoreOrderBy(long categoryId, long score, String order) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? GROUP BY experiences.experienceid HAVING AVG(score)>=? ORDER BY ? ",
                new Object[]{categoryId, score, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryAndScoreOrderByDesc(long categoryId, long score, String order) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? GROUP BY experiences.experienceid HAVING AVG(score)>=? ORDER BY ? DESC ",
                new Object[]{categoryId, score, order}, EXPERIENCE_MODEL_ROW_MAPPER);
    }


    @Override
    public List<ExperienceModel> listByCategoryCityAndScore(long categoryId, long cityId, long score) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND cityid = ? GROUP BY experiences.experienceid HAVING AVG(score)>=? ",
                new Object[]{categoryId, cityId, score}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndScore(long categoryId, Double max, long score) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND price <=? GROUP BY experiences.experienceid HAVING AVG(score)>=? ",
                new Object[]{categoryId, max, score}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceCityAndScore(long categoryId, Double max, long cityId, long score) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, siteUrl, price, cityId, categoryId, experiences.userId, hasImage FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND price <=? AND cityid = ? GROUP BY experiences.experienceid HAVING AVG(score)>=? ",
                new Object[]{categoryId, max, cityId, score}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

}