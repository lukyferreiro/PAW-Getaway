package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;
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
public class ReviewDaoImpl implements ReviewDao{

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
    public ReviewModel create(String title, String description, long score, long experienceId, Date reviewDate, long userId) {
        final Map<String, Object> reviewData = new HashMap<>();
        reviewData.put("title", title);
        reviewData.put("description", description);
        reviewData.put("score", score);
        reviewData.put("experienceId", experienceId);
        reviewData.put("reviewDate", reviewDate);
        reviewData.put("userId", userId);
        final long reviewId = jdbcInsert.executeAndReturnKey(reviewData).longValue();
        return new ReviewModel(reviewId, title, description,score, experienceId, reviewDate, userId);
    }

    @Override
    public List<ReviewModel> getReviewsFromId(long experienceId) {
        return jdbcTemplate.query("SELECT * FROM reviews WHERE experienceId = ?",
                new Object[]{experienceId}, REVIEW_MODEL_ROW_MAPPER);
    }

    @Override
    public Double getAverageScore(long experienceId){
        return jdbcTemplate.queryForObject("SELECT AVG(score) FROM reviews WHERE experienceId = ?",
                new Object[] { experienceId }, Double.class);
    }

    @Override
    public Integer getReviewCount(long experienceId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reviews WHERE experienceId = ?",
                new Object[] { experienceId }, Integer.class);
    }

    @Override
    public List<ReviewUserModel> getReviewAndUser(long experienceId){
        return jdbcTemplate.query("SELECT * FROM reviews NATURAL JOIN users WHERE experienceId = ?",
                new Object[]{experienceId}, REVIEW_USER_ROW_MAPPER);
    }

    @Override
    public Optional<ReviewModel> getById(long reviewId) {
        return jdbcTemplate.query("SELECT * FROM reviews WHERE reviewId = ?",
                new Object[]{reviewId}, REVIEW_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<ReviewUserModel> getByUserId(long userId) {
        return jdbcTemplate.query("SELECT * FROM reviews  NATURAL JOIN users WHERE userid = ?",
                new Object[]{userId}, REVIEW_USER_ROW_MAPPER);
    }

    @Override
    public boolean delete(long reviewId) {
        return jdbcTemplate.update("DELETE FROM reviews WHERE reviewId = ?",
                reviewId) == 1;
    }

    @Override
    public boolean update(long reviewId, ReviewModel reviewModel) {
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
