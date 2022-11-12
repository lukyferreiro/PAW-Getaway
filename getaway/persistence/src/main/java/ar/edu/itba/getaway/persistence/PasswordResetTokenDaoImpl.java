package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.PasswordResetToken;
import ar.edu.itba.getaway.interfaces.persistence.PasswordResetTokenDao;
import ar.edu.itba.getaway.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PasswordResetTokenDaoImpl implements PasswordResetTokenDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetTokenDaoImpl.class);

    @Override
    public PasswordResetToken createToken(UserModel user, String token, LocalDateTime expirationDate) {
        LOGGER.debug("Create password reset token for user with id {}", user.getUserId());
        final PasswordResetToken resetToken = new PasswordResetToken(token, user, expirationDate);
        em.persist(resetToken);
        return resetToken;
    }

    @Override
    public Optional<PasswordResetToken> getTokenByValue(String token) {
        LOGGER.debug("Get password reset token with value {}", token);
        final TypedQuery<PasswordResetToken> query = em.createQuery("FROM PasswordResetToken WHERE passtoken = :token", PasswordResetToken.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public void removeToken(PasswordResetToken passwordResetToken) {
        LOGGER.debug("Removing password reset token with id {}", passwordResetToken.getId());
        em.remove(passwordResetToken);
    }

    @Override
    public Optional<PasswordResetToken> getTokenByUser(UserModel user) {
        LOGGER.debug("Get password reset token for user with id {}", user.getUserId());
        final TypedQuery<PasswordResetToken> query = em.createQuery("FROM PasswordResetToken WHERE user = :user", PasswordResetToken.class);
        query.setParameter("user", user);
        return query.getResultList().stream().findFirst();
    }

}
