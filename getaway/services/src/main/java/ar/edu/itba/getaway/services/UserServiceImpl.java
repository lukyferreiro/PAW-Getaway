package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.persistence.PasswordResetTokenDao;
import ar.edu.itba.getaway.persistence.UserDao;
import ar.edu.itba.getaway.persistence.VerificationTokenDao;
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

    private final Locale locale = LocaleContextHolder.getLocale();
    private final Collection<Roles> DEFAULT_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    @Override
    public Optional<UserModel> getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Transactional
    @Override
    public UserModel createUser(String password, String name, String surname, String email) throws DuplicateUserException {
        UserModel userModel = userDao.createUser(passwordEncoder.encode(password), name, surname, email, DEFAULT_ROLES);
        VerificationToken token = generateVerificationToken(userModel.getId());
        sendVerificationToken(userModel, token);
        return userModel;
    }

    @Transactional
    @Override
    public Optional<UserModel> verifyAccount(String token) {
        Optional<VerificationToken> vtokenOpt = verificationTokenDao.getTokenByValue(token);

        if (!vtokenOpt.isPresent()) {
            return Optional.empty();
        }

        VerificationToken vtoken = vtokenOpt.get();
        verificationTokenDao.removeTokenById(vtoken.getId());//remove always, either token is valid or not

        if (!vtoken.isValid()) {
            return Optional.empty();
        }

        return userDao.updateRoles(vtoken.getUserId(), Roles.NOT_VERIFIED, Roles.VERIFIED);
    }

    @Transactional
    @Override
    public void resendVerificationToken(UserModel userModel) {
        verificationTokenDao.removeTokenByUserId(userModel.getId());
        VerificationToken token = generateVerificationToken(userModel.getId());
        sendVerificationToken(userModel, token);
    }

    @Override
    public boolean validatePasswordReset(String token) {
        Optional<PasswordResetToken> prtokenOpt = passwordResetTokenDao.getTokenByValue(token);
        return prtokenOpt.isPresent() && prtokenOpt.get().isValid();
    }

    @Transactional
    @Override
    public void generateNewPassword(UserModel userModel) {
        passwordResetTokenDao.removeTokenByUserId(userModel.getId());
        PasswordResetToken token = generatePasswordResetToken(userModel.getId());
        sendPasswordResetToken(userModel, token);
    }

    @Transactional
    @Override
    public Optional<UserModel> updatePassword(String token, String password) {
        Optional<PasswordResetToken> prtokenOpt = passwordResetTokenDao.getTokenByValue(token);

        if (!prtokenOpt.isPresent()) {
            return Optional.empty();
        }

        PasswordResetToken prtoken = prtokenOpt.get();
        passwordResetTokenDao.removeTokenById(prtoken.getId()); //remove always, either token is valid or not
        if (!prtoken.isValid()) {
            return Optional.empty();
        }

        return userDao.updatePassword(prtoken.getUserId(), passwordEncoder.encode(password));
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, UserModel userModel) {
        userDao.updateUserInfo(userInfo, userModel);
    }

    @Override
    public void updateProfileImage(ImageModel imageDto, UserModel userModel) {
        Long imageId = userModel.getProfileImageId();
        if (imageId == 0) {
            imageId = imageService.create(imageDto.getImage()).getId();
            userDao.updateProfileImage(imageId, userModel);
        } else {
            imageService.update(imageId, imageDto);
        }
    }

    private void sendVerificationToken(UserModel userModel, VerificationToken token) {
        try {
            String url = new URL("http", appBaseUrl, "/user/resetPassword?token=" + token.getValue()).toString();
//            String url = new URL("http", appBaseUrl, "/paw-2022b-1/user/verifyAccount?token=" + token.getValue()).toString();
            Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("confirmationURL", url);
            mailAttrs.put("to", userModel.getEmail());
            emailService.sendMail("verification", messageSource.getMessage("email.verifyAccount", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void sendPasswordResetToken(UserModel userModel, PasswordResetToken token) {
        try {
            String url = new URL("http", appBaseUrl, "/user/resetPassword?token=" + token.getValue()).toString();
//            String url = new URL("http", appBaseUrl, "/paw-2022b-1/user/resetPassword?token=" + token.getValue()).toString();
            Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("confirmationURL", url);
            mailAttrs.put("to", userModel.getEmail());
            emailService.sendMail("passwordReset", messageSource.getMessage("email.resetPassword", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private VerificationToken generateVerificationToken(long userId) {
        String token = UUID.randomUUID().toString();
        return verificationTokenDao.createVerificationToken(userId, token, VerificationToken.generateTokenExpirationDate());
    }

    private PasswordResetToken generatePasswordResetToken(long userId) {
        String token = UUID.randomUUID().toString();
        return passwordResetTokenDao.createToken(userId, token, VerificationToken.generateTokenExpirationDate());
    }
}
