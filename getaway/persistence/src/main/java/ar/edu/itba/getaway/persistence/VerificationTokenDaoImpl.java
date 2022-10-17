package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.VerificationToken;
import ar.edu.itba.getaway.interfaces.persistence.VerificationTokenDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class VerificationTokenDaoImpl implements VerificationTokenDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationTokenDaoImpl.class);

    @Override
    public VerificationToken createVerificationToken(UserModel user, String token, LocalDateTime expirationDate) {
        LOGGER.debug("Create verification token for user with id {}", user.getUserId());
        final VerificationToken verificationToken = new VerificationToken(token, user, expirationDate);
        em.persist(verificationToken);
        return verificationToken;
    }

    @Override
    public Optional<VerificationToken> getTokenByValue(String token) {
        LOGGER.debug("Get verification token with value {}", token);
        final TypedQuery<VerificationToken> query = em.createQuery("FROM VerificationToken where verifToken = :token", VerificationToken.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
    }


    @Override
    public void removeToken(VerificationToken verificationToken) {
        LOGGER.debug("Removing verification token with id {}", verificationToken.getId());
        em.remove(verificationToken);
    }

    @Override
    public Optional<VerificationToken> getTokenByUser(UserModel user) {
        LOGGER.debug("Get verification token for user with id {}", user.getUserId());
        final TypedQuery<VerificationToken> query = em.createQuery("FROM VerificationToken WHERE user = :user", VerificationToken.class);
        query.setParameter("user", user);
        return query.getResultList().stream().findFirst();
    }

}
