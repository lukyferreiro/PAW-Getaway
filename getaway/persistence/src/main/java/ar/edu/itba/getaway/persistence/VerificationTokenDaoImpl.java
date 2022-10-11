package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.VerificationToken;
import ar.edu.itba.getaway.interfaces.persistence.VerificationTokenDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class VerificationTokenDaoImpl implements VerificationTokenDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationTokenDaoImpl.class);

    @Override
    public VerificationToken createVerificationToken(UserModel user, String token, LocalDateTime expirationDate) {
        final VerificationToken verificationToken = new VerificationToken(token, user, expirationDate);
        em.persist(verificationToken);
        return verificationToken;
    }

    @Override
    public Optional<VerificationToken> getTokenByValue(String token) {
        return em.createQuery("FROM VerificationToken where value = :token",
                        VerificationToken.class)
                .setParameter("token", token)
                .getResultList()
                .stream()
                .findFirst();
    }


    @Override
    public void removeToken(VerificationToken verificationToken) {
        em.remove(verificationToken);
    }

    @Override
    public Optional<VerificationToken> getTokenByUser(UserModel user) {
        return em.createQuery("FROM VerificationToken vt WHERE vt.user.id = :userId",
                        VerificationToken.class)
                .setParameter("userId", user.getUserId())
                .getResultList()
                .stream()
                .findFirst();
    }

}
