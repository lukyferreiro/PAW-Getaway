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
import org.springframework.test.jdbc.JdbcTestUtils;
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


    private final String token1 = "asdfghy123";
    private final String token2 = "adasdasdasd345";
    private final Long id1 = Long.valueOf(1);
    private final Long id2 = Long.valueOf(2);

    private final Long Uid1 = Long.valueOf(34);

    private final Long Uid2 = Long.valueOf(12);

    private final LocalDateTime localDateTime1 = LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS_TESTING1);
    private final LocalDateTime localDateTime2 = LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS_TESTING2);

    private static final Integer TOKEN_DURATION_DAYS_TESTING1 = 1;
    private static final Integer TOKEN_DURATION_DAYS_TESTING2 = 2;

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
        final PasswordResetToken resetToken = passwordResetTokenDao.createToken(id1, token1, localDateTime1);
        assertNotNull(resetToken);
        assertEquals(Uid1, resetToken.getUserId());
        assertEquals(token1, passwordResetTokenDao.getTokenByValue(token1));
        assertEquals(LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS_TESTING1), resetToken.getExpirationDate());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "passwordresettoken", "tokenId = " + resetToken.getId()));


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
        final PasswordResetToken resetToken = passwordResetTokenDao.createToken(Uid2, token2, localDateTime2);
        final Long idTest = resetToken.getId();
        passwordResetTokenDao.removeTokenById(idTest);
        assertEquals(null, resetToken);


    }

    @Test
    @Rollback
    public void testRemoveTokenByUserId() {
        final PasswordResetToken resetToken = passwordResetTokenDao.createToken(Uid1, token2, localDateTime2);
        passwordResetTokenDao.removeTokenByUserId(Uid1);
        assertEquals(null, resetToken);

    }
}