package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.interfaces.persistence.VerificationTokenDao;
import ar.edu.itba.getaway.models.*;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:verification-token-dao-test.sql")
public class VerificationTokenDaoTest {
    /**Data for tests**/
    //TO BE CREATED TOKEN DATA
    private final String token2 = "6789x";
    private static final LocalDateTime EXPIRATION_DATE = LocalDateTime.of(2021, 5, 29, 12, 30);

    //GENERAL USER DATA
    private static final RoleModel USER_MODEL = new RoleModel(2L, Roles.USER);
    private static final RoleModel NOT_VERIFIED_MODEL = new RoleModel(4L, Roles.NOT_VERIFIED);

    private static final Collection<RoleModel> DEFAULT_ROLES_MODELS = new ArrayList<>(Arrays.asList(USER_MODEL, NOT_VERIFIED_MODEL));

    //USER1 DATA
    private final static String PASSWORD_1 = "contra1";
    private final static String NAME_1 = "usuario";
    private final static String SURNAME_1 = "uno";
    private final static String EMAIL_1 = "uno@mail.com";
    private final static ImageModel IMAGE_1 = new ImageModel(15L, null);

    private final static UserModel USER_1 = new UserModel(1, PASSWORD_1, NAME_1, SURNAME_1, EMAIL_1, DEFAULT_ROLES_MODELS, IMAGE_1);

    //USER2 DATA
    private final static String PASSWORD_2 = "contra2";
    private final static String NAME_2 = "usuario2";
    private final static String SURNAME_2 = "dos";
    private final static String EMAIL_2 = "dos@mail.com";
    private final static ImageModel IMAGE_2 = new ImageModel(  16L, null);

    private final static UserModel USER_2 = new UserModel(2, PASSWORD_2, NAME_2, SURNAME_2, EMAIL_2, DEFAULT_ROLES_MODELS, IMAGE_2);

    //CREATED TOKEN DATA
    private final String token1 = "12345";
    private final VerificationToken VERIF = new VerificationToken(1,token1, USER_2, LocalDateTime.of(2020, 3, 23, 0, 0));

    /****/
    @Autowired
    private VerificationTokenDao verificationTokenDao;
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
        final VerificationToken verificationToken = verificationTokenDao.createVerificationToken(USER_1, token2, EXPIRATION_DATE);
        assertNotNull(verificationToken);
        assertEquals(USER_1, verificationToken.getUser());
        assertEquals(EXPIRATION_DATE, verificationToken.getExpirationDate());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "verificationtoken", "verifId = " + verificationToken.getId()));
    }

    @Test
    public void testGetTokenByValue() {
        final Optional<VerificationToken> verificationToken = verificationTokenDao.getTokenByValue(token1);
        assertNotNull(verificationToken);
        assertTrue(verificationToken.isPresent());
        assertEquals(token1, verificationToken.get().getValue());
        assertEquals(1L, verificationToken.get().getId());

        System.out.println("ACTUAL");
        System.out.println(verificationToken.get().getUser().getEmail());
        System.out.println(verificationToken.get().getUser().getName());
        System.out.println(verificationToken.get().getUser().getSurname());
        System.out.println(verificationToken.get().getUser().getPassword());
        System.out.println(verificationToken.get().getUser().getUserId());

        System.out.println("EXPECTED");
        System.out.println(USER_2.getEmail());
        System.out.println(USER_2.getName());
        System.out.println(USER_2.getSurname());
        System.out.println(USER_2.getPassword());
        System.out.println(USER_2.getUserId());

        //Creo que lo toma como diferente porque uno es generado por hibernate y otro creado a mano, pero no estoy seguro
        assertEquals(USER_2, verificationToken.get().getUser());
    }

    @Test
    public void testGetTokenByUser() {
        final Optional<VerificationToken> verificationToken = verificationTokenDao.getTokenByUser(USER_2);
        assertNotNull(verificationToken);
        assertTrue(verificationToken.isPresent());
        assertEquals(token1, verificationToken.get().getValue());
        assertEquals(1L, verificationToken.get().getId());
    }

    @Test
    @Rollback
    public void testRemoveToken() {
//        verificationTokenDao.removeToken(VERIF);
        VerificationToken toDeleteToken = verificationTokenDao.getTokenByValue(VERIF.getValue()).orElse(VERIF);
        verificationTokenDao.removeToken(toDeleteToken);
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "verificationtoken", "verifid = " + toDeleteToken.getId()));
    }
}
