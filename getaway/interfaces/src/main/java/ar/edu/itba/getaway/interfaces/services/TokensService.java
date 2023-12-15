package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.PasswordResetToken;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.VerificationToken;

public interface TokensService {
    VerificationToken generateVerificationToken(UserModel user);

    PasswordResetToken generatePasswordResetToken(UserModel user);

    void sendVerificationToken(UserModel userModel, VerificationToken token);

    void sendPasswordResetToken(UserModel userModel, PasswordResetToken token);
    boolean validateVerificationToken(UserModel userModel, String token);
    boolean validatePasswordResetToken(UserModel userModel, String token);
}
