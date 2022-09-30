package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.persistence.PasswordResetTokenDao;
import ar.edu.itba.getaway.persistence.UserDao;
import ar.edu.itba.getaway.persistence.VerificationTokenDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private VerificationTokenDao verificationTokenDao;
    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private String appBaseUrl;
    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final Locale locale = LocaleContextHolder.getLocale();
    private final Collection<Roles> DEFAULT_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    @Override
    public Collection<RoleModel> getUserRolesModels(long userId) {
        LOGGER.debug("Retrieving roles of user with id {}", userId);
        return userDao.getUserRolesModels(userId);
    }

    @Override
    public Optional<UserModel> getUserById(long id) {
        LOGGER.debug("Retrieving user with id {}", id);
        return userDao.getUserById(id);
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        LOGGER.debug("Retrieving user with email {}", email);
        return userDao.getUserByEmail(email);
    }

    @Transactional
    @Override
    public UserModel createUser(String password, String name, String surname, String email) throws DuplicateUserException {
        LOGGER.debug("Creating user with email {}", email);
        UserModel userModel = userDao.createUser(passwordEncoder.encode(password), name, surname, email, DEFAULT_ROLES);
        LOGGER.debug("Created user with id {}", userModel.getId());
        LOGGER.debug("Creating verification token to user with id {}", userModel.getId());
        VerificationToken token = generateVerificationToken(userModel.getId());
        LOGGER.debug("Created verification token with id {}", token.getId());
        sendVerificationToken(userModel, token);
        return userModel;
    }

    @Transactional
    @Override
    public Optional<UserModel> verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenDao.getTokenByValue(token);

        if (!verificationTokenOptional.isPresent()) {
            LOGGER.warn("No verification token with value {}", token);
            return Optional.empty();
        }

        final VerificationToken verificationToken = verificationTokenOptional.get();
        //Eliminamos el token siempre, ya sea valido o no
        verificationTokenDao.removeTokenById(verificationToken.getId());
        LOGGER.debug("Removed verification token with id {}", verificationToken.getId());

        if (!verificationToken.isValid()) {
            LOGGER.warn("Verification token with value {} is invalid", token);
            return Optional.empty();
        }

        LOGGER.debug("Validating user with id {}", verificationToken.getUserId());
        return userDao.updateRoles(verificationToken.getUserId(), Roles.NOT_VERIFIED, Roles.VERIFIED);
    }

    @Transactional
    @Override
    public void resendVerificationToken(UserModel userModel) {
        LOGGER.debug("Removing verification token for user with id {}", userModel.getId());
        verificationTokenDao.removeTokenByUserId(userModel.getId());
        VerificationToken verificationToken = generateVerificationToken(userModel.getId());
        LOGGER.debug("Created verification token with id {}", verificationToken.getId());
        sendVerificationToken(userModel, verificationToken);
    }

    @Override
    public boolean validatePasswordReset(String token) {
        Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenDao.getTokenByValue(token);

        if (!passwordResetTokenOptional.isPresent() || !passwordResetTokenOptional.get().isValid()) {
            LOGGER.info("Token {} is not valid", token);
            return false;
        }

        LOGGER.info("Token {} is valid", token);
        return true;
    }

    @Transactional
    @Override
    public void generateNewPassword(UserModel userModel) {
        LOGGER.debug("Removing password reset token for user {}", userModel.getId());
        passwordResetTokenDao.removeTokenByUserId(userModel.getId());
        PasswordResetToken passwordResetToken = generatePasswordResetToken(userModel.getId());
        LOGGER.info("Created password reset token for user {}", userModel.getId());
        sendPasswordResetToken(userModel, passwordResetToken);
    }

    @Transactional
    @Override
    public Optional<UserModel> updatePassword(String token, String password) {
        Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenDao.getTokenByValue(token);

        if (!passwordResetTokenOptional.isPresent()) {
            LOGGER.warn("Password reset token {} is not valid", token);
            return Optional.empty();
        }

        final PasswordResetToken passwordResetToken = passwordResetTokenOptional.get();
        //Eliminamos el token siempre, ya sea valido o no
        passwordResetTokenDao.removeTokenById(passwordResetToken.getId());
        LOGGER.debug("Removed password reset token with id {}", passwordResetToken.getId());

        if (!passwordResetToken.isValid()) {
            LOGGER.warn("Password reset token {} has expired", token);
            return Optional.empty();
        }

        LOGGER.debug("Updating password for user {}", passwordResetToken.getUserId());
        return userDao.updatePassword(passwordResetToken.getUserId(), passwordEncoder.encode(password));
    }

    @Override
    public void updateUserInfo(long userId, UserInfo userInfo) {
        LOGGER.debug("Updating user info for user {}", userId);
        userDao.updateUserInfo(userId, userInfo);
    }

    @Override
    public void updateProfileImage(UserModel userModel, byte[] image) {
        LOGGER.debug("Updating user {} profile image of id {}", userModel.getId(), userModel.getProfileImageId());
        imageService.updateImg(image, userModel.getProfileImageId());
    }

    private void sendVerificationToken(UserModel userModel, VerificationToken token) {
        try {
            String url = new URL("http", appBaseUrl, 8080, "/webapp_war/user/verifyAccount/" + token.getValue()).toString();
//            String url = new URL("http", appBaseUrl, "/paw-2022b-1/user/verifyAccount/" + token.getValue()).toString();
            Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("confirmationURL", url);
            mailAttrs.put("to", userModel.getEmail());
            emailService.sendMail("verification", messageSource.getMessage("email.verifyAccount", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, user verification mail not sent");
        }
    }

    private void sendPasswordResetToken(UserModel userModel, PasswordResetToken token) {
        try {
            String url = new URL("http", appBaseUrl, 8080, "/webapp_war/user/resetPassword/" + token.getValue()).toString();
//            String url = new URL("http", appBaseUrl, "/paw-2022b-1/user/resetPassword/" + token.getValue()).toString();
            Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("confirmationURL", url);
            mailAttrs.put("to", userModel.getEmail());
            emailService.sendMail("passwordReset", messageSource.getMessage("email.resetPassword", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, user password reset mail not sent");
        }
    }

    private VerificationToken generateVerificationToken(long userId) {
        String token = UUID.randomUUID().toString();
        return verificationTokenDao.createVerificationToken(userId, token, VerificationToken.generateTokenExpirationDate());
    }

    private PasswordResetToken generatePasswordResetToken(long userId) {
        String token = UUID.randomUUID().toString();
        return passwordResetTokenDao.createToken(userId, token, PasswordResetToken.generateTokenExpirationDate());
    }

}
