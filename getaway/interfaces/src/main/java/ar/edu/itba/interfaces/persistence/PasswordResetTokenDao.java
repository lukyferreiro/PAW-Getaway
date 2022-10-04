package ar.edu.itba.interfaces.persistence;

import ar.edu.itba.getaway.models.PasswordResetToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenDao {
    PasswordResetToken createToken (Long userId, String token, LocalDateTime expirationDate);
    Optional<PasswordResetToken> getTokenByValue (String token);
    void removeTokenById (Long id);
    void removeTokenByUserId (Long userId);
}
