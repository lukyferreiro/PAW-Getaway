package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PasswordResetTokenDaoImpl implements PasswordResetTokenDao{

    @Autowired
    private DataSource ds;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<PasswordResetToken> PASSWORD_RESET_TOKEN_ROW_MAPPER =
            (rs, rowNum) -> new PasswordResetToken(rs.getLong("passTokenId"),
                    rs.getString("passToken"),
                    rs.getLong("passTokenUserId"),
                    rs.getTimestamp("passTokenExpirationDate").toLocalDateTime());

    @Autowired
    public PasswordResetTokenDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).
                withTableName("passwordResetToken").
                usingGeneratedKeyColumns("passTokenId");
    }


    @Override
    public Optional<PasswordResetToken> getToken(long id) {
        return Optional.empty();
    }

    @Override
    public PasswordResetToken createToken(long userId, String token, LocalDateTime expirationDate) {
        return null;
    }

    @Override
    public Optional<PasswordResetToken> getTokenByValue(String token) {
        return Optional.empty();
    }

    @Override
    public void removeTokenById(long id) {

    }

    @Override
    public void removeTokenByUserId(long userId) {

    }

    @Override
    public Optional<PasswordResetToken> getTokenByUserId(long userId) {
        return Optional.empty();
    }
}
