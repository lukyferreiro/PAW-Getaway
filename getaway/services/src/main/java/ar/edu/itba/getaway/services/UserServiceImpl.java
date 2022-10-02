package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.persistence.PasswordResetTokenDao;
import ar.edu.itba.getaway.persistence.UserDao;
import ar.edu.itba.getaway.persistence.VerificationTokenDao;
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

    @Override
    public Optional<UserModel> getUserByExperienceId(Long experienceId){
        LOGGER.debug("Retrieving user who creates experience with id {}", experienceId);
        return userDao.getUserByExperienceId(experienceId);
    }

    @Override
    public Optional<UserModel> getUserByReviewId(Long reviewId){
        LOGGER.debug("Retrieving user who creates review with id {}", reviewId);
        return userDao.getUserByReviewId(reviewId);
    }

    @Transactional
    @Override
    public UserModel createUser(String password, String name, String surname, String email) throws DuplicateUserException {
        final ImageModel imageModel = imageService.createImg(null);
        LOGGER.debug("Creating user with email {}", email);
        UserModel userModel = userDao.createUser(passwordEncoder.encode(password), name, surname, email, DEFAULT_ROLES, imageModel.getId());
        LOGGER.debug("Creating verification token to user with id {}", userModel.getId());
        VerificationToken token = tokensService.generateVerificationToken(userModel.getId());
        LOGGER.debug("Created verification token with id {}", token.getId());
        tokensService.sendVerificationToken(userModel, token);
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
        VerificationToken verificationToken = tokensService.generateVerificationToken(userModel.getId());
        LOGGER.debug("Created verification token with id {}", verificationToken.getId());
        tokensService.sendVerificationToken(userModel, verificationToken);
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
        PasswordResetToken passwordResetToken = tokensService.generatePasswordResetToken(userModel.getId());
        LOGGER.info("Created password reset token for user {}", userModel.getId());
        tokensService.sendPasswordResetToken(userModel, passwordResetToken);
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

    @Override
    public void addRole(long userId, Roles newRole){
        LOGGER.debug("Adding role {} to user with id {}", newRole.name(), userId);
        userDao.addRole(userId, newRole);
    }

}
