package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.FavExperienceModel;
import ar.edu.itba.getaway.services.FavExperienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FavExperienceDaoImpl implements FavExperienceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FavExperienceDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<FavExperienceModel> FAV_EXPERIENCE_ROW_MAPPER = (rs, rowNum) ->new FavExperienceModel(
            rs.getLong("userid"),
            rs.getLong("experienceid"));

    private static final RowMapper<Long> FAV_EXPERIENCEID_ROW_MAPPER = (rs, rowNum) -> rs.getLong("experienceid");


    @Autowired
    public FavExperienceDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("experiences")
                .usingGeneratedKeyColumns("experienceid");
    }

    @Override
    public FavExperienceModel create(long userId, long experienceId) {
        final Map<String, Object> experienceData = new HashMap<>();
        experienceData.put("userId", userId);
        experienceData.put("experienceId", experienceId);

        LOGGER.info("Setting experience as fav");

        return new FavExperienceModel(userId, experienceId);
    }

    @Override
    public boolean delete(long userId, long experienceId){
        final String query = "DELETE FROM favuserexperience WHERE experienceId = ? AND userid = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.update(query, experienceId, userId) == 1;
    }

    @Override
    public List<FavExperienceModel> getByExperienceId(long experienceId) {
        final String query = "SELECT * FROM favuserexperience WHERE experienceId = ? ";
        return jdbcTemplate.query(query, new Object[]{experienceId}, FAV_EXPERIENCE_ROW_MAPPER);

    }

    @Override
    public List<FavExperienceModel> listAll() {
        final String query = "SELECT * FROM favuserexperience";
        return jdbcTemplate.query(query, new Object[]{}, FAV_EXPERIENCE_ROW_MAPPER);    }

    @Override
    public List<Long> listByUserId(Long id) {
        final String query = "SELECT experienceid FROM favuserexperience WHERE userid = ? ";
        return jdbcTemplate.query(query, new Object[]{id}, FAV_EXPERIENCEID_ROW_MAPPER);
    }
}
