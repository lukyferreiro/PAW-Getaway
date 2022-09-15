package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.models.User;
import ar.edu.itba.getaway.models.UserInfo;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(long id);
    Optional<User> getUserByEmail(String email);
    User createUser(String password, String name, String surname, String email) throws DuplicateUserException;
    Optional<User> verifyAccount(String token);
    void resendVerificationToken(User user);
    boolean validatePasswordReset(String token);
    void generateNewPassword(User user);
    Optional<User> updatePassword(String token, String password);
    void updateUserInfo(UserInfo userInfo, User user);
    void updateProfileImage(ImageModel imageDto, User user);
}
