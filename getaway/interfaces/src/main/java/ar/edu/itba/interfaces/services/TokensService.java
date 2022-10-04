package ar.edu.itba.interfaces.services;

import ar.edu.itba.getaway.models.PasswordResetToken;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.VerificationToken;

public interface TokensService {

    VerificationToken generateVerificationToken(Long userId);
    PasswordResetToken generatePasswordResetToken(Long userId);
    void sendVerificationToken(UserModel userModel, VerificationToken token);
    void sendPasswordResetToken(UserModel userModel, PasswordResetToken token);
}
