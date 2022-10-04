package ar.edu.itba.getaway.persistence;

import ar.edu.itba.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.interfaces.persistence.UserDao;
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

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert userSimpleJdbcInsert;
    private final SimpleJdbcInsert userRolesSimpleJdbcInsert;
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
        this.userRolesSimpleJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("userRoles");
    }

    @Override
    public UserModel createUser(String password, String name, String surname, String email,
                                Collection<Roles> roles, Long imageId) throws DuplicateUserException {
        final Map<String, Object> userData = new HashMap<>();
        userData.put("userName", name);
        userData.put("userSurname", surname);
        userData.put("email", email);
        userData.put("password", password);
        userData.put("imgId", imageId);

        final Long userId;
        try {
            userId = userSimpleJdbcInsert.executeAndReturnKey(userData).longValue();
            LOGGER.info("Created user with id {}", userId);
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserException();
        }

        final Map<String, Object> userRolesData = new HashMap<>();
        userRolesData.put("userId", userId);

        Optional<RoleModel> roleModel;
        for (Roles role : roles) {
            roleModel = getRoleByName(role);
            userRolesData.put("roleId", roleModel.get().getRoleId());
            userRolesSimpleJdbcInsert.execute(userRolesData);
            LOGGER.info("Added role {} to user {}", roleModel.get().getRoleName().name() , userId);
        }

        return new UserModel(userId, password, name, surname, email, roles, imageId);
    }

    @Override
    public Optional<UserModel> getUserById(Long userId) {
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
    public Optional<UserModel> getUserByExperienceId(Long experienceId){
        final String query = "SELECT * FROM users WHERE userid = (SELECT userid FROM experiences WHERE experienceid = ?)";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{experienceId}, USER_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Optional<UserModel> getUserByReviewId(Long reviewId){
        final String query = "SELECT * FROM users WHERE userid = (SELECT userid FROM reviews WHERE reviewId = ?)";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{reviewId}, USER_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Collection<Roles> getUserRoles(Long userId) {
        final Collection<RoleModel> rolesModel = getUserRolesModels(userId);
        final Collection<Roles> rolesCollection = new ArrayList<>();
        for(RoleModel roleModel : rolesModel){
            rolesCollection.add(roleModel.getRoleName());
        }
        LOGGER.debug("Executing query to get roles of user with id: {}", userId);
        return rolesCollection;
    }

    @Override
    public Collection<RoleModel> getUserRolesModels(Long userId){
        final String query = "SELECT roleid, rolename FROM users NATURAL JOIN userroles NATURAL JOIN roles WHERE userid=?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query,
                new Object[]{userId}, ROLE_MODEL_ROW_MAPPER);
    }

    @Override
    public Optional<RoleModel> getRoleByName(Roles role) {
        final String query = "SELECT * FROM roles WHERE roleName = ?";
        LOGGER.debug("Executing query: {}", query);
        final String roleName = role.name();
        return jdbcTemplate.query(query, new Object[]{roleName}, ROLE_MODEL_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Optional<UserModel> updateRoles(Long userId, Roles oldVal, Roles newVal) {
        final String query = "UPDATE userroles SET roleid = ? WHERE userid = ? AND roleid = ?";
        LOGGER.debug("Executing query: {}", query);
        final Long oldValueID = getRoleByName(oldVal).get().getRoleId();
        final Long newValueID = getRoleByName(newVal).get().getRoleId();
        if (jdbcTemplate.update(query, newValueID, userId, oldValueID) == 1) {
            LOGGER.debug("Roles updated");
            return getUserById(userId);
        }
        LOGGER.debug("Roles not updated");
        return Optional.empty();
    }

    @Override
    public Optional<UserModel> updatePassword(Long userId, String password) {
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
    public void updateUserInfo(Long userId, UserInfo userInfo) {
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
    public void addRole(Long userId, Roles newRole) {
        final Map<String, Object> userRolesData = new HashMap<>();
        userRolesData.put("userId", userId);
        final Optional<RoleModel> roleModel = getRoleByName(newRole);
        userRolesData.put("roleId", roleModel.get().getRoleId());
        userRolesSimpleJdbcInsert.execute(userRolesData);
        LOGGER.info("Added role {} to user {}", newRole.name(), userId);
    }
}
