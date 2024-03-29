package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    UserModel createUser(String password, String name, String surname, String email) throws DuplicateUserException;

    Optional<UserModel> getUserById(long userId);

    Optional<UserModel> getUserByEmail(String email);

    Optional<UserModel> getUserByExperienceId(long experienceId);

    Optional<UserModel> getUserByReviewId(long reviewId);

    Optional<UserModel> verifyAccount(String token);

    void resendVerificationToken(UserModel userModel);

    boolean validatePasswordReset(String token);

    void generateNewPassword(String mail);

    boolean updateUser(long userId, UserModel user);

    Optional<UserModel> updatePassword(String token, String password);

    void updateUserInfo(long userId, UserInfo userInfo);

    void addRole(UserModel user, Roles newRole);

    Collection<Roles> getRolesByUser(UserModel user);

}
