package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.persistence.PasswordResetTokenDao;
import ar.edu.itba.getaway.interfaces.persistence.UserDao;
import ar.edu.itba.getaway.interfaces.persistence.VerificationTokenDao;
import ar.edu.itba.getaway.interfaces.services.ImageService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ImageService imageService;
    @Autowired
    private TokensServiceImpl tokensService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final Collection<Roles> DEFAULT_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

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

    @Override
    public Optional<UserModel> getUserByExperienceId(long experienceId) {
        LOGGER.debug("Retrieving user who created experience with id {}", experienceId);
        return userDao.getUserByExperienceId(experienceId);
    }

    @Override
    public Optional<UserModel> getUserByReviewId(long reviewId) {
        LOGGER.debug("Retrieving user who created review with id {}", reviewId);
        return userDao.getUserByReviewId(reviewId);
    }

    @Transactional
    @Override
    public UserModel createUser(String password, String name, String surname, String email) throws DuplicateUserException {
        final ImageModel imageModel = imageService.createImg(null, null);
        LOGGER.debug("Creating user with email {}", email);
        final UserModel userModel = userDao.createUser(passwordEncoder.encode(password), name, surname, email, DEFAULT_ROLES, imageModel);
        LOGGER.debug("Creating verification token to user with id {}", userModel.getUserId());
//        final VerificationToken token = tokensService.generateVerificationToken(userModel);
//        tokensService.sendVerificationToken(userModel, token);
        return userModel;
    }

    @Transactional
    @Override
    public Optional<UserModel> verifyAccount(String token) {
        final Optional<VerificationToken> verificationTokenOptional = verificationTokenDao.getTokenByValue(token);

        if (!verificationTokenOptional.isPresent()) {
            LOGGER.warn("No verification token with value {}", token);
            return Optional.empty();
        }

        final VerificationToken verificationToken = verificationTokenOptional.get();
        verificationTokenDao.removeToken(verificationToken);
        LOGGER.debug("Removed verification token with id {}", verificationToken.getId());
       
        if (!verificationToken.isValid()) {
            LOGGER.warn("Verification token with value {} is invalid (expired)", token);
            return Optional.empty();
        }

        LOGGER.debug("Validating user with id {}", verificationToken.getUser().getUserId());
        return userDao.updateRoles(verificationToken.getUser(), Roles.NOT_VERIFIED, Roles.VERIFIED);
    }

    @Transactional
    @Override
    public void resendVerificationToken(UserModel userModel) {
        LOGGER.debug("Removing verification token for user with id {}", userModel.getUserId());
        final Optional<VerificationToken> verificationTokenOptional = verificationTokenDao.getTokenByUser(userModel);
        verificationTokenOptional.ifPresent(verificationToken -> verificationTokenDao.removeToken(verificationToken));
        final VerificationToken verificationToken = tokensService.generateVerificationToken(userModel);
        tokensService.sendVerificationToken(userModel, verificationToken);
    }

    @Override
    public boolean validatePasswordReset(String token) {
        final Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenDao.getTokenByValue(token);

        if (!passwordResetTokenOptional.isPresent() || !passwordResetTokenOptional.get().isValid()) {
            LOGGER.info("Token {} is not valid (expired)", token);
            return false;
        }

        LOGGER.info("Token {} is valid", token);
        return true;
    }

    @Transactional
    @Override
    public void generateNewPassword(UserModel userModel) {
        LOGGER.debug("Removing password reset token for user {}", userModel.getUserId());
        final Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenDao.getTokenByUser(userModel);
        passwordResetTokenOptional.ifPresent(passwordResetToken -> passwordResetTokenDao.removeToken(passwordResetToken));
        final PasswordResetToken passwordResetToken = tokensService.generatePasswordResetToken(userModel);
        tokensService.sendPasswordResetToken(userModel, passwordResetToken);
    }

    @Transactional
    @Override
    public boolean updateUser(long userId, UserModel user) {
        return userDao.updateUser(userId, user);
    }

    @Transactional
    @Override
    public Optional<UserModel> updatePassword(String token, String password) {
        final Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenDao.getTokenByValue(token);

        if (!passwordResetTokenOptional.isPresent()) {
            LOGGER.warn("Password reset token {} is not valid", token);
            return Optional.empty();
        }

        final PasswordResetToken passwordResetToken = passwordResetTokenOptional.get();
        passwordResetTokenDao.removeToken(passwordResetToken);
        LOGGER.debug("Removed password reset token with id {}", passwordResetToken.getId());

        if (!passwordResetToken.isValid()) {
            LOGGER.warn("Password reset token {} has expired", token);
            return Optional.empty();
        }

        LOGGER.debug("Updating password for user {}", passwordResetToken.getUser().getUserId());
        return userDao.updatePassword(passwordResetToken.getUser(), passwordEncoder.encode(password));
    }

    @Transactional
    @Override
    public void updateUserInfo(UserModel user, UserInfo userInfo) {
        LOGGER.debug("Updating user info for user {}", user.getUserId());
        userDao.updateUserInfo(user, userInfo);
    }

    @Override
    public void addRole(UserModel user, Roles newRole) {
        LOGGER.debug("Adding role {} to user with id {}", newRole.name(), user.getUserId());
        userDao.addRole(user, newRole);
    }

    @Override
    public Collection<Roles> getRolesByUser(UserModel user) {
        final List<Roles> userRoles = new ArrayList<>();
        final Collection<RoleModel> userRoleModels = user.getRoles();
        for (RoleModel role : userRoleModels) {
            userRoles.add(role.getRoleName());
        }
        return userRoles;
    }
}
