package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class VerificationTokenDaoImpl implements VerificationTokenDao {

    @Autowired
    private DataSource ds;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<VerificationToken> VERIFICATION_TOKEN_ROW_MAPPER =
            (rs, rowNum) -> new VerificationToken(rs.getLong("verifId"),
                    rs.getString("verifToken"),
                    rs.getLong("verifUserId"),
                    rs.getTimestamp("verifExpirationDate").toLocalDateTime());

    @Autowired
    public VerificationTokenDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).
                withTableName("verificationToken").
                usingGeneratedKeyColumns("verifId");
    }

    @Override
    public Optional<VerificationToken> getVerificationToken(long id) {
        return Optional.empty();
    }

    @Override
    public VerificationToken createVerificationToken(long userId, String token, LocalDateTime expirationDate) {
        return null;
    }

    @Override
    public Optional<VerificationToken> getTokenByValue(String token) {
        return Optional.empty();
    }

    @Override
    public void removeTokenById(long id) {

    }

    @Override
    public void removeTokenByUserId(long userId) {

    }

    @Override
    public Optional<VerificationToken> getTokenByUserId(long userId) {
        return Optional.empty();
    }
}
