package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.persistence.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
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

        Collection<RoleModel> roleModels = new ArrayList<>();
        for (Roles role: roles) {
            roleModels.add(getRoleByName(role).get());
        }

        final UserModel userModel = new UserModel(password, name, surname, email, roleModels, image);
        em.persist(userModel);
        LOGGER.info("Created user with id {}", userModel.getUserId());

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

    @Override
    public Collection<RoleModel> getUserRoles(UserModel user) {
        return user.getRoles();
    }

    @Override
    public Collection<Roles> getRolesByUser (UserModel user){
        final List<Roles> userRoles = new ArrayList<>();
        final Collection<RoleModel> userRoleModels = user.getRoles();
        for (RoleModel role: userRoleModels) {
            userRoles.add(role.getRoleName());
        }
        return userRoles;
    }

    @Override
    public Optional<RoleModel> getRoleByName(Roles role) {
        LOGGER.debug("Get role with name {}", role.name());
        final TypedQuery<RoleModel> query = em.createQuery("FROM RoleModel WHERE roleName = :roleName", RoleModel.class);
        query.setParameter("roleName", role);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<UserModel> updateRoles(UserModel user, Roles oldVal, Roles newVal) {
        final RoleModel oldRole = getRoleByName(oldVal).get();
        user.removeRole(oldRole);

        final RoleModel newRole = getRoleByName(newVal).get();
        user.addRole(newRole);

        return Optional.ofNullable(em.merge(user));
    }

    @Override
    public Optional<UserModel> updatePassword(UserModel user, String password) {
        user.setPassword(password);
        return Optional.ofNullable(em.merge(user));
    }

    @Override
    public Optional<UserModel> updateUserInfo(UserModel user, UserInfo userInfo) {
        user.setName(userInfo.getName());
        user.setSurname(userInfo.getSurname());
        return Optional.ofNullable(em.merge(user));
    }

    @Override
    public Optional<UserModel> addRole(UserModel user, Roles newRole) {
        final RoleModel roleModel = getRoleByName(newRole).get();
        user.addRole(roleModel);
        return Optional.ofNullable(em.merge(user));
    }
}
