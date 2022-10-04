package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.VerificationToken;
import ar.edu.itba.getaway.interfaces.persistence.VerificationTokenDao;
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
    public VerificationToken createVerificationToken(Long userId, String token, LocalDateTime expirationDate) {
        final Map<String, Object> verifTokenData = new HashMap<>();
        verifTokenData.put("verifUserId", userId);
        verifTokenData.put("verifToken", token);
        verifTokenData.put("verifExpirationDate", expirationDate);
        final Long tokenId = simpleJdbcInsert.executeAndReturnKey(verifTokenData).longValue();

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
    public void removeTokenById(Long id) {
        final String query = "DELETE FROM verificationToken WHERE verifId = ?";
        LOGGER.debug("Executing query: {}", query);
        jdbcTemplate.update(query, id);
    }

    @Override
    public void removeTokenByUserId(Long userId) {
        final String query = "DELETE FROM verificationToken WHERE verifUserId = ?";
        LOGGER.debug("Executing query: {}", query);
        jdbcTemplate.update(query, userId);
    }

}
