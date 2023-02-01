package ar.edu.itba.getaway.webapp.security.api;

import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.ReviewNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.security.models.BasicAuthToken;
import ar.edu.itba.getaway.webapp.security.models.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.ws.rs.ForbiddenException;
import java.util.Optional;

@Component
public class AntMatcherVoter {
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;

    private UserModel getUser(Authentication authentication) {
        if(authentication instanceof BasicAuthToken) {
            return userService.getUserByEmail(((BasicAuthToken)authentication).getPrincipal()).orElseThrow(UserNotFoundException::new);
        }
        return ((MyUserDetails)(authentication.getPrincipal())).toUserModel();
    }

    public boolean canEditExperienceById(Authentication authentication, long experienceId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userModel = userService.getUserByExperienceId(experienceId);
        UserModel user = getUser(authentication);

        if(userModel.isPresent()) {
            return userModel.get().equals(user);
        }

        throw new ExperienceNotFoundException();

    }

    public boolean canDeleteExperienceById(Authentication authentication, long experienceId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userModel = userService.getUserByExperienceId(experienceId);
        UserModel user = getUser(authentication);

        if(userModel.isPresent()) {
            return userModel.get().equals(user);
        }

        throw new ExperienceNotFoundException();

    }

    public boolean canEditReviewById(Authentication authentication, long reviewId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userModel = userService.getUserByReviewId(reviewId);
        UserModel user = getUser(authentication);

        if(userModel.isPresent()) {
            return userModel.get().equals(user);
        }

        throw new ReviewNotFoundException();

    }

    public boolean canDeleteReviewById(Authentication authentication, long reviewId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userModel = userService.getUserByReviewId(reviewId);
        UserModel user = getUser(authentication);

        if(userModel.isPresent()) {
            return userModel.get().equals(user);
        }

        throw new ReviewNotFoundException();
    }

    public boolean userEditHimself(Authentication authentication, long userId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        //Usuario que quiero editar
        final Optional<UserModel> userToEdit = userService.getUserById(userId);

        UserModel user = getUser(authentication);

        if (user.isVerified()) {
            if(userToEdit.isPresent()) {
                return userToEdit.get().equals(user);
            }
        }
        return false;
    }
}
