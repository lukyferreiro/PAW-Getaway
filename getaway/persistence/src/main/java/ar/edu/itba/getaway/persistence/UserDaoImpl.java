package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.RoleModel;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private DataSource ds;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert userSimpleJdbcInsert;
    private final SimpleJdbcInsert roleSimpleJdbcInsert;
    private final SimpleJdbcInsert userRolesSimpleJdbcInsert;

    private final Collection<Roles> roles = Arrays.asList(Roles.values().clone());

//    private static final ResultSetExtractor<Collection<UserModel>> USER_ROW_MAPPER = rs -> {
//        Map<Long, UserModel> userMap = new HashMap<>();
//        long userId;
//        while (rs.next()) {
//            userId = rs.getLong("userId");
//            if (!userMap.containsKey(userId)) {
//                userMap.put(userId, new UserModel(userId,
//                        rs.getString("password"),
//                        rs.getString("userName"),
//                        rs.getString("userSurname"),
//                        rs.getString("email"),
//                        new ArrayList<>(),
//                        rs.getLong("imgId")));
//            }
//            userMap.get(userId).addRole(Roles.valueOf(rs.getString("roleName")));
//        }
//        return userMap.values();
//    };

    private static final RowMapper<UserModel> USER_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new UserModel(rs.getLong("userid"),
                    rs.getString("password"),
                    rs.getString("userName"),
                    rs.getString("userSurname"),
                    rs.getString("email"),
                    new ArrayList<>(),
                    rs.getLong("imgId"));

    private static final RowMapper<RoleModel> ROLE_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new RoleModel(rs.getLong("roleId"),
                    Roles.valueOf(rs.getString("roleName")));


    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        userSimpleJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("users")
                .usingGeneratedKeyColumns("userid");
        roleSimpleJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("roles");
        userRolesSimpleJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("userRoles");
    }

    @Override
    public Optional<UserModel> getUserById(long userId) {
        return jdbcTemplate.query("SELECT userId, userName, userSurname, email, imgId, password FROM users WHERE userId = ?",
                new Object[]{userId}, USER_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        return jdbcTemplate.query("SELECT userId, userName, userSurname, email, imgId, password FROM users WHERE email = ?",
                new Object[]{email}, USER_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public UserModel createUser(String password, String name, String surname, String email, Collection<Roles> roles) throws DuplicateUserException {
        final Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userName", name);
        userInfo.put("userSurname", surname);
        userInfo.put("email", email);
        userInfo.put("imgId", null);
        userInfo.put("password", password);

        final long userId;
        try {
            userId = userSimpleJdbcInsert.executeAndReturnKey(userInfo).longValue();
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserException();
        }

        Map<String, Object> userRoles = new HashMap<>();
        userRoles.put("userId", userId);

        Optional<RoleModel> roleModel;
        for (Roles role : roles) {
            roleModel = getRoleByName(role);
            userRoles.put("roleId", roleModel.get().getRoleId());
            userRolesSimpleJdbcInsert.execute(userRoles);
        }

        return new UserModel(userId, password, name, surname, email, roles, null);
    }

    @Override
    public Collection<Roles> getUserRoles() {
        return roles;
    }

    @Override
    public Optional<RoleModel> getRoleByName(Roles role) {
        String roleName = role.name();
        return jdbcTemplate.query("SELECT * FROM roles WHERE roleName = ?",
                new Object[]{roleName}, ROLE_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<UserModel> updateRoles(long userId, Roles oldVal, Roles newVal) {
        Long oldValueID = getRoleByName(oldVal).get().getRoleId();
        Long newValueID = getRoleByName(newVal).get().getRoleId();
        if (jdbcTemplate.update("UPDATE userRoles SET roleId = ? WHERE userId = ? AND roleId = ?",
                oldValueID, userId, newValueID) == 1) {
            return getUserById(userId);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserModel> updatePassword(long userId, String password) {
        if (jdbcTemplate.update("UPDATE users SET password = ? WHERE userId = ?",
                password, userId) == 1) {
            return getUserById(userId);
        }
        return Optional.empty();
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, UserModel userModel) {
        jdbcTemplate.update("UPDATE users SET userName = ?, userSurname = ? WHERE userId = ?",
                userInfo.getName(), userInfo.getSurname(), userModel.getId());
    }

    @Override
    public void addRole(long userId, Roles newRole) {
        Map<String, Object> userRoles = new HashMap<>();
        userRoles.put("roleId", userId);
        userRoles.put("roleName", newRole.name());
        roleSimpleJdbcInsert.execute(userRoles);
    }

    @Override
    public void updateProfileImage(Long imageId, UserModel userModel) {
        jdbcTemplate.update("UPDATE users SET imgId = ? where userId = ?",
                imageId, userModel.getId());
    }
}
