//package ar.edu.itba.getaway.persistence.config;
//
//import ar.edu.itba.getaway.interfaces.persistence.PasswordResetTokenDao;
//import ar.edu.itba.getaway.models.PasswordResetToken;
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
//@Sql(scripts = "classpath:password-reset-token-dao-test.sql")
//public class PasswordResetTokenDaoTest {
//    /** Data for tests **/
//    private final String token1 = "12345";
//    private final String token2 = "6789x";
//    private final Long id1 = Long.valueOf(1);
//    private final Long Uid2 = Long.valueOf(2);
//
//    private static final LocalDateTime EXPIRATION_DATE = LocalDateTime.of(2021, 5, 29, 12, 30);
//    /****/
//
//    @Autowired
//    private PasswordResetTokenDao passwordResetTokenDao;
//
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
//    @Test
//    @Rollback
//    public void testCreateToken() {
//        final PasswordResetToken resetToken = passwordResetTokenDao.createToken(Uid2, token2, EXPIRATION_DATE);
////        assertNotNull(resetToken);
////        assertEquals(Uid2, resetToken.getUserId());
////        assertEquals(token2, passwordResetTokenDao.getTokenByValue(token1));
////        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "passwordresettoken", "passtokenid = " + resetToken.getId()));
//    }
//
//    @Test
//    public void testGetTokenByValue() {
//        final Optional<PasswordResetToken> resetTokenOptional = passwordResetTokenDao.getTokenByValue(token1);
//        assertNotNull(resetTokenOptional);
//        assertTrue(resetTokenOptional.isPresent());
//        assertEquals(token1, resetTokenOptional.get().getValue());
//        assertEquals(new Long(1), resetTokenOptional.get().getId());
//        assertEquals(new Long(1), resetTokenOptional.get().getUser());
//    }
//
//    @Test
//    @Rollback
//    public void testRemoveTokenById() {
//        passwordResetTokenDao.removeToken(id1);
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "passwordresettoken", "passtokenid = " + id1));
//    }
//
//    @Test
//    @Rollback
//    public void testRemoveTokenByUserId() {
//        passwordResetTokenDao.removeTokenByUserId(id1);
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "passwordresettoken", "passtokenuserid = " + id1));
//    }
//}