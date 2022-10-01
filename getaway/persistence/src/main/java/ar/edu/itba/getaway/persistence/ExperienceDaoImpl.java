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
    @Autowired
    private ImageDao imageDao;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceDaoImpl.class);

    private static final RowMapper<Double> PRICE_EXPERIENCE_ROW_MAPPER = (rs,rowNum) ->
            rs.getDouble("max_price");

    private final RowMapper<ExperienceModel> EXPERIENCE_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new ExperienceModel(rs.getLong("experienceid"),
                    rs.getString("experienceName"),
                    rs.getString("address"),
                    rs.getString("description"),
                    rs.getString("email"),
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
//        this.imageSimplejdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
//                .withTableName("images")
//                .usingGeneratedKeyColumns("imgid");
//        this.imageExperienceSimplejdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
//                .withTableName("imagesExperiences");
    }

    @Override
    public ExperienceModel create(String name, String address, String description, String email, String url,
                                  Double price, long cityId, long categoryId, long userId) {
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

        LOGGER.info("Created new experience with id {}", experienceId);

        return new ExperienceModel(experienceId, name, address, description, email, url, price, cityId, categoryId, userId, null, false);
    }

    @Override
    public boolean update(ExperienceModel experienceModel) {
        LOGGER.debug("Executing query to update experience with id: {}", experienceModel.getExperienceId());
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
        return jdbcTemplate.update(query, experienceId) == 1;
    }

    @Override
    public List<ExperienceModel> listAll(String order) {
        final String query = "SELECT * FROM experiences" + order;
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
    public List<ExperienceModel> listByUserId(long userId, String order) {
        final String query = "SELECT * FROM experiences WHERE userid = ?" + order;
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{userId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public Optional<Double> getMaxPrice(long categoryId){
        return jdbcTemplate.query("SELECT MAX(COALESCE(price,0)) as max_price FROM experiences WHERE categoryid = ?", new Object[]{categoryId}, PRICE_EXPERIENCE_ROW_MAPPER ).stream().findFirst();
    }

    @Override
    public List<ExperienceModel> listByFilter(long categoryId, Double max, long score, Long city, String order, int page, int page_size) {
        if (city > 0){
            return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND COALESCE(price,0) <=? AND cityId = ? " +
                            "GROUP BY experiences.experienceid HAVING AVG(COALESCE(score,0))>=?" + order
                            + " LIMIT ? OFFSET ?",
                    new Object[]{categoryId, max, city, score, page_size, (page-1)*page_size}, EXPERIENCE_MODEL_ROW_MAPPER);
        }
        else {
            return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND COALESCE(price,0) <=? " +
                            "GROUP BY experiences.experienceid HAVING AVG(COALESCE(score,0))>=?" + order
                            + " LIMIT ? OFFSET ?",
                    new Object[]{categoryId, max, score, page_size, (page-1)*page_size}, EXPERIENCE_MODEL_ROW_MAPPER);
        }
    }

    @Override
    public Integer countListByFilter(long categoryId, Double max, long score, Long city) {
        if (city > 0){
            return jdbcTemplate.queryForObject("SELECT COALESCE(COUNT (experienceName), 0) FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND COALESCE(price,0) <=? AND cityId = ?" +
                            "HAVING AVG(COALESCE(score,0))>=?",
                    new Object[]{categoryId, max, city, score}, Integer.class);
        }
        else {
            return jdbcTemplate.queryForObject("SELECT COALESCE(COUNT (experienceName), 0) FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? AND COALESCE(price,0) <=? " +
                            "HAVING AVG(COALESCE(score,0))>=?",
                    new Object[]{categoryId, max, score}, Integer.class);
        }
    }

    @Override
    public List<ExperienceModel> listByBestRanked(long categoryId){
        return jdbcTemplate.query("SELECT experiences.experienceId, experienceName, address, experiences.description, email, siteUrl, price, cityId, categoryId, experiences.userId FROM experiences LEFT JOIN reviews ON experiences.experienceid = reviews.experienceid WHERE categoryid = ? GROUP BY experiences.experienceid ORDER BY avg(score) DESC ",
                new Object[]{categoryId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

    @Override
    public List<ExperienceModel> listFavsByUserId(Long userId, String order) {
        final String query = "SELECT * FROM experiences WHERE experienceId IN ( SELECT experienceId FROM favuserexperience WHERE userId = ? )" + order;
        return jdbcTemplate.query(query, new Object[]{userId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }
}