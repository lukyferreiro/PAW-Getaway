package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.exceptions.DuplicateImageException;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ImageExperienceModel;
import ar.edu.itba.getaway.models.ImageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    private ImageDao imageDao;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert imageSimplejdbcInsert;
    private final SimpleJdbcInsert imageExperienceSimplejdbcInsert;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceDaoImpl.class);

    private static final RowMapper<Long> REVIEWS_EXPERIENCE_ROW_MAPPER = (rs,rowNum) ->
            rs.getLong("avg_score");

    private final RowMapper<ExperienceModel> EXPERIENCE_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new ExperienceModel(rs.getLong("experienceid"),
                    rs.getString("experienceName"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getString("description"),
                    rs.getString("siteUrl"),
                    (rs.getObject("price") == null) ? null : rs.getBigDecimal("price").doubleValue(),
                    rs.getLong("cityId"),
                    rs.getLong("categoryId"),
                    rs.getLong("userId"),
                    getImageIdByExperienceId(rs.getLong("experienceid")),
                    getHasImage(rs.getLong("experienceid")));

    private Long getImageIdByExperienceId(long experienceId){
        return imageDao.getImgByExperienceId(experienceId).get().getId();
    }
    private boolean getHasImage(long experienceId){
        return imageDao.getImgByExperienceId(experienceId).get().getImage() != null;
    }

    @Autowired
    public ExperienceDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("experiences")
                .usingGeneratedKeyColumns("experienceid");
        this.imageSimplejdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("images")
                .usingGeneratedKeyColumns("imgid");
        this.imageExperienceSimplejdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("imagesExperiences");
    }

    @Override
    public ExperienceModel create(String name, String address, String description, String email, String url,
                                  Double price, long cityId, long categoryId, long userId, byte[] image) throws DuplicateImageException {
        final Map<String, Object> experienceData = new HashMap<>();
        experienceData.put("experienceName", name);
        experienceData.put("address", address);
        experienceData.put("description", description);
        experienceData.put("email", email);
        experienceData.put("siteUrl", url);
        experienceData.put("price", price);
        experienceData.put("cityId", cityId);
        experienceData.put("categoryId", categoryId);
        experienceData.put("userId", userId);

        final long experienceId = jdbcInsert.executeAndReturnKey(experienceData).longValue();
        final ImageExperienceModel imageExperienceModel = imageDao.createExperienceImg(image, experienceId, true);

        LOGGER.info("Created new experience with id {}", experienceId);

        return new ExperienceModel(experienceId, name, address, description, email, url, price, cityId, categoryId, userId, imageExperienceModel.getImageId(), image!=null);
    }

    @Override
    public boolean update(ExperienceModel experienceModel, byte[] image) {
        LOGGER.debug("Executing query to update experience with id: {}", experienceModel.getExperienceId());
        imageDao.updateImg(image, experienceModel.getImageExperienceId());
        return jdbcTemplate.update("UPDATE experiences " +
                "SET experienceName = ?, " +
                "price = ?, " +
                "address = ?, " +
                "email = ?, " +
                "description = ?, " +
                "siteUrl = ?, " +
                "cityId = ?, " +
                "categoryId = ?," +
                "userId = ?" +
                "WHERE experienceId = ?",
                experienceModel.getExperienceName(), experienceModel.getPrice(),
                experienceModel.getAddress(), experienceModel.getEmail(),
                experienceModel.getDescription(), experienceModel.getSiteUrl(),
                experienceModel.getCityId(), experienceModel.getCategoryId(),
                experienceModel.getUserId(),
                experienceModel.getExperienceId()) == 1;
    }

    @Override
    public boolean delete(long experienceId) {
        final String query = "DELETE FROM experiences WHERE experienceId = ?";
        LOGGER.debug("Executing query: {}", query);
        Optional<ExperienceModel> experienceModelOptional = getById(experienceId);
        imageDao.deleteImg(experienceModelOptional.get().getImageExperienceId());
        return jdbcTemplate.update(query, experienceId) == 1;
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

//    @Override
//    public List<ExperienceModel> getRandom() {
//        final String query = "SELECT * FROM experiences ORDER BY RANDOM() LIMIT 10";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, EXPERIENCE_MODEL_ROW_MAPPER);
//    }

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
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? GROUP BY experiences.experienceid HAVING AVG(score)>=? ",
                new Object[]{categoryId, score}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryCityAndScore(long categoryId, long cityId, long score) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND cityid = ? GROUP BY experiences.experienceid HAVING AVG(score)>=? ",
                new Object[]{categoryId, cityId, score}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceAndScore(long categoryId, Double max, long score) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND price <=? GROUP BY experiences.experienceid HAVING AVG(score)>=? ",
                new Object[]{categoryId, max, score}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listByCategoryPriceCityAndScore(long categoryId, Double max, long cityId, long score) {
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND price <=? AND cityid = ? GROUP BY experiences.experienceid HAVING AVG(score)>=? ",
                new Object[]{categoryId, max, cityId, score}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

}