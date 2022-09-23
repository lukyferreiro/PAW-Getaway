package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.RoleModel;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserInfo;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.persistence.UserDao;
import ar.edu.itba.getaway.persistence.UserDaoImpl;
import org.hsqldb.rights.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunListener;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    /****/

    @Autowired
    private DataSource ds;

    @Autowired
    private UserDao userDao;

    private static final Collection<Roles> DEFAULT_ROLES = new ArrayList<>(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));
    private static final Collection<RoleModel> DEFAULT_ROLES_MODELS = new ArrayList<>(Arrays.asList(new RoleModel(new Long(2), Roles.USER), new RoleModel(new Long(4), Roles.NOT_VERIFIED)));

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
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

    @Test(expected = DuplicateUserException.class)
    @Rollback
    public void testCreateDuplicateUser() throws DuplicateUserException {
        userDao.createUser("contra1", "usuario", "uno", "uno@mail.com", DEFAULT_ROLES);
    }

    @Test
    public void testGetUserById() {
        final Optional<UserModel> user = userDao.getUserById(1);

        ArrayList<Roles> arrayRoles = new ArrayList<>(user.get().getRoles());

        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals("contra1", user.get().getPassword());
        assertEquals("usuario", user.get().getName());
        assertEquals("uno", user.get().getSurname());
        assertEquals("uno@mail.com", user.get().getEmail());
        assertEquals(new Long(0), user.get().getProfileImageId());
        assertTrue(arrayRoles.contains(Roles.USER));
        assertTrue(arrayRoles.contains(Roles.NOT_VERIFIED));
    }

//
//    @Test(expected = UserNotFound.class)
//    public void testGetUserByIdNotFound(){
//        userDao.getUserById(50);
//    }

    @Test
    public void testGetUserByEmail(){
        final Optional<UserModel> user = userDao.getUserByEmail("uno@mail.com");

        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(new Long(1), user.get().getId());
        assertEquals("contra1", user.get().getPassword());
        assertEquals("usuario", user.get().getName());
        assertEquals("uno", user.get().getSurname());
        assertEquals("uno@mail.com", user.get().getEmail());
        assertEquals(new Long(0), user.get().getProfileImageId());
        assertEquals(DEFAULT_ROLES, user.get().getRoles());
    }

    @Test
    public void testGetUserRoles() {
        Collection<Roles> roles = userDao.getUserRoles(1);
        assertEquals(DEFAULT_ROLES, roles);
    }

    @Test
    public void testGetUserRolesModels() {
        Collection<RoleModel> roleModels = userDao.getUserRolesModels(1);
        assertEquals(DEFAULT_ROLES_MODELS.size(), roleModels.size());
        ArrayList<RoleModel> arrayRoles = new ArrayList<>(roleModels);
        assertTrue(arrayRoles.contains(new RoleModel(new Long(2), Roles.USER)));
        assertTrue(arrayRoles.contains(new RoleModel(new Long(4), Roles.NOT_VERIFIED)));
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
        final Optional<UserModel> userBeforeUpdate = userDao.getUserById(1);
        final Optional<UserModel> user = userDao.updateRoles(1, Roles.NOT_VERIFIED, Roles.VERIFIED);
        assertTrue(user.isPresent());
        ArrayList<Roles> arrayRoles = new ArrayList<>(user.get().getRoles());

        //Check if all the other info is the same
        assertEquals(userBeforeUpdate.get().getEmail(), user.get().getEmail());
        assertEquals(userBeforeUpdate.get().getName(), user.get().getName());
        assertEquals(userBeforeUpdate.get().getSurname(), user.get().getSurname());
        assertEquals(userBeforeUpdate.get().getPassword(), user.get().getPassword());
        assertEquals(userBeforeUpdate.get().getProfileImageId(), user.get().getProfileImageId());

        assertTrue(arrayRoles.contains(Roles.USER));
        assertTrue(arrayRoles.contains(Roles.VERIFIED));
        assertFalse(arrayRoles.contains(Roles.NOT_VERIFIED));
    }

    @Test
    @Rollback
    public void testUpdatePassword(){
        final Optional<UserModel> userBeforeUpdate = userDao.getUserById(1);
        final Optional<UserModel> user = userDao.updatePassword(1, "newpwd");
        assertTrue(user.isPresent());

        //Check if all the other info is the same
        assertEquals(userBeforeUpdate.get().getEmail(), user.get().getEmail());
        assertEquals(userBeforeUpdate.get().getName(), user.get().getName());
        assertEquals(userBeforeUpdate.get().getSurname(), user.get().getSurname());
        assertEquals(userBeforeUpdate.get().getRoles(), user.get().getRoles());
        assertEquals(userBeforeUpdate.get().getProfileImageId(), user.get().getProfileImageId());

        assertEquals("newpwd",user.get().getPassword());
    }

    @Test
    @Rollback
    public void testUpdateUserInfo() {
        final Optional<UserModel> userBeforeUpdate = userDao.getUserById(1);
        userDao.updateUserInfo(1, new UserInfo("newusuario", "newuno"));
        final Optional<UserModel> user = userDao.getUserById(1);
        assertTrue(user.isPresent());

        //Check if all the other info is the same
        assertEquals(userBeforeUpdate.get().getEmail(), user.get().getEmail());
        assertEquals(userBeforeUpdate.get().getRoles(), user.get().getRoles());
        assertEquals(userBeforeUpdate.get().getPassword(), user.get().getPassword());
        assertEquals(userBeforeUpdate.get().getProfileImageId(), user.get().getProfileImageId());

        assertEquals("newusuario", user.get().getName());
        assertEquals("newuno", user.get().getSurname());
    }

    @Test
    @Rollback
    public void testAddRole() {
        final Optional<UserModel> userBeforeUpdate = userDao.getUserById(1);
        userDao.addRole(1, Roles.PROVIDER);
        final Optional<UserModel> user = userDao.getUserById(1);

        assertTrue(user.isPresent());

        ArrayList<Roles> arrayRoles = new ArrayList<>(user.get().getRoles());

        //Check if all the other info is the same
        assertEquals(userBeforeUpdate.get().getEmail(), user.get().getEmail());
        assertEquals(userBeforeUpdate.get().getName(), user.get().getName());
        assertEquals(userBeforeUpdate.get().getSurname(), user.get().getSurname());
        assertEquals(userBeforeUpdate.get().getPassword(), user.get().getPassword());
        assertEquals(userBeforeUpdate.get().getProfileImageId(), user.get().getProfileImageId());

        assertTrue(arrayRoles.contains(Roles.USER));
        assertTrue(arrayRoles.contains(Roles.NOT_VERIFIED));
        assertTrue(arrayRoles.contains(Roles.PROVIDER));
    }

    @Test
    @Rollback
    public void testUpdateProfileImage() {
        final Optional<UserModel> userBeforeUpdate = userDao.getUserById(1);
        userDao.updateProfileImage(1, 15);
        final Optional<UserModel> user = userDao.getUserById(1);

        assertTrue(user.isPresent());

        //Check if all the other info is the same
        assertEquals(userBeforeUpdate.get().getEmail(), user.get().getEmail());
        assertEquals(userBeforeUpdate.get().getName(), user.get().getName());
        assertEquals(userBeforeUpdate.get().getSurname(), user.get().getSurname());
        assertEquals(userBeforeUpdate.get().getPassword(), user.get().getPassword());
        assertEquals(userBeforeUpdate.get().getRoles(), user.get().getRoles());

        assertEquals(new Long(15), user.get().getProfileImageId());
    }
}
