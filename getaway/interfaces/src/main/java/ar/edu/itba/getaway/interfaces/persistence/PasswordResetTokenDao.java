package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.PasswordResetToken;
import ar.edu.itba.getaway.models.UserModel;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenDao {
    PasswordResetToken createToken (UserModel user, String token, LocalDateTime expirationDate);
    Optional<PasswordResetToken> getTokenByValue (String token);
    void removeToken (PasswordResetToken passwordResetToken);
    Optional<PasswordResetToken> getTokenByUser(UserModel user);
}
