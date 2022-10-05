package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.PasswordResetToken;
import ar.edu.itba.getaway.interfaces.persistence.PasswordResetTokenDao;
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
public class PasswordResetTokenDaoImpl implements PasswordResetTokenDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetTokenDaoImpl.class);

    private static final RowMapper<PasswordResetToken> PASSWORD_RESET_TOKEN_ROW_MAPPER =
            (rs, rowNum) -> new PasswordResetToken(rs.getLong("passtokenid"),
                    rs.getString("passToken"),
                    rs.getLong("passTokenUserId"),
                    rs.getTimestamp("passTokenExpirationDate").toLocalDateTime());

    @Autowired
    public PasswordResetTokenDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.simpleJdbcInsert = new SimpleJdbcInsert(ds).
                withTableName("passwordResetToken").
                usingGeneratedKeyColumns("passtokenid");
    }

    @Override
    public PasswordResetToken createToken(Long userId, String token, LocalDateTime expirationDate) {
        final Map<String, Object> passTokenData = new HashMap<>();
        passTokenData.put("passTokenUserId", userId);
        passTokenData.put("passToken", token);
        passTokenData.put("passTokenExpirationDate", expirationDate);
        final Long tokenId = simpleJdbcInsert.executeAndReturnKey(passTokenData).longValue();

        LOGGER.debug("Created new password reset token with id {} for user with id {}", tokenId, userId);

        return new PasswordResetToken(tokenId, token, userId, expirationDate);
    }

    @Override
    public Optional<PasswordResetToken> getTokenByValue(String token) {
        final String query = "SELECT * FROM passwordResetToken WHERE passToken = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{token}, PASSWORD_RESET_TOKEN_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public void removeTokenById(Long id) {
        final String query = "DELETE FROM passwordResetToken WHERE passTokenId = ?";
        LOGGER.debug("Executing query: {}", query);
        jdbcTemplate.update(query, id);
    }

    @Override
    public void removeTokenByUserId(Long userId) {
        final String query = "DELETE FROM passwordResetToken WHERE passTokenUserId = ?";
        LOGGER.debug("Executing query: {}", query);
        jdbcTemplate.update(query, userId);
    }

}
