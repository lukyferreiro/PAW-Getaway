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

//TODO: check methods update and addrole

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
    private final static ImageModel IMAGE = new ImageModel( 15L, null);
    private final static ImageModel IMAGE_2 = new ImageModel( 16L, null);
    private final static ImageModel IMAGE_3 = new ImageModel( 17L, null);

    private final static ImageModel IMAGE_CREATE = new ImageModel( 18L, null);
    private static final Collection<Roles> DEFAULT_ROLES = new ArrayList<>(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    private static final RoleModel PROVIDER_MODEL = new RoleModel(1L, Roles.PROVIDER);
    private static final RoleModel USER_MODEL = new RoleModel(2L, Roles.USER);
    private static final RoleModel VERIFIED_MODEL = new RoleModel(3L, Roles.VERIFIED);
    private static final RoleModel NOT_VERIFIED_MODEL = new RoleModel(4L, Roles.NOT_VERIFIED);

    private static final Collection<RoleModel> DEFAULT_ROLES_MODELS = new ArrayList<>(Arrays.asList(USER_MODEL, NOT_VERIFIED_MODEL));

    private static final UserModel MAIN_USER = new UserModel(10L, "contra1", "usuario", "uno", "uno@mail.com", DEFAULT_ROLES_MODELS, IMAGE);
    private static final UserModel USER_TO_ADD_ROLE = new UserModel(20L, "contra2", "usuario2", "dos", "dos@mail.com", DEFAULT_ROLES_MODELS, IMAGE_2);
    private static final UserModel USER_TO_UPDATE_ROLE = new UserModel(30L, "contra3", "usuario3", "tres", "tres@mail.com", DEFAULT_ROLES_MODELS, IMAGE_3);

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
        try {
            user = userDao.createUser(PASSWORD, NAME, SURNAME, EMAIL, DEFAULT_ROLES, IMAGE_CREATE);
            assertNotNull(user);
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        }

        assertNotNull(user);

        System.out.println(user.getUserId());

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

        ArrayList<RoleModel> arrayRoles = new ArrayList<>(user.get().getRoles());

        assertTrue(arrayRoles.contains(USER_MODEL));
        assertTrue(arrayRoles.contains(NOT_VERIFIED_MODEL));
    }

    @Test
    public void testGetUserByIdNotFound(){
       final Optional<UserModel> notFoundUser = userDao.getUserById(50L);
       assertFalse(notFoundUser.isPresent());
    }

    @Test
    public void testGetUserByEmail(){
        final Optional<UserModel> user = userDao.getUserByEmail("uno@mail.com");

        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(10, user.get().getUserId());
        assertEquals("contra1", user.get().getPassword());
        assertEquals("usuario", user.get().getName());
        assertEquals("uno", user.get().getSurname());
        assertEquals("uno@mail.com", user.get().getEmail());
        assertEquals(IMAGE , user.get().getProfileImage());

        assertEquals(DEFAULT_ROLES_MODELS, user.get().getRoles());
        assertTrue(user.get().getRoles().contains(USER_MODEL));
        assertTrue(user.get().getRoles().contains(NOT_VERIFIED_MODEL));
        assertFalse(user.get().getRoles().contains(VERIFIED_MODEL));
        assertFalse(user.get().getRoles().contains(PROVIDER_MODEL));
    }

    @Test
    public void testGetRoleByNameProvider(){
        final Optional<RoleModel> roleModel = userDao.getRoleByName(Roles.PROVIDER);
        assertTrue(roleModel.isPresent());
        assertEquals(1, roleModel.get().getRoleId());
        assertEquals(Roles.PROVIDER, roleModel.get().getRoleName());
    }
    @Test
    public void testGetRoleByNameUser(){
        final Optional<RoleModel> roleModel = userDao.getRoleByName(Roles.USER);
        assertTrue(roleModel.isPresent());
        assertEquals(2, roleModel.get().getRoleId());
        assertEquals(Roles.USER, roleModel.get().getRoleName());
    }
    @Test
    public void testGetRoleByNameVerified(){
        final Optional<RoleModel> roleModel = userDao.getRoleByName(Roles.VERIFIED);
        assertTrue(roleModel.isPresent());
        assertEquals(3, roleModel.get().getRoleId());
        assertEquals(Roles.VERIFIED, roleModel.get().getRoleName());
    }
    @Test
    public void testGetRoleByNameNotVerified(){
        final Optional<RoleModel> roleModel = userDao.getRoleByName(Roles.NOT_VERIFIED);
        assertTrue(roleModel.isPresent());
        assertEquals(4, roleModel.get().getRoleId());
        assertEquals(Roles.NOT_VERIFIED, roleModel.get().getRoleName());
    }

    @Test
    @Rollback
    public void testUpdateRoles(){
        userDao.updateRoles(USER_TO_UPDATE_ROLE, Roles.NOT_VERIFIED, Roles.VERIFIED);
        final Optional<UserModel> user = userDao.getUserById(USER_TO_UPDATE_ROLE.getUserId());
        assertTrue(user.isPresent());
        final ArrayList<RoleModel> arrayRoles = new ArrayList<>(user.get().getRoles());

        System.out.println(user.get().getUserId());

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
    public void testUpdatePassword(){
        final Optional<UserModel> userBeforeUpdate = userDao.getUserById(MAIN_USER.getUserId());
        userDao.updatePassword(MAIN_USER, "newpwd");
        final Optional<UserModel> user = userDao.getUserById(MAIN_USER.getUserId());
        assertTrue(user.isPresent());

        //Check if all the other info is the same
        assertEquals(userBeforeUpdate.get().getEmail(), user.get().getEmail());
        assertEquals(userBeforeUpdate.get().getName(), user.get().getName());
        assertEquals(userBeforeUpdate.get().getSurname(), user.get().getSurname());
        assertEquals(userBeforeUpdate.get().getRoles(), user.get().getRoles());
        assertEquals(userBeforeUpdate.get().getProfileImage(), user.get().getProfileImage());

        assertEquals("newpwd",user.get().getPassword());
    }

    @Test
    @Rollback
    public void testUpdateUserInfo() {
        final Optional<UserModel> userBeforeUpdate = userDao.getUserById(MAIN_USER.getUserId());
        userDao.updateUserInfo(MAIN_USER, new UserInfo("newusuario", "newuno"));
        final Optional<UserModel> user = userDao.getUserById(MAIN_USER.getUserId());
        assertTrue(user.isPresent());

        //Check if all the other info is the same
        assertEquals(userBeforeUpdate.get().getEmail(), user.get().getEmail());
        assertEquals(userBeforeUpdate.get().getRoles(), user.get().getRoles());
        assertEquals(userBeforeUpdate.get().getPassword(), user.get().getPassword());
        assertEquals(userBeforeUpdate.get().getProfileImage(), user.get().getProfileImage());

        assertEquals("newusuario", user.get().getName());
        assertEquals("newuno", user.get().getSurname());
    }

    @Test
    @Rollback
    public void testAddRole() {
        userDao.addRole(USER_TO_ADD_ROLE, Roles.PROVIDER);
        final Optional<UserModel> user = userDao.getUserById(USER_TO_ADD_ROLE.getUserId());
        assertTrue(user.isPresent());
        System.out.println(user.get().getUserId());

        final ArrayList<RoleModel> arrayRoles = new ArrayList<>(user.get().getRoles());

        assertTrue(arrayRoles.contains(USER_MODEL));
        assertTrue(arrayRoles.contains(NOT_VERIFIED_MODEL));
        assertTrue(arrayRoles.contains(PROVIDER_MODEL));

        em.flush();

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + USER_TO_ADD_ROLE.getUserId()) + " AND roleId = " + USER_MODEL.getRoleId()));
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + USER_TO_ADD_ROLE.getUserId()) + " AND roleId = " + NOT_VERIFIED_MODEL.getRoleId()));
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", String.format("userId = " + USER_TO_ADD_ROLE.getUserId() + " AND roleId = " + PROVIDER_MODEL.getRoleId())));
    }
}
