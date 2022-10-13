package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.persistence.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.sql.DataSource;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public UserModel createUser(String password, String name, String surname, String email,
                                Collection<Roles> roles, ImageModel image) throws DuplicateUserException {

        Optional<UserModel> user = getUserByEmail(email);

        if(user.isPresent()){
            throw new DuplicateUserException();
        }

        final UserModel userModel = new UserModel(password, name, surname, email, roles, image);
        em.persist(userModel);
        LOGGER.info("Created user with id {}", userModel.getUserId());

        Optional<RoleModel> roleModel;
        for (Roles role : roles) {
            roleModel = getRoleByName(role);

            addRole(userModel, roleModel.get().getRoleName());

//            UserRoleModel userRoleModel = new UserRoleModel(userModel, roleModel.get());
//            em.persist(userRoleModel);
//            LOGGER.debug("Added role {} to user with id {}", role, userModel.getUserId());
        }

        return userModel;
    }

    @Override
    public Optional<UserModel> getUserById(Long userId) {
        LOGGER.debug("Get user with id {}", userId);
        return Optional.ofNullable(em.find(UserModel.class, userId));
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        LOGGER.debug("Get user with email {}", email);
        final TypedQuery<UserModel> query = em.createQuery("FROM UserModel WHERE email = :email", UserModel.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

//    @Override
//    public Optional<UserModel> getUserByExperienceId(Long experienceId){
////        final String query = "SELECT * FROM users WHERE userid = (SELECT userid FROM experiences WHERE experienceid = ?)";
////        LOGGER.debug("Executing query: {}", query);
////        return jdbcTemplate.query(query, new Object[]{experienceId}, USER_MODEL_ROW_MAPPER)
////                .stream().findFirst();
//        return Optional.empty();
//    }

//    @Override
//    public Optional<UserModel> getUserByReviewId(Long reviewId){
//        final String query = "SELECT * FROM users WHERE userid = (SELECT userid FROM reviews WHERE reviewId = ?)";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{reviewId}, USER_MODEL_ROW_MAPPER)
//                .stream().findFirst();
//        return Optional.empty();
//    }

    @Override
    public Collection<Roles> getUserRoles(UserModel user) {
        return user.getRoles();
    }

    @Override
    public Collection<UserRoleModel> getUserRolesModels(UserModel user){
        LOGGER.debug("Get roles of user with id {}", user.getUserId());
        final TypedQuery<UserRoleModel> query = em.createQuery("FROM UserRoleModel WHERE user = :user", UserRoleModel.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public Optional<RoleModel> getRoleByName(Roles role) {
        LOGGER.debug("Get role with name {}", role.name());
        final TypedQuery<RoleModel> query = em.createQuery("FROM RoleModel WHERE roleName = :roleName", RoleModel.class);
        query.setParameter("roleName", role);
        return query.getResultList().stream().findFirst();
    }

    private Optional<UserRoleModel> getUserRole(UserModel user, RoleModel role) {
        final TypedQuery<UserRoleModel> query = em.createQuery("FROM UserRoleModel WHERE user = :user AND role = :role", UserRoleModel.class);
        query.setParameter("user", user);
        query.setParameter("role", role);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<UserModel> updateRoles(UserModel user, Roles oldVal, Roles newVal) {
//        final String query = "UPDATE userroles SET roleid = ? WHERE userid = ? AND roleid = ?";
//        LOGGER.debug("Executing query: {}", query);
//        final Long oldValueID = getRoleByName(oldVal).get().getRoleId();
//        final Long newValueID = getRoleByName(newVal).get().getRoleId();
//        if (jdbcTemplate.update(query, newValueID, userId, oldValueID) == 1) {
//            LOGGER.debug("Roles updated");
//            return getUserById(userId);
//        }
//        LOGGER.debug("Roles not updated");
//        return Optional.empty();

        final RoleModel oldRole = getRoleByName(oldVal).get();
        Optional<UserRoleModel> userRoleModel = getUserRole(user, oldRole);

        final RoleModel newRole = getRoleByName(newVal).get();
        userRoleModel.get().setRole(newRole);

//        return Optional.ofNullable(em.merge(userRoleModel).get().getUser());

        return Optional.empty();
    }

    @Override
    public Optional<UserModel> updatePassword(UserModel user, String password) {
//        final String query = "UPDATE users SET password = ? WHERE userId = ?";
//        LOGGER.debug("Executing query: {}", query);
//        if (jdbcTemplate.update(query, password, userId) == 1) {
//            LOGGER.debug("Password updated");
//            return getUserById(userId);
//        }
//        LOGGER.debug("Password not updated");
//        return Optional.empty();

        user.setPassword(password);
        return Optional.ofNullable(em.merge(user));

//        return Optional.empty();
    }

    @Override
    public void updateUserInfo(UserModel user, UserInfo userInfo) {
//        final String query = "UPDATE users SET userName = ?, userSurname = ? WHERE userId = ?";
//        LOGGER.debug("Executing query: {}", query);
//        if (jdbcTemplate.update(query, userInfo.getName(), userInfo.getSurname(), userId) == 1) {
//            LOGGER.debug("User info updated");
//        }
//        else {
//            LOGGER.debug("User info not updated");
//        }
        user.setName(userInfo.getName());
        user.setSurname(userInfo.getSurname());
        em.merge(user);
    }

    @Override
    public void addRole(UserModel user, Roles newRole) {
//        final Map<String, Object> userRolesData = new HashMap<>();
//        userRolesData.put("userId", userId);
//        final Optional<RoleModel> roleModel = getRoleByName(newRole);
//        userRolesData.put("roleId", roleModel.get().getRoleId());
//        userRolesSimpleJdbcInsert.execute(userRolesData);
//        LOGGER.info("Added role {} to user {}", newRole.name(), userId);

        final RoleModel roleModel = getRoleByName(newRole).get();
        final UserRoleModel userRoleModel = new UserRoleModel(user, roleModel);
        em.persist(userRoleModel);
    }
}
