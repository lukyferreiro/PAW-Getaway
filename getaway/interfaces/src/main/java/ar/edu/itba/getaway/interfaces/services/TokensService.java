package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.PasswordResetToken;
//import ar.edu.itba.getaway.models.SessionRefreshToken;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.VerificationToken;

public interface TokensService {
    VerificationToken generateVerificationToken(UserModel user);

    PasswordResetToken generatePasswordResetToken(UserModel user);
//    SessionRefreshToken generateSessionRefreshToken(UserModel user);

    void sendVerificationToken(UserModel userModel, VerificationToken token);

    void sendPasswordResetToken(UserModel userModel, PasswordResetToken token);
//    SessionRefreshToken getSessionRefreshToken(UserModel user);
}
