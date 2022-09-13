package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.PasswordResetToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenDao {
    Optional<PasswordResetToken> getToken(long id);
    PasswordResetToken createToken(long userId, String token, LocalDateTime expirationDate);
    Optional<PasswordResetToken> getTokenByValue(String token);
    void removeTokenById(long id);
    void removeTokenByUserId(long userId);
    Optional<PasswordResetToken> getTokenByUserId(long userId);
}
