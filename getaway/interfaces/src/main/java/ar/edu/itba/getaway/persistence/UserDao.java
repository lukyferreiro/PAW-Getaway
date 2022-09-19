package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.RoleModel;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.UserInfo;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    Collection<RoleModel> getUserRolesModels(long userId);
    Optional<UserModel> getUserById (long userId);
    Optional<UserModel> getUserByEmail (String email);
    UserModel createUser (String password, String name, String surname, String email, Collection<Roles> roles) throws DuplicateUserException;
    Collection<Roles> getUserRoles (long userId);
    Optional<RoleModel> getRoleByName (Roles role);
    Optional<UserModel> updateRoles (long userId, Roles oldVal, Roles newVal);
    Optional<UserModel> updatePassword (long userId, String password);
    void updateUserInfo (UserInfo userInfo, UserModel userModel);
    void addRole (long userId, Roles newRole);
    void updateProfileImage (Long imageId, UserModel userModel);
}
