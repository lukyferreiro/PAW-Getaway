package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.interfaces.persistence.VerificationTokenDao;
import ar.edu.itba.getaway.models.PasswordResetToken;
import ar.edu.itba.getaway.models.VerificationToken;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:verification-token-dao-test.sql")
public class VerificationTokenDaoTest {

    @Autowired
    private VerificationTokenDao verificationTokenDao;
    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

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


    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }


    @Test
    @Rollback
    public void testCreateToken() {
        final VerificationToken verificationToken = verificationTokenDao.createVerificationToken(Uid1, token1, localDateTime1);
        assertNotNull(verificationToken);
        assertEquals(Uid1, verificationToken.getUserId());
        assertEquals(token1, verificationTokenDao.getTokenByValue(token1));
        assertEquals(LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS_TESTING1), verificationToken.getExpirationDate());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "verificationtoken", "tokenId = " + verificationToken.getId()));


    }

    @Test
    public void testGetTokenByValue() {
        final Optional<VerificationToken> verificationToken = verificationTokenDao.getTokenByValue(token1);
        assertNotNull(verificationToken);
        assertEquals(token1, verificationToken.get());
    }


    @Test
    @Rollback
    public void testRemoveTokenById() {
        final VerificationToken verificationToken = verificationTokenDao.createVerificationToken(Uid2, token2, localDateTime2);
        final Long idTest = verificationToken.getId();
        verificationTokenDao.removeTokenById(idTest);
        assertEquals(null, verificationToken);


    }

    @Test
    @Rollback
    public void testRemoveTokenByUserId() {
        final VerificationToken verificationToken = verificationTokenDao.createVerificationToken(Uid2, token2, localDateTime2);
        verificationTokenDao.removeTokenByUserId(Uid1);
        assertEquals(null, verificationToken);

    }


}
