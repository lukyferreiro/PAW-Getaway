package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.User;
import ar.edu.itba.getaway.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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

    private final Collection<Roles> roles = Arrays.asList(Roles.values().clone());

    private static final ResultSetExtractor<Collection<User>> USER_ROW_MAPPER = rs -> {
        Map<Long, User> userMap = new HashMap<>();
        long userId;
        while (rs.next()) {
            userId = rs.getLong("userId");
            if (!userMap.containsKey(userId)) {
                userMap.put(userId, new User(userId,
                        rs.getString("password"),
                        rs.getString("userName"),
                        rs.getString("userSurname"),
                        rs.getString("email"),
                        new ArrayList<>(),
                        rs.getLong("imgId")));
            }
            userMap.get(userId).addRole(Roles.valueOf(rs.getString("roleName")));
        }
        return userMap.values();
    };

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        userSimpleJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("users")
                .usingGeneratedKeyColumns("userId");
        roleSimpleJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("roles")
                .usingGeneratedKeyColumns("roleId");
    }

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public User createUser(String password, String name, String surname, String email, Collection<Roles> roles) throws DuplicateUserException {
        return null;
    }

    @Override
    public Collection<Roles> getUserRoles() {
        return null;
    }

    @Override
    public Optional<User> updateRoles(long userId, Roles oldVal, Roles newVal) {
        return Optional.empty();
    }

    @Override
    public Optional<User> updatePassword(long userId, String password) {
        return Optional.empty();
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, User user) {

    }

    @Override
    public void addRole(long userId, Roles newRole) {

    }

    @Override
    public void updateProfileImage(Long imageId, User user) {

    }
}
