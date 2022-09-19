package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ReviewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewDaoImpl implements ReviewDao{

    @Autowired
    private DataSource ds;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<ReviewModel> REVIEW_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new ReviewModel(rs.getLong("reviewid"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getLong("score"),
                    rs.getLong("experienceid"),
                    rs.getDate("reviewdate"),
                    rs.getLong("userid"));

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
}
