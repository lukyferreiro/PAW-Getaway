package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private ImageDao imageDao;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert userSimpleJdbcInsert;
    private final SimpleJdbcInsert roleSimpleJdbcInsert;
    private final SimpleJdbcInsert userRolesSimpleJdbcInsert;
    private final SimpleJdbcInsert imagesSimpleJdbcInsert;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private final RowMapper<UserModel> USER_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new UserModel(rs.getLong("userid"),
                    rs.getString("password"),
                    rs.getString("userName"),
                    rs.getString("userSurname"),
                    rs.getString("email"),
                    getUserRoles(rs.getLong("userid")),
                    rs.getLong("imgId"));

    private static final RowMapper<RoleModel> ROLE_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new RoleModel(rs.getLong("roleid"),
                    Roles.valueOf(rs.getString("roleName")));


    @Autowired
    public UserDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.userSimpleJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("users")
                .usingGeneratedKeyColumns("userid");
        this.roleSimpleJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("roles");
        this.userRolesSimpleJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("userRoles");
        this.imagesSimpleJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("images")
                .usingGeneratedKeyColumns("imgid");
    }

    @Override
    public UserModel createUser(String password, String name, String surname, String email,
                                Collection<Roles> roles) throws DuplicateUserException {
        final Map<String, Object> userData = new HashMap<>();
        userData.put("userName", name);
        userData.put("userSurname", surname);
        userData.put("email", email);
        userData.put("password", password);

        final ImageModel imageModel = imageDao.createImg(null);
        userData.put("imgId", imageModel.getId());

        final long userId;
        try {
            userId = userSimpleJdbcInsert.executeAndReturnKey(userData).longValue();
            LOGGER.info("Created user with id {}", userId);
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserException();
        }

        Map<String, Object> userRolesData = new HashMap<>();
        userRolesData.put("userId", userId);

        Optional<RoleModel> roleModel;
        for (Roles role : roles) {
            roleModel = getRoleByName(role);
            userRolesData.put("roleId", roleModel.get().getRoleId());
            userRolesSimpleJdbcInsert.execute(userRolesData);
            LOGGER.info("Added role {} to user {}", roleModel.get().getRoleName().name() , userId);
        }

        return new UserModel(userId, password, name, surname, email, roles, imageModel.getId());
    }

    @Override
    public Optional<UserModel> getUserById(long userId) {
        final String query = "SELECT * FROM users WHERE userId = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{userId}, USER_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        final String query = "SELECT * FROM users WHERE email = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{email}, USER_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Collection<Roles> getUserRoles(long userId) {
        Collection<RoleModel> rolesModel = getUserRolesModels(userId);
        Collection<Roles> rolesCollection = new ArrayList<>();
        for(RoleModel roleModel : rolesModel){
            rolesCollection.add(roleModel.getRoleName());
        }
        LOGGER.debug("Executing query to get roles of user with id: {}", userId);
        return rolesCollection;
    }

    @Override
    public Collection<RoleModel> getUserRolesModels(long userId){
        final String query = "SELECT roleid, rolename FROM users NATURAL JOIN userroles NATURAL JOIN roles WHERE userid=?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query,
                new Object[]{userId}, ROLE_MODEL_ROW_MAPPER);
    }

    @Override
    public Optional<RoleModel> getRoleByName(Roles role) {
        final String query = "SELECT * FROM roles WHERE roleName = ?";
        LOGGER.debug("Executing query: {}", query);
        String roleName = role.name();
        return jdbcTemplate.query(query, new Object[]{roleName}, ROLE_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Optional<UserModel> updateRoles(long userId, Roles oldVal, Roles newVal) {
        final String query = "UPDATE userroles SET roleid = ? WHERE userid = ? AND roleid = ?";
        LOGGER.debug("Executing query: {}", query);
        Long oldValueID = getRoleByName(oldVal).get().getRoleId();
        Long newValueID = getRoleByName(newVal).get().getRoleId();
        if (jdbcTemplate.update(query, newValueID, userId, oldValueID) == 1) {
            LOGGER.debug("Roles updated");
            return getUserById(userId);
        }
        LOGGER.debug("Roles not updated");
        return Optional.empty();
    }

    @Override
    public Optional<UserModel> updatePassword(long userId, String password) {
        final String query = "UPDATE users SET password = ? WHERE userId = ?";
        LOGGER.debug("Executing query: {}", query);
        if (jdbcTemplate.update(query, password, userId) == 1) {
            LOGGER.debug("Password updated");
            return getUserById(userId);
        }
        LOGGER.debug("Password not updated");
        return Optional.empty();
    }

    @Override
    public void updateUserInfo(long userId, UserInfo userInfo) {
        final String query = "UPDATE users SET userName = ?, userSurname = ? WHERE userId = ?";
        LOGGER.debug("Executing query: {}", query);
        if (jdbcTemplate.update(query, userInfo.getName(), userInfo.getSurname(), userId) == 1) {
            LOGGER.debug("User info updated");
        }
        else {
            LOGGER.debug("User info not updated");
        }
    }

    @Override
    public void addRole(long userId, Roles newRole) {
        Map<String, Object> userRolesData = new HashMap<>();
        userRolesData.put("userId", userId);
        Optional<RoleModel> roleModel = getRoleByName(newRole);
        userRolesData.put("roleId", roleModel.get().getRoleId());
        userRolesSimpleJdbcInsert.execute(userRolesData);
        LOGGER.info("Added role {} to user {}", newRole.name(), userId);
    }
}
