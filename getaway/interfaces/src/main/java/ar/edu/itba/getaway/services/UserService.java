package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    Collection<RoleModel> getUserRoles(long userId);
    Optional<UserModel> getUserById (long userId);
    Optional<UserModel> getUserByEmail (String email);
    UserModel createUser (String password, String name, String surname, String email) throws DuplicateUserException;
    Optional<UserModel> verifyAccount (String token);
    void resendVerificationToken (UserModel userModel);
    boolean validatePasswordReset (String token);
    void generateNewPassword (UserModel userModel);
    Optional<UserModel> updatePassword (String token, String password);
    void updateUserInfo (UserInfo userInfo, UserModel userModel);
    void updateProfileImage (ImageModel imageModel, UserModel userModel);
}
