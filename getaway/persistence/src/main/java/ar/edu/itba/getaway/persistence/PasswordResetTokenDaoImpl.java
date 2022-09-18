package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.PasswordResetToken;
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
public class PasswordResetTokenDaoImpl implements PasswordResetTokenDao{

    @Autowired
    private DataSource ds;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

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
    public Optional<PasswordResetToken> getToken(long id) {
        return jdbcTemplate.query("SELECT * FROM passwordResetToken WHERE passTokenId = ?",
                new Object[]{id}, PASSWORD_RESET_TOKEN_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public PasswordResetToken createToken(long userId, String token, LocalDateTime expirationDate) {
        Map<String, Object> args = new HashMap<>();
        args.put("passTokenUserId", userId);
        args.put("passToken", token);
        args.put("passTokenExpirationDate", expirationDate);
        final long tokenId = simpleJdbcInsert.executeAndReturnKey(args).longValue();;
        //Nunca retorna null
        return new PasswordResetToken(tokenId, token, userId, expirationDate);
    }

    @Override
    public Optional<PasswordResetToken> getTokenByValue(String token) {
        return jdbcTemplate.query("SELECT * FROM passwordResetToken WHERE passToken = ?",
                new Object[]{token}, PASSWORD_RESET_TOKEN_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void removeTokenById(long id) {
        jdbcTemplate.update("DELETE from passwordResetToken where passTokenId = ?", id);
    }

    @Override
    public void removeTokenByUserId(long userId) {
        jdbcTemplate.update("DELETE from passwordResetToken where passTokenUserId = ?", userId);
    }

    @Override
    public Optional<PasswordResetToken> getTokenByUserId(long userId) {
        return jdbcTemplate.query("SELECT * FROM passwordResetToken WHERE passTokenUserId = ?",
                new Object[]{userId}, PASSWORD_RESET_TOKEN_ROW_MAPPER).stream().findFirst();
    }
}
