package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.interfaces.persistence.SessionRefreshTokenDao;
import ar.edu.itba.getaway.models.PasswordResetToken;
import ar.edu.itba.getaway.models.SessionRefreshToken;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.VerificationToken;
import ar.edu.itba.getaway.interfaces.persistence.PasswordResetTokenDao;
import ar.edu.itba.getaway.interfaces.persistence.VerificationTokenDao;
import ar.edu.itba.getaway.interfaces.services.EmailService;
import ar.edu.itba.getaway.interfaces.services.TokensService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class TokensServiceImpl implements TokensService {

    @Autowired
    private VerificationTokenDao verificationTokenDao;
    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;
    @Autowired
    private SessionRefreshTokenDao sessionRefreshTokenDao;
    @Autowired
    private EmailService emailService;
    @Autowired
    private String appBaseUrl;
    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(TokensServiceImpl.class);
    private final Locale locale = LocaleContextHolder.getLocale();

    @Override
    public VerificationToken generateVerificationToken(UserModel user) {
        String token = UUID.randomUUID().toString();
        LOGGER.debug("Created verification token {}", token);
        return verificationTokenDao.createVerificationToken(user, token, VerificationToken.generateTokenExpirationDate());
    }

    @Override
    public PasswordResetToken generatePasswordResetToken(UserModel user) {
        String token = UUID.randomUUID().toString();
        LOGGER.debug("Created password token {}", token);
        return passwordResetTokenDao.createToken(user, token, PasswordResetToken.generateTokenExpirationDate());
    }

    @Override
    public SessionRefreshToken generateSessionRefreshToken(UserModel user) {
        return sessionRefreshTokenDao.createToken(user,
                SessionRefreshToken.generateSessionToken(),
                SessionRefreshToken.generateTokenExpirationDate()
        );
    }

    @Override
    public void sendVerificationToken(UserModel userModel, VerificationToken token) {
        try {
//            final String url = new URL("http", appBaseUrl, 8080, "/webapp_war/user/verifyAccount/" + token.getValue()).toString();
            final String url = new URL("http", appBaseUrl, "/paw-2022b-1/user/verifyAccount/" + token.getValue()).toString();
            final Map<String, Object> variables = new HashMap<>();
            variables.put("confirmationURL", url);
            variables.put("to", userModel.getEmail());
            emailService.sendMail("verification", messageSource.getMessage("email.verifyAccount", new Object[]{}, locale), variables, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, mail to verify account not sent");
        }
    }

    @Override
    public void sendPasswordResetToken(UserModel userModel, PasswordResetToken token) {
        try {
//           final String url = new URL("http", appBaseUrl, 8080, "/webapp_war/user/resetPassword/" + token.getValue()).toString();
            final String url = new URL("http", appBaseUrl, "/paw-2022b-1/user/resetPassword/" + token.getValue()).toString();
            final Map<String, Object> variables = new HashMap<>();
            variables.put("confirmationURL", url);
            variables.put("to", userModel.getEmail());
            emailService.sendMail("passwordReset", messageSource.getMessage("email.resetPassword", new Object[]{}, locale), variables, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, mail to reset password not sent");
        }
    }

    @Transactional
    @Override
    public SessionRefreshToken getSessionRefreshToken(UserModel user) {
        LOGGER.debug("Retrieving session refresh token for user");
        final Optional<SessionRefreshToken> tokenOpt = sessionRefreshTokenDao.getTokenByUser(user);
        if (tokenOpt.isPresent()) {
            final SessionRefreshToken sessionRefreshToken = tokenOpt.get();
            if (!sessionRefreshToken.isValid()) {
                sessionRefreshToken.refresh();
            }
            return sessionRefreshToken;
        }
        return generateSessionRefreshToken(user);
    }

}
