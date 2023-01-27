//package ar.edu.itba.getaway.interfaces.persistence;
//
//import ar.edu.itba.getaway.models.SessionRefreshToken;
//import ar.edu.itba.getaway.models.UserModel;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//public interface SessionRefreshTokenDao {
//
//    SessionRefreshToken createToken(UserModel user, String token, LocalDateTime expirationDate);
//
//    Optional<SessionRefreshToken> getTokenByValue(String token);
//
//    void removeToken(SessionRefreshToken token);
//
//    Optional<SessionRefreshToken> getTokenByUser(UserModel user);
//}