//package ar.edu.itba.getaway.persistence.config;
//
//import ar.edu.itba.getaway.interfaces.persistence.VerificationTokenDao;
//import ar.edu.itba.getaway.models.VerificationToken;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.jdbc.JdbcTestUtils;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.sql.DataSource;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@Sql(scripts = "classpath:verification-token-dao-test.sql")
//public class VerificationTokenDaoTest {
//    /**Data for tests**/
//    private final String token1 = "12345";
//    private final String token2 = "6789x";
//    private final Long id1 = Long.valueOf(1);
//    private final Long Uid2 = Long.valueOf(2);
//
//    private static final LocalDateTime EXPIRATION_DATE = LocalDateTime.of(2021, 5, 29, 12, 30);
//    /****/
//    @Autowired
//    private VerificationTokenDao verificationTokenDao;
//    @Autowired
//    private DataSource ds;
//
//    private JdbcTemplate jdbcTemplate;
//
//    @Before
//    public void setUp() {
//        this.jdbcTemplate = new JdbcTemplate(ds);
//    }
//
//
//    @Test
//    @Rollback
//    public void testCreateToken() {
//        final VerificationToken verificationToken = verificationTokenDao.createVerificationToken(Uid2, token2, EXPIRATION_DATE);
////        assertNotNull(verificationToken);
////        assertEquals(Uid1, verificationToken.getUserId());
////        assertEquals(token1, verificationTokenDao.getTokenByValue(token1));
////        assertEquals(LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS_TESTING1), verificationToken.getExpirationDate());
////        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "verificationtoken", "tokenId = " + verificationToken.getId()));
//    }
//
//    @Test
//    public void testGetTokenByValue() {
//        final Optional<VerificationToken> verificationToken = verificationTokenDao.getTokenByValue(token1);
//        assertNotNull(verificationToken);
//        assertTrue(verificationToken.isPresent());
//        assertEquals(token1, verificationToken.get().getValue());
//        assertEquals(new Long(1), verificationToken.get().getId());
//        assertEquals(new Long(1), verificationToken.get().getUserId());
//    }
//
//    @Test
//    @Rollback
//    public void testRemoveTokenById() {
//        verificationTokenDao.removeTokenById(id1);
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "verificationtoken", "verifid = " + id1));
//    }
//
//    @Test
//    @Rollback
//    public void testRemoveTokenByUserId() {
//        verificationTokenDao.removeTokenByUserId(id1);
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "verificationtoken", "verifuserid = " + id1));
//    }
//}
