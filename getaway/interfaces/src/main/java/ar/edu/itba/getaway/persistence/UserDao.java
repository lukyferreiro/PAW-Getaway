package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.User;
import ar.edu.itba.getaway.models.UserInfo;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    Optional<User> getUserById(long id);
    Optional<User> getUserByEmail(String email);
    User createUser(String password, String name, String surname, String email, Collection<Roles> roles) throws DuplicateUserException;
    Collection<Roles> getUserRoles();
    Optional<User> updateRoles(long userId, Roles oldVal, Roles newVal);
    Optional<User> updatePassword(long userId, String password);
    void updateUserInfo(UserInfo userInfo, User user);
    void addRole(long userId, Roles newRole);
    void updateProfileImage(Long imageId, User user);
}
