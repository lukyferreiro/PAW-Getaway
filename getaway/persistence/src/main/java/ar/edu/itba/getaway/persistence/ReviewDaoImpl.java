package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
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
                    rs.getDate("reviewdate"));

    @Autowired
    public ReviewDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reviews")
                .usingGeneratedKeyColumns("reviewid");
    }

    @Override
    public ReviewModel create(String title, String description, long score, long experienceId, Date reviewDate) {
        final Map<String, Object> args = new HashMap<>();
        args.put("title", title);
        args.put("description", description);
        args.put("score", score);
        args.put("experienceId", experienceId);
        args.put("reviewDate", reviewDate);
        final long reviewid = jdbcInsert.executeAndReturnKey(args).longValue();
        return new ReviewModel(reviewid, title, description,score, experienceId, reviewDate);
    }

    @Override
    public List<ReviewModel> getReviewsFromId(long experienceId) {
        return jdbcTemplate.query("SELECT * FROM reviews WHERE experienceId = ?",
                new Object[]{experienceId}, REVIEW_MODEL_ROW_MAPPER);    }
}
