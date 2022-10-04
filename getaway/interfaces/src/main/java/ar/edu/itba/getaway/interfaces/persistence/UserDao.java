package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.RoleModel;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.UserInfo;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    Collection<RoleModel> getUserRolesModels(Long userId);
    Optional<UserModel> getUserById (Long userId);
    Optional<UserModel> getUserByEmail (String email);
    Optional<UserModel> getUserByExperienceId(Long experienceId);
    Optional<UserModel> getUserByReviewId(Long reviewId);
    UserModel createUser (String password, String name, String surname, String email, Collection<Roles> roles, Long imageId) throws DuplicateUserException;
    Collection<Roles> getUserRoles (Long userId);
    Optional<RoleModel> getRoleByName (Roles role);
    Optional<UserModel> updateRoles (Long userId, Roles oldVal, Roles newVal);
    Optional<UserModel> updatePassword (Long userId, String password);
    void updateUserInfo (Long userId, UserInfo userInfo);
    void addRole (Long userId, Roles newRole);
}
