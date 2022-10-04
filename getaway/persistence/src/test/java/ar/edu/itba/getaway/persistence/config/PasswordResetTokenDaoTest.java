package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.interfaces.persistence.PasswordResetTokenDao;
import ar.edu.itba.getaway.models.PasswordResetToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:password-reset-token-dao-test.sql")
public class PasswordResetTokenDaoTest {

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    private PasswordResetToken passwordResetToken;

    private final String token1 = "asdfghy123";
    private final String token2 = "adasdasdasd345";
    private final Long id1 = Long.valueOf(1);
    private final Long id2 = Long.valueOf(2);

    private static final Integer TOKEN_DURATION_DAYS = 1;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Rollback
    public void testCreateToken() {
        final PasswordResetToken resetToken = passwordResetTokenDao.createToken(Long.valueOf(2), token1, passwordResetToken.getExpirationDate());
        assertNotNull(resetToken);
        assertEquals(Long.valueOf(2), resetToken.getUserId());
        assertEquals(token1, passwordResetTokenDao.getTokenByValue(token1));
        assertEquals(LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS), passwordResetToken.getExpirationDate());

    }

    @Test
    public void testGetTokenByValue() {
        final Optional<PasswordResetToken> resetTokenOptional = passwordResetTokenDao.getTokenByValue(token1);
        assertNotNull(resetTokenOptional);
        assertEquals(token1, resetTokenOptional.get());
    }

    @Test
    @Rollback
    public void testRemoveTokenById() {
        passwordResetTokenDao.removeTokenById(id1); //TODO terminar este testing , ACA FALTA AGREEGAR TOKEN ASOCIADO CON ESTE ID Y IDEM ABAJO DPS



    }

    @Test
    @Rollback
    public void testRemoveTokenByUserId() {

    }
}