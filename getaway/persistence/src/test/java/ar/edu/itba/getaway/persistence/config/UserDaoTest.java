package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.persistence.ImageDao;
import ar.edu.itba.getaway.interfaces.persistence.UserDao;
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
import java.util.*;

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
    private final static ImageModel IMAGE = new ImageModel( null, 15L);

    private static final Collection<Roles> DEFAULT_ROLES = new ArrayList<>(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));
    private static final RoleModel USER_MODEL = new RoleModel(2L, Roles.USER);
    private static final RoleModel NOT_VERIFIED_MODEL = new RoleModel(4L, Roles.NOT_VERIFIED);
    private static final Collection<RoleModel> DEFAULT_ROLES_MODELS = new ArrayList<>(Arrays.asList(USER_MODEL, NOT_VERIFIED_MODEL));

    private final static UserModel DEFAULT_USER = new UserModel(1L, PASSWORD, NAME, SURNAME, EMAIL, DEFAULT_ROLES, IMAGE);

    /****/

    @Autowired
    private DataSource ds;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ImageDao imageDao;


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
            user = userDao.createUser(PASSWORD, NAME, SURNAME, EMAIL, DEFAULT_ROLES, IMAGE);
            assertNotNull(user);
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        }

        assertNotNull(user);
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(NAME, user.getName());
        assertEquals(SURNAME, user.getSurname());
        assertEquals(EMAIL, user.getEmail());

        //Assert to check every user is created with a profile image pointing to null
        assertTrue(imageDao.getImgById(user.getProfileImage().getImageId()).isPresent());
        assertEquals(IMAGE, imageDao.getImgById(user.getProfileImage().getImageId()).get());
//        assertNull(imageDao.getImgById(user.getProfileImage().getImageId()).get().getImage());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "userId = " + user.getUserId()));
        assertEquals(2, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", "userId = " + user.getUserId()));
    }

    @Test(expected = DuplicateUserException.class)
    @Rollback
    public void testCreateDuplicateUser() throws DuplicateUserException {
        userDao.createUser("contra1", "usuario", "uno", "uno@mail.com", DEFAULT_ROLES, IMAGE);
    }

    @Test
    public void testGetUserById() {
        final Optional<UserModel> user = userDao.getUserById(1L);

//        ArrayList<Roles> arrayRoles = new ArrayList<>(user.get().getRoles());

        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals("contra1", user.get().getPassword());
        assertEquals("usuario", user.get().getName());
        assertEquals("uno", user.get().getSurname());
        assertEquals("uno@mail.com", user.get().getEmail());
        assertEquals(IMAGE, user.get().getProfileImage());
//        assertTrue(arrayRoles.contains(Roles.USER));
//        assertTrue(arrayRoles.contains(Roles.NOT_VERIFIED));
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
        assertEquals(new Long(1), user.get().getUserId());
        assertEquals("contra1", user.get().getPassword());
        assertEquals("usuario", user.get().getName());
        assertEquals("uno", user.get().getSurname());
        assertEquals("uno@mail.com", user.get().getEmail());
        assertEquals(IMAGE , user.get().getProfileImage());
        assertEquals(DEFAULT_ROLES, user.get().getRoles());
    }

    @Test
    public void testGetUserRoles() {
        final Collection<Roles> roles = userDao.getUserRoles(DEFAULT_USER);
        assertEquals(DEFAULT_ROLES, roles);
    }

    @Test
    public void testGetUserRolesModels() {
        final Collection<UserRoleModel> roleModels = userDao.getUserRolesModels(DEFAULT_USER);
        assertEquals(DEFAULT_ROLES_MODELS.size(), roleModels.size());
        final ArrayList<UserRoleModel> arrayRoles = new ArrayList<>(roleModels);
        assertTrue(arrayRoles.contains(new UserRoleModel(DEFAULT_USER, USER_MODEL)));
        assertTrue(arrayRoles.contains(new UserRoleModel(DEFAULT_USER, NOT_VERIFIED_MODEL)));
    }

    @Test
    public void testGetRoleByNameProvider(){
        final Optional<RoleModel> roleModel = userDao.getRoleByName(Roles.PROVIDER);
        assertTrue(roleModel.isPresent());
        assertEquals(new Long(1), roleModel.get().getRoleId());
        assertEquals(Roles.PROVIDER, roleModel.get().getRoleName());
    }
    @Test
    public void testGetRoleByNameUser(){
        final Optional<RoleModel> roleModel = userDao.getRoleByName(Roles.USER);
        assertTrue(roleModel.isPresent());
        assertEquals(new Long(2), roleModel.get().getRoleId());
        assertEquals(Roles.USER, roleModel.get().getRoleName());
    }
    @Test
    public void testGetRoleByNameVerified(){
        final Optional<RoleModel> roleModel = userDao.getRoleByName(Roles.VERIFIED);
        assertTrue(roleModel.isPresent());
        assertEquals(new Long(3), roleModel.get().getRoleId());
        assertEquals(Roles.VERIFIED, roleModel.get().getRoleName());
    }
    @Test
    public void testGetRoleByNameNotVerified(){
        final Optional<RoleModel> roleModel = userDao.getRoleByName(Roles.NOT_VERIFIED);
        assertTrue(roleModel.isPresent());
        assertEquals(new Long(4), roleModel.get().getRoleId());
        assertEquals(Roles.NOT_VERIFIED, roleModel.get().getRoleName());
    }

    @Test
    @Rollback
    public void testUpdateRoles(){
        final Optional<UserModel> userBeforeUpdate = userDao.getUserById(1L);
        final Optional<UserModel> user = userDao.updateRoles(DEFAULT_USER, Roles.NOT_VERIFIED, Roles.VERIFIED);
        assertTrue(user.isPresent());
        final ArrayList<Roles> arrayRoles = new ArrayList<>(user.get().getRoles());

        //Check if all the other info is the same
        assertEquals(userBeforeUpdate.get().getEmail(), user.get().getEmail());
        assertEquals(userBeforeUpdate.get().getName(), user.get().getName());
        assertEquals(userBeforeUpdate.get().getSurname(), user.get().getSurname());
        assertEquals(userBeforeUpdate.get().getPassword(), user.get().getPassword());
        assertEquals(userBeforeUpdate.get().getProfileImage(), user.get().getProfileImage());

        assertTrue(arrayRoles.contains(Roles.USER));
        assertTrue(arrayRoles.contains(Roles.VERIFIED));
        assertFalse(arrayRoles.contains(Roles.NOT_VERIFIED));
    }

    @Test
    @Rollback
    public void testUpdatePassword(){
        final Optional<UserModel> userBeforeUpdate = userDao.getUserById(1L);
        final Optional<UserModel> user = userDao.updatePassword(DEFAULT_USER, "newpwd");
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
        final Optional<UserModel> userBeforeUpdate = userDao.getUserById(1L);
        userDao.updateUserInfo(DEFAULT_USER, new UserInfo("newusuario", "newuno"));
        final Optional<UserModel> user = userDao.getUserById(1L);
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
        final Optional<UserModel> userBeforeUpdate = userDao.getUserById(1L);
        userDao.addRole(DEFAULT_USER, Roles.PROVIDER);
        final Optional<UserModel> user = userDao.getUserById(1L);

        assertTrue(user.isPresent());

        final ArrayList<Roles> arrayRoles = new ArrayList<>(user.get().getRoles());

        //Check if all the other info is the same
        assertEquals(userBeforeUpdate.get().getEmail(), user.get().getEmail());
        assertEquals(userBeforeUpdate.get().getName(), user.get().getName());
        assertEquals(userBeforeUpdate.get().getSurname(), user.get().getSurname());
        assertEquals(userBeforeUpdate.get().getPassword(), user.get().getPassword());
        assertEquals(userBeforeUpdate.get().getProfileImage(), user.get().getProfileImage());

        assertTrue(arrayRoles.contains(Roles.USER));
        assertTrue(arrayRoles.contains(Roles.NOT_VERIFIED));
        assertTrue(arrayRoles.contains(Roles.PROVIDER));
    }
}
