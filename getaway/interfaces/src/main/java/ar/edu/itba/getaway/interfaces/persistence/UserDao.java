package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    UserModel createUser (String password, String name, String surname, String email, Collection<Roles> roles, ImageModel image) throws DuplicateUserException;
    Optional<UserModel> getUserById (Long userId);
    Optional<UserModel> getUserByEmail (String email);
    Optional<UserModel> getUserByExperience(ExperienceModel experience);
    Optional<UserModel> getUserByReview(ReviewModel review);
    Collection<RoleModel> getUserRoles (UserModel user);
    Optional<RoleModel> getRoleByName (Roles role);
    Optional<UserModel> updateRoles (UserModel user, Roles oldVal, Roles newVal);
    Optional<UserModel> updatePassword (UserModel user, String password);
    Optional<UserModel> updateUserInfo (UserModel user, UserInfo userInfo);
    Optional<UserModel> addRole (UserModel user, Roles newRole);
}
