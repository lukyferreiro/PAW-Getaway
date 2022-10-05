package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    Collection<RoleModel> getUserRolesModels(Long userId);
    Optional<UserModel> getUserById (Long userId);
    Optional<UserModel> getUserByEmail (String email);
    Optional<UserModel> getUserByExperienceId(Long experienceId);
    Optional<UserModel> getUserByReviewId(Long reviewId);
    UserModel createUser (String password, String name, String surname, String email) throws DuplicateUserException;
    Optional<UserModel> verifyAccount (String token);
    void resendVerificationToken (UserModel userModel);
    boolean validatePasswordReset (String token);
    void generateNewPassword (UserModel userModel);
    Optional<UserModel> updatePassword (String token, String password);
    void updateUserInfo (Long userId, UserInfo userInfo);
    void updateProfileImage (UserModel userModel, byte[] image);
    void addRole(Long userId, Roles newRole);
}