package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    UserModel createUser (String password, String name, String surname, String email) throws DuplicateUserException;
    Collection<RoleModel> getUserRolesModels(UserModel user);
    Optional<UserModel> getUserById (Long userId);
    Optional<UserModel> getUserByEmail (String email);
    Optional<UserModel> getUserByExperience(ExperienceModel experience);
    Optional<UserModel> getUserByReview(ReviewModel review);
    Optional<UserModel> verifyAccount (String token);
    void resendVerificationToken (UserModel userModel);
    boolean validatePasswordReset (String token);
    void generateNewPassword (UserModel userModel);
    Optional<UserModel> updatePassword (String token, String password);
    void updateUserInfo (UserModel userModel, UserInfo userInfo);
    void updateProfileImage (UserModel userModel, byte[] image);
    void addRole(UserModel user, Roles newRole);
}
