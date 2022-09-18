package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.VerificationToken;
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

    @Autowired
    private DataSource ds;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<VerificationToken> VERIFICATION_TOKEN_ROW_MAPPER =
            (rs, rowNum) -> new VerificationToken(rs.getLong("verifid"),
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
        return jdbcTemplate.query("SELECT * FROM verificationToken WHERE verifId = ?",
                new Object[]{id}, VERIFICATION_TOKEN_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public VerificationToken createVerificationToken(long userId, String token, LocalDateTime expirationDate) {
        Map<String, Object> values = new HashMap<>();
        values.put("verifUserId", userId);
        values.put("verifToken", token);
        values.put("verifExpirationDate", expirationDate);
        final long tokenId = simpleJdbcInsert.executeAndReturnKey(values).longValue();
        //Nunca retorna null
        return new VerificationToken(tokenId, token, userId, expirationDate);
    }

    @Override
    public Optional<VerificationToken> getTokenByValue(String token) {
        return jdbcTemplate.query("SELECT * FROM verificationToken WHERE verifToken=?",
                new Object[]{token}, VERIFICATION_TOKEN_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void removeTokenById(long id) {
        jdbcTemplate.update("DELETE from verificationToken where verifId = ?", id);
    }

    @Override
    public void removeTokenByUserId(long userId) {
        jdbcTemplate.update("DELETE from verificationToken where verifUserId = ?", userId);
    }

    @Override
    public Optional<VerificationToken> getTokenByUserId(long userId) {
        return jdbcTemplate.query("SELECT * FROM verificationToken WHERE verifUserId=?",
                new Object[]{userId}, VERIFICATION_TOKEN_ROW_MAPPER).stream().findFirst();
    }
}
