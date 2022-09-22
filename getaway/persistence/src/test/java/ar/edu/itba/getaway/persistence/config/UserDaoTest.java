package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.persistence.UserDao;
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

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:user-dao-test.sql")
public class UserDaoTest {
    /** Data for tests **/
    private final static String PASSWORD = "password";
    private final static String NAME = "NAME";
    private final static String SURNAME = "SURNAME";
    private final static String EMAIL = "e@mail.com";
    /****/

    @Autowired
    private DataSource ds;

    @Autowired
    private UserDao userDao;

    private static final Collection<Roles> DEFAULT_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    @Rollback
    public void testCreateUser() throws DuplicateUserException {
        final UserModel user = userDao.createUser(PASSWORD, NAME, SURNAME, EMAIL, DEFAULT_ROLES);
        assertNotNull(user);
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(NAME, user.getName());
        assertEquals(SURNAME, user.getSurname());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(null, user.getProfileImageId());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "userId = " + user.getId()));
    }
}
