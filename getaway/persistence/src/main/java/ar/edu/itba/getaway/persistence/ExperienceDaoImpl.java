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

    private static final RowMapper<ExperienceModel> EXPERIENCE_MODEL_ROW_MAPPER =
            (rs, rowNum) -> new ExperienceModel(rs.getLong("experienceid"),
                    rs.getString("experienceName"),
                    rs.getString("address"),
                    rs.getString("description"),
                    rs.getString("siteUrl"),
                    rs.getDouble("price"),
                    rs.getLong("cityId"),
                    rs.getLong("categoryId"),
                    rs.getLong("userId"));

    @Autowired
    public ExperienceDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("experiences")
                .usingGeneratedKeyColumns("experienceid");
    }

    @Override
    public ExperienceModel create(String name, String address, String description, String url, long price, long cityId, long categoryId, long userId) {
        final Map<String, Object> args = new HashMap<>();
        args.put("experienceName", name);
        args.put("address", address);
        args.put("description", description);
        args.put("siteUrl", url);
        args.put("price", price);
        args.put("cityId", cityId);
        args.put("categoryId", categoryId);
        args.put("userId",userId);
        final long experienceId = jdbcInsert.executeAndReturnKey(args).longValue();
        return new ExperienceModel(experienceId, name, address, description,url, price, cityId, categoryId, userId);
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
                "WHERE experienceId = ?",
                experienceModel.getName(), experienceModel.getAddress(),
                experienceModel.getDescription(), experienceModel.getSiteUrl(),
                experienceModel.getPrice(),
                experienceModel.getCityId(), experienceModel.getCategoryId(),
                experienceModel.getUserId(),
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
                "SELECT experienceId, experienceName, address, description, siteUrl, price, cityId, categoryId, userId FROM experiences",
                EXPERIENCE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<ExperienceModel> getById(long experienceId) {
        return jdbcTemplate.query("SELECT experienceId, experienceName, address, description, siteUrl, price, cityId, categoryId, userId FROM experiences WHERE experienceId = ?",
                new Object[]{experienceId}, EXPERIENCE_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<ExperienceModel> listByCategory(long categoryId) {
        return jdbcTemplate.query("SELECT experienceId, experienceName, address, description, siteUrl, price, cityId, categoryId, userId FROM experiences NATURAL JOIN categories WHERE categoryId = ?",
                new Object[]{categoryId}, EXPERIENCE_MODEL_ROW_MAPPER);
    }

}