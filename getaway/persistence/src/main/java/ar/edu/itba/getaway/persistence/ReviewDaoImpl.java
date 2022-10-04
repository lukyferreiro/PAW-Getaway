package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;
import ar.edu.itba.getaway.interfaces.persistence.ReviewDao;
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
public class ReviewDaoImpl implements ReviewDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDaoImpl.class);

    private static final RowMapper<ReviewModel> REVIEW_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new ReviewModel(rs.getLong("reviewid"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getLong("score"),
                    rs.getLong("experienceid"),
                    rs.getDate("reviewdate"),
                    rs.getLong("userid"));

    private static final RowMapper<ReviewUserModel> REVIEW_USER_ROW_MAPPER = (rs, rowNum) ->
            new ReviewUserModel(rs.getLong("reviewid"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getLong("score"),
                    rs.getLong("experienceid"),
                    rs.getDate("reviewdate"),
                    rs.getLong("userid"),
                    rs.getString("username"),
                    rs.getString("usersurname"),
                    rs.getLong("imgid"));

    @Autowired
    public ReviewDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reviews")
                .usingGeneratedKeyColumns("reviewid");
    }

    @Override
    public ReviewModel createReview (String title, String description, Long score, Long experienceId, Date reviewDate, Long userId) {
        final Map<String, Object> reviewData = new HashMap<>();
        reviewData.put("title", title);
        reviewData.put("description", description);
        reviewData.put("score", score);
        reviewData.put("experienceId", experienceId);
        reviewData.put("reviewDate", reviewDate);
        reviewData.put("userId", userId);
        final Long reviewId = jdbcInsert.executeAndReturnKey(reviewData).longValue();

        LOGGER.debug("Created new review with id {} by user with id {}", reviewId, userId);

        return new ReviewModel(reviewId, title, description, score, experienceId, reviewDate, userId);
    }

    @Override
    public List<ReviewModel> getReviewsByExperienceId(Long experienceId) {
        final String query = "SELECT * FROM reviews WHERE experienceId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{experienceId}, REVIEW_MODEL_ROW_MAPPER);
    }

    @Override
    public Long getReviewAverageScore(Long experienceId) {
        final String query = "SELECT CEILING(AVG(score)) FROM reviews WHERE experienceId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.queryForObject(query, new Object[]{experienceId}, Long.class);
    }

    @Override
    public Integer getReviewCount(Long experienceId) {
        final String query = "SELECT COUNT(*) FROM reviews WHERE experienceId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.queryForObject(query, new Object[]{experienceId}, Integer.class);
    }

    @Override
    public List<ReviewUserModel> getReviewAndUser(Long experienceId) {
        final String query = "SELECT * FROM reviews NATURAL JOIN users WHERE experienceId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{experienceId}, REVIEW_USER_ROW_MAPPER);
    }

    @Override
    public Optional<ReviewModel> getReviewById(Long reviewId) {
        final String query = "SELECT * FROM reviews WHERE reviewId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{reviewId}, REVIEW_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<ReviewUserModel> getReviewsByUserId(Long userId) {
        final String query = "SELECT * FROM reviews  NATURAL JOIN users WHERE userid = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{userId}, REVIEW_USER_ROW_MAPPER);
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        final String query = "DELETE FROM reviews WHERE reviewId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.update(query, reviewId) == 1;
    }

    @Override
    public boolean updateReview(Long reviewId, ReviewModel reviewModel) {
        LOGGER.debug("Executing query to update review with id: {}", reviewId);
        return jdbcTemplate.update("UPDATE reviews " +
                        "SET title = ?, " +
                        "description = ?, " +
                        "score = ?, " +
                        "experienceid = ?, " +
                        "reviewdate = ?, " +
                        "userid = ? " +
                        "WHERE reviewId = ?",
                reviewModel.getTitle(), reviewModel.getDescription(),
                reviewModel.getScore(), reviewModel.getExperienceId(),
                reviewModel.getReviewDate(),
                reviewModel.getUserId(),
                reviewId) == 1;
    }
}
