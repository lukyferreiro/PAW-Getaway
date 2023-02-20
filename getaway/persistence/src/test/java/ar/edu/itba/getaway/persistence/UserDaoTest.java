package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.persistence.UserDao;
import ar.edu.itba.getaway.persistence.config.TestConfig;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:user-dao-test.sql")
public class UserDaoTest {
    /**
     * Data for tests
     **/

    private final static String PASSWORD = "password";
    private final static String NAME = "NAME";
    private final static String SURNAME = "SURNAME";
    private final static String EMAIL = "e@mail.com";

    private static final String DEFAULT_TYPE = "JPG";
    private static final byte[] DEFAULT_IMG_OBJECT = {1, 2, 3, 4};

    private final static ImageModel IMAGE = new ImageModel(15L, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    private final static ImageModel IMAGE_2 = new ImageModel(16L, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    private final static ImageModel IMAGE_3 = new ImageModel(17L, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);

    private final static ImageModel IMAGE_CREATE = new ImageModel(18L, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    private static final Collection<Roles> DEFAULT_ROLES = new ArrayList<>(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    private static final RoleModel PROVIDER_MODEL = new RoleModel(1L, Roles.PROVIDER);
    private static final RoleModel USER_MODEL = new RoleModel(2L, Roles.USER);
    private static final RoleModel VERIFIED_MODEL = new RoleModel(3L, Roles.VERIFIED);
    private static final RoleModel NOT_VERIFIED_MODEL = new RoleModel(4L, Roles.NOT_VERIFIED);

    private static final Collection<RoleModel> DEFAULT_ROLES_MODELS = new ArrayList<>(Arrays.asList(USER_MODEL, NOT_VERIFIED_MODEL));

    private static final UserModel MAIN_USER = new UserModel(10L, "contra1", "usuario", "uno", "uno@mail.com", DEFAULT_ROLES_MODELS, IMAGE);
    private static final UserModel USER_TO_ADD_ROLE = new UserModel(20L, "contra2", "usuario2", "dos", "dos@mail.com", DEFAULT_ROLES_MODELS, IMAGE_2);
    private static final UserModel USER_TO_UPDATE_ROLE = new UserModel(30L, "contra3", "usuario3", "tres", "tres@mail.com", DEFAULT_ROLES_MODELS, IMAGE_3);


    private final static CategoryModel CATEGORY_1 = new CategoryModel(1L, "Aventura");
    private final static CategoryModel CATEGORY_2 = new CategoryModel(2L, "Gastronomia");
    private final static CategoryModel CATEGORY_6 = new CategoryModel(6L, "Historico");

    private final static CountryModel COUNTRY_1 = new CountryModel(1L, "Test Country");

    private final static CityModel CITY_1 = new CityModel(1L, COUNTRY_1, "Test City One");
    private final ImageModel IMAGE_ADV_1 = new ImageModel(1, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    private final ImageModel IMAGE_GAS = new ImageModel(2, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);

    private final ExperienceModel DEFAULT_ADV = new ExperienceModel(1L, "testaventura", "diraventura", null, "owner@mail.com", null, 0.0, CITY_1, CATEGORY_1, MAIN_USER, IMAGE_ADV_1, true, 0);
    private final ExperienceModel DEFAULT_GAS = new ExperienceModel(2L, "testgastro", "dirgastro", null, "owner@mail.com", null, 1000.0, CITY_1, CATEGORY_2, MAIN_USER, IMAGE_GAS, true, 0);


    /****/

    @Autowired
    private DataSource ds;
    @Autowired
    private UserDao userDao;
    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Rollback
    public void testCreateUser() {
        UserModel user = null;
        ImageModel imageToCreate = em.find(ImageModel.class, 18L);

        try {
            user = userDao.createUser(PASSWORD, NAME, SURNAME, EMAIL, DEFAULT_ROLES, imageToCreate);
            assertNotNull(user);
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        }

        assertNotNull(user);

        assertEquals(PASSWORD, user.getPassword());
        assertEquals(NAME, user.getName());
        assertEquals(SURNAME, user.getSurname());
        assertEquals(EMAIL, user.getEmail());

        assertEquals(DEFAULT_ROLES_MODELS, user.getRoles());
        assertTrue(user.getRoles().contains(USER_MODEL));
        assertTrue(user.getRoles().contains(NOT_VERIFIED_MODEL));
        assertFalse(user.getRoles().contains(VERIFIED_MODEL));
        assertFalse(user.getRoles().contains(PROVIDER_MODEL));

        em.flush();

        //Check new row added to table
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "userId = " + user.getUserId()));

        //Check user was created with the two default roles only
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + user.getUserId()) + " AND roleId = " + NOT_VERIFIED_MODEL.getRoleId()));
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + user.getUserId()) + " AND roleId = " + USER_MODEL.getRoleId()));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + user.getUserId()) + " AND roleId = " + VERIFIED_MODEL.getRoleId()));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + user.getUserId()) + " AND roleId = " + PROVIDER_MODEL.getRoleId()));
    }

    @Test(expected = DuplicateUserException.class)
    @Rollback
    public void testCreateDuplicateUser() throws DuplicateUserException {
        userDao.createUser("contra1", "usuario", "uno", "uno@mail.com", DEFAULT_ROLES, IMAGE_CREATE);
    }

    @Test
    public void testGetUserById() {
        final Optional<UserModel> user = userDao.getUserById(10L);

        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals("contra1", user.get().getPassword());
        assertEquals("usuario", user.get().getName());
        assertEquals("uno", user.get().getSurname());
        assertEquals("uno@mail.com", user.get().getEmail());
        assertEquals(IMAGE, user.get().getProfileImage());
        assertTrue(user.get().hasExperiences());
        assertTrue(user.get().hasReviews());

        ArrayList<RoleModel> arrayRoles = new ArrayList<>(user.get().getRoles());

        assertTrue(arrayRoles.contains(USER_MODEL));
        assertTrue(arrayRoles.contains(NOT_VERIFIED_MODEL));

        List<ExperienceModel> favs = user.get().getFavExperiences();

        assertTrue(favs.contains(DEFAULT_ADV));
        assertTrue(favs.contains(DEFAULT_GAS));

        List<ExperienceModel> viewed = user.get().getViewedExperiences();

        assertTrue(viewed.contains(DEFAULT_ADV));
        assertTrue(viewed.contains(DEFAULT_GAS));
    }

    @Test
    public void testGetUserByIdNotFound() {
        final Optional<UserModel> notFoundUser = userDao.getUserById(50L);
        assertFalse(notFoundUser.isPresent());
    }

    @Test
    public void testGetUserByEmail() {
        final Optional<UserModel> user = userDao.getUserByEmail("uno@mail.com");

        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(10, user.get().getUserId());
        assertEquals("contra1", user.get().getPassword());
        assertEquals("usuario", user.get().getName());
        assertEquals("uno", user.get().getSurname());
        assertEquals("uno@mail.com", user.get().getEmail());
        assertEquals(IMAGE, user.get().getProfileImage());

        ArrayList<RoleModel> arrayRoles = new ArrayList<>(user.get().getRoles());

        assertTrue(arrayRoles.contains(USER_MODEL));
        assertTrue(arrayRoles.contains(NOT_VERIFIED_MODEL));

        List<ExperienceModel> favs = user.get().getFavExperiences();

        assertTrue(favs.contains(DEFAULT_ADV));
        assertTrue(favs.contains(DEFAULT_GAS));

        List<ExperienceModel> viewed = user.get().getViewedExperiences();

        assertTrue(viewed.contains(DEFAULT_ADV));
        assertTrue(viewed.contains(DEFAULT_GAS));
    }

    @Test
    public void testGetRoleByNameProvider() {
        final Optional<RoleModel> roleModel = userDao.getRoleByName(Roles.PROVIDER);
        assertTrue(roleModel.isPresent());
        assertEquals(1, roleModel.get().getRoleId());
        assertEquals(Roles.PROVIDER, roleModel.get().getRoleName());
    }

    @Test
    public void testGetRoleByNameUser() {
        final Optional<RoleModel> roleModel = userDao.getRoleByName(Roles.USER);
        assertTrue(roleModel.isPresent());
        assertEquals(2, roleModel.get().getRoleId());
        assertEquals(Roles.USER, roleModel.get().getRoleName());
    }

    @Test
    public void testGetRoleByNameVerified() {
        final Optional<RoleModel> roleModel = userDao.getRoleByName(Roles.VERIFIED);
        assertTrue(roleModel.isPresent());
        assertEquals(3, roleModel.get().getRoleId());
        assertEquals(Roles.VERIFIED, roleModel.get().getRoleName());
    }

    @Test
    public void testGetRoleByNameNotVerified() {
        final Optional<RoleModel> roleModel = userDao.getRoleByName(Roles.NOT_VERIFIED);
        assertTrue(roleModel.isPresent());
        assertEquals(4, roleModel.get().getRoleId());
        assertEquals(Roles.NOT_VERIFIED, roleModel.get().getRoleName());
    }

    @Test
    @Rollback
    public void testUpdateRoles() {
        Optional<UserModel> user = userDao.updateRoles(USER_TO_UPDATE_ROLE, Roles.NOT_VERIFIED, Roles.VERIFIED);

        assertNotNull(user);
        assertTrue(user.isPresent());

        final ArrayList<RoleModel> arrayRoles = new ArrayList<>(user.get().getRoles());

        //Asserts to check changes in userModel
        assertTrue(arrayRoles.contains(USER_MODEL));
        assertTrue(arrayRoles.contains(VERIFIED_MODEL));
        assertFalse(arrayRoles.contains(NOT_VERIFIED_MODEL));
        assertFalse(arrayRoles.contains(PROVIDER_MODEL));

        em.flush();

        //Asserts to check changes in userroles table
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + USER_TO_UPDATE_ROLE.getUserId()) + " AND roleId = " + USER_MODEL.getRoleId()));
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + USER_TO_UPDATE_ROLE.getUserId()) + " AND roleId = " + VERIFIED_MODEL.getRoleId()));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + USER_TO_UPDATE_ROLE.getUserId() + " AND roleId = " + NOT_VERIFIED_MODEL.getRoleId())));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + USER_TO_UPDATE_ROLE.getUserId() + " AND roleId = " + PROVIDER_MODEL.getRoleId())));
    }

    @Test
    @Rollback
    public void testUpdatePassword() {
        Optional<UserModel> user = userDao.updatePassword(MAIN_USER, "newpwd");

        assertNotNull(user);
        assertTrue(user.isPresent());

        //Check if all the other info is the same
        assertEquals(MAIN_USER.getEmail(), user.get().getEmail());
        assertEquals(MAIN_USER.getName(), user.get().getName());
        assertEquals(MAIN_USER.getSurname(), user.get().getSurname());
        assertEquals(MAIN_USER.getRoles(), user.get().getRoles());
        assertEquals(MAIN_USER.getProfileImage(), user.get().getProfileImage());

        assertEquals("newpwd", user.get().getPassword());
    }

    @Test
    @Rollback
    public void testUpdateUserInfo() {
        Optional<UserModel> user = userDao.updateUserInfo(MAIN_USER, new UserInfo("newusuario", "newuno"));

        assertNotNull(user);
        assertTrue(user.isPresent());

        //Check if all the other info is the same
        assertEquals(MAIN_USER.getEmail(), user.get().getEmail());
        assertEquals(MAIN_USER.getRoles(), user.get().getRoles());
        assertEquals(MAIN_USER.getPassword(), user.get().getPassword());
        assertEquals(MAIN_USER.getProfileImage(), user.get().getProfileImage());

        assertEquals("newusuario", user.get().getName());
        assertEquals("newuno", user.get().getSurname());
    }

    @Test
    @Rollback
    public void testAddRole() {
        Optional<UserModel> user = userDao.addRole(USER_TO_ADD_ROLE, Roles.PROVIDER);

        assertNotNull(user);
        assertTrue(user.isPresent());

        final ArrayList<RoleModel> arrayRoles = new ArrayList<>(user.get().getRoles());

        assertTrue(arrayRoles.contains(USER_MODEL));
        assertTrue(arrayRoles.contains(NOT_VERIFIED_MODEL));
        assertTrue(arrayRoles.contains(PROVIDER_MODEL));

        em.flush();

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + USER_TO_ADD_ROLE.getUserId()) + " AND roleId = " + USER_MODEL.getRoleId()));
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + USER_TO_ADD_ROLE.getUserId()) + " AND roleId = " + NOT_VERIFIED_MODEL.getRoleId()));
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + USER_TO_ADD_ROLE.getUserId() + " AND roleId = " + PROVIDER_MODEL.getRoleId())));
    }

    @Test
    public void testGetUserByExperienceId() {
        final Optional<UserModel> user = userDao.getUserByExperienceId(1L);

        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals("contra1", user.get().getPassword());
        assertEquals("usuario", user.get().getName());
        assertEquals("uno", user.get().getSurname());
        assertEquals("uno@mail.com", user.get().getEmail());
        assertEquals(IMAGE, user.get().getProfileImage());
        assertTrue(user.get().hasExperiences());
        assertTrue(user.get().hasReviews());

        ArrayList<RoleModel> arrayRoles = new ArrayList<>(user.get().getRoles());

        assertTrue(arrayRoles.contains(USER_MODEL));
        assertTrue(arrayRoles.contains(NOT_VERIFIED_MODEL));

        List<ExperienceModel> favs = user.get().getFavExperiences();

        assertTrue(favs.contains(DEFAULT_ADV));
        assertTrue(favs.contains(DEFAULT_GAS));

        List<ExperienceModel> viewed = user.get().getViewedExperiences();

        assertTrue(viewed.contains(DEFAULT_ADV));
        assertTrue(viewed.contains(DEFAULT_GAS));
    }

    @Test
    public void testGetUserByReviewId() {
        final Optional<UserModel> user = userDao.getUserByReviewId(1L);

        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals("contra1", user.get().getPassword());
        assertEquals("usuario", user.get().getName());
        assertEquals("uno", user.get().getSurname());
        assertEquals("uno@mail.com", user.get().getEmail());
        assertEquals(IMAGE, user.get().getProfileImage());
        assertTrue(user.get().hasExperiences());
        assertTrue(user.get().hasReviews());

        ArrayList<RoleModel> arrayRoles = new ArrayList<>(user.get().getRoles());

        assertTrue(arrayRoles.contains(USER_MODEL));
        assertTrue(arrayRoles.contains(NOT_VERIFIED_MODEL));

        List<ExperienceModel> favs = user.get().getFavExperiences();

        assertTrue(favs.contains(DEFAULT_ADV));
        assertTrue(favs.contains(DEFAULT_GAS));

        List<ExperienceModel> viewed = user.get().getViewedExperiences();

        assertTrue(viewed.contains(DEFAULT_ADV));
        assertTrue(viewed.contains(DEFAULT_GAS));
    }
}
