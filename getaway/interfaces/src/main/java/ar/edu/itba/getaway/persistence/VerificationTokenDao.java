package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.VerificationToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenDao {
    Optional<VerificationToken> getVerificationToken (long id);
    VerificationToken createVerificationToken (long userId, String token, LocalDateTime expirationDate);
    Optional<VerificationToken> getTokenByValue (String token);
    void removeTokenById (long id);
    void removeTokenByUserId (long userId);
    Optional<VerificationToken> getTokenByUserId (long userId);
}