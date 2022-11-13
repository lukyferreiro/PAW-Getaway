package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.VerificationToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenDao {
    VerificationToken createVerificationToken(UserModel user, String token, LocalDateTime expirationDate);

    Optional<VerificationToken> getTokenByValue(String token);

    void removeToken(VerificationToken verificationToken);

    Optional<VerificationToken> getTokenByUser(UserModel user);
}