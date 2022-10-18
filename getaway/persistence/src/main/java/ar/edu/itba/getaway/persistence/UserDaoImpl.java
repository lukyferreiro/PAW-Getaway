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

//        Optional<RoleModel> roleModel;
//        for (RoleModel roleModel : roleModels) {
////            roleModel = getRoleByName(role);
//            addRole(userModel, roleModel);
////            UserRoleModel userRoleModel = new UserRoleModel(userModel, roleModel.get());
////            em.persist(userRoleModel);
////            LOGGER.debug("Added role {} to user with id {}", role, userModel.getUserId());
//        }

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
    public Optional<UserModel> getUserByExperience(ExperienceModel experience){
        return Optional.ofNullable(experience.getUser());
    }

    @Override
    public Optional<UserModel> getUserByReview(ReviewModel review){
        return Optional.ofNullable(review.getUser());
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

//    @Override
//    public Collection<UserRoleModel> getUserRolesModels(UserModel user){
//        LOGGER.debug("Get roles of user with id {}", user.getUserId());
//        final TypedQuery<UserRoleModel> query = em.createQuery("FROM UserRoleModel WHERE user = :user", UserRoleModel.class);
//        query.setParameter("user", user);
//        return query.getResultList();
//    }

    @Override
    public Optional<RoleModel> getRoleByName(Roles role) {
        LOGGER.debug("Get role with name {}", role.name());
        final TypedQuery<RoleModel> query = em.createQuery("FROM RoleModel WHERE roleName = :roleName", RoleModel.class);
        query.setParameter("roleName", role);
        return query.getResultList().stream().findFirst();
    }

//    private Optional<UserRoleModel> getUserRole(UserModel user, RoleModel role) {
//        final TypedQuery<UserRoleModel> query = em.createQuery("FROM UserRoleModel WHERE user = :user AND role = :role", UserRoleModel.class);
//        query.setParameter("user", user);
//        query.setParameter("role", role);
//        return query.getResultList().stream().findFirst();
//    }

    @Override
    public Optional<UserModel> updateRoles(UserModel user, Roles oldVal, Roles newVal) {
        final RoleModel oldRole = getRoleByName(oldVal).get();
        user.removeRole(oldRole);

        System.out.println("Elimino role oldRole");
        for (RoleModel role : user.getRoles()
             ) {
            System.out.println(role.getRoleName());
        }

        final RoleModel newRole = getRoleByName(newVal).get();
        user.addRole(newRole);

        System.out.println("Agrego role newRole");
        for (RoleModel role : user.getRoles()
        ) {
            System.out.println(role.getRoleName());
        }

        em.remove(oldRole);
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

        System.out.println("Agrego role newRole");
        for (RoleModel role : user.getRoles()) {
            System.out.println(role.getRoleName());
        }

        return Optional.ofNullable(em.merge(user));
//        final UserRoleModel userRoleModel = new UserRoleModel(user, roleModel);
//        em.persist(userRoleModel);
    }

}
