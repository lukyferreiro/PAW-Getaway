package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.VerificationToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenDao {
    VerificationToken createVerificationToken (Long userId, String token, LocalDateTime expirationDate);
    Optional<VerificationToken> getTokenByValue (String token);
    void removeTokenById (Long id);
    void removeTokenByUserId (Long userId);
}