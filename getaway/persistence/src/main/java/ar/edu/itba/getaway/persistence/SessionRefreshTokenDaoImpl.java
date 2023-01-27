//package ar.edu.itba.getaway.persistence;
//
//import ar.edu.itba.getaway.interfaces.persistence.SessionRefreshTokenDao;
//import ar.edu.itba.getaway.models.SessionRefreshToken;
//import ar.edu.itba.getaway.models.UserModel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Repository
//public class SessionRefreshTokenDaoImpl implements SessionRefreshTokenDao {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRefreshTokenDaoImpl.class);
//
//    @Override
//    public SessionRefreshToken createToken(UserModel user, String token, LocalDateTime expirationDate) {
//        LOGGER.debug("Create session refresh token for user with id {}", user.getUserId());
//        final SessionRefreshToken sessionToken = new SessionRefreshToken(token, user, expirationDate);
//        em.persist(sessionToken);
//        return sessionToken;
//    }
//
//    @Override
//    public Optional<SessionRefreshToken> getTokenByValue(String token) {
//        LOGGER.debug("Get session refresh token with value {}", token);
//        final TypedQuery<SessionRefreshToken> query = em.createQuery("FROM SessionRefreshToken WHERE sessiontoken = :token", SessionRefreshToken.class);
//        query.setParameter("token", token);
//        return query.getResultList().stream().findFirst();
//    }
//
//    @Override
//    public void removeToken(SessionRefreshToken sessionRefreshToken) {
//        LOGGER.debug("Removing session refresh token with id {}", sessionRefreshToken.getId());
//        em.remove(sessionRefreshToken);
//    }
//
//    @Override
//    public Optional<SessionRefreshToken> getTokenByUser(UserModel user) {
//        LOGGER.debug("Get session refresh token for user with id {}", user.getUserId());
//        final TypedQuery<SessionRefreshToken> query = em.createQuery("FROM SessionRefreshToken WHERE user = :user", SessionRefreshToken.class);
//        query.setParameter("user", user);
//        return query.getResultList().stream().findFirst();
//    }
//}
