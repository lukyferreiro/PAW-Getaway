package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.PasswordResetToken;
import ar.edu.itba.getaway.interfaces.persistence.PasswordResetTokenDao;
import ar.edu.itba.getaway.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PasswordResetTokenDaoImpl implements PasswordResetTokenDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetTokenDaoImpl.class);

    @Override
    public PasswordResetToken createToken(UserModel user, String token, LocalDateTime expirationDate) {
        final PasswordResetToken resetToken = new PasswordResetToken(token, user, expirationDate);
        em.persist(resetToken);
        return resetToken;
    }

    @Override
    public Optional<PasswordResetToken> getTokenByValue(String token) {
        return em.createQuery("FROM PasswordResetToken WHERE value = :token",
                        PasswordResetToken.class)
                .setParameter("token", token)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public void removeToken(PasswordResetToken passwordResetToken) {
        em.remove(passwordResetToken);
    }

    @Override
    public Optional<PasswordResetToken> getTokenByUser(UserModel user) {
        return em.createQuery("FROM PasswordResetToken prt WHERE prt.user.id = :userId",
                        PasswordResetToken.class)
                .setParameter("userId", user.getUserId())
                .getResultList()
                .stream()
                .findFirst();

    }

}
