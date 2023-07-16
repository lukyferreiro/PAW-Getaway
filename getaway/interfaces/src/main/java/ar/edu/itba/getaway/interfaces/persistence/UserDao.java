package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    UserModel createUser(String password, String name, String surname, String email, Collection<Roles> roles, ImageModel image) throws DuplicateUserException;

    Optional<UserModel> getUserById(long userId);

    Optional<UserModel> getUserByEmail(String email);

    Optional<RoleModel> getRoleByName(Roles role);

    boolean updateUser(long userId, UserModel user);

    Optional<UserModel> updateRoles(UserModel user, Roles oldVal, Roles newVal);

    Optional<UserModel> updatePassword(UserModel user, String password);

    Optional<UserModel> updateUserInfo(UserModel user, UserInfo userInfo);

    Optional<UserModel> addRole(UserModel user, Roles newRole);

    Optional<UserModel> getUserByExperienceId(long experienceId);

    Optional<UserModel> getUserByReviewId(long reviewId);
}
