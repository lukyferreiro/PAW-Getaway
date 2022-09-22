package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.VerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class VerificationTokenDaoImpl implements VerificationTokenDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationTokenDaoImpl.class);

    private static final RowMapper<VerificationToken> VERIFICATION_TOKEN_ROW_MAPPER = (rs, rowNum) ->
            new VerificationToken(rs.getLong("verifid"),
                    rs.getString("verifToken"),
                    rs.getLong("verifUserId"),
                    rs.getTimestamp("verifExpirationDate").toLocalDateTime());

    @Autowired
    public VerificationTokenDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.simpleJdbcInsert = new SimpleJdbcInsert(ds).
                withTableName("verificationToken").
                usingGeneratedKeyColumns("verifid");
    }

    @Override
    public Optional<VerificationToken> getVerificationToken(long id) {
        final String query = "SELECT * FROM verificationToken WHERE verifId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{id}, VERIFICATION_TOKEN_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public VerificationToken createVerificationToken(long userId, String token, LocalDateTime expirationDate) {
        Map<String, Object> verifTokenData = new HashMap<>();
        verifTokenData.put("verifUserId", userId);
        verifTokenData.put("verifToken", token);
        verifTokenData.put("verifExpirationDate", expirationDate);
        final long tokenId = simpleJdbcInsert.executeAndReturnKey(verifTokenData).longValue();

        LOGGER.info("Created new verification token for user with id {}", userId);

        return new VerificationToken(tokenId, token, userId, expirationDate);
    }

    @Override
    public Optional<VerificationToken> getTokenByValue(String token) {
        final String query = "SELECT * FROM verificationToken WHERE verifToken=?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{token}, VERIFICATION_TOKEN_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public void removeTokenById(long id) {
        final String query = "DELETE FROM verificationToken WHERE verifId = ?";
        LOGGER.debug("Executing query: {}", query);
        jdbcTemplate.update(query, id);
    }

    @Override
    public void removeTokenByUserId(long userId) {
        final String query = "DELETE FROM verificationToken WHERE verifUserId = ?";
        LOGGER.debug("Executing query: {}", query);
        jdbcTemplate.update(query, userId);
    }

    @Override
    public Optional<VerificationToken> getTokenByUserId(long userId) {
        final String query = "SELECT * FROM verificationToken WHERE verifUserId=?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{userId}, VERIFICATION_TOKEN_ROW_MAPPER)
                .stream().findFirst();
    }
}
