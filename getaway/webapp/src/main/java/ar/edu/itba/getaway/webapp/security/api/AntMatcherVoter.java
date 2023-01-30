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
    private Long getUserId(Authentication authentication) {
        return getUser(authentication).getUserId();
    }
    private boolean isVerified(Authentication authentication) {
        return getUser(authentication).isVerified();
    }
    private boolean isProvider(Authentication authentication) {
        return getUser(authentication).isProvider();
    }

    public boolean canEditExperienceById(Authentication authentication, long experienceId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final ExperienceModel experienceModel = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final Optional<UserModel> userModel = Optional.of(experienceModel.getUser());
        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }

    public boolean canDeleteExperienceById(Authentication authentication, long experienceId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final ExperienceModel experienceModel = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final Optional<UserModel> userModel = Optional.of(experienceModel.getUser());
        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }

    public boolean canEditReviewById(Authentication authentication, long reviewId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final ReviewModel reviewModel = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);
        final Optional<UserModel> userModel = Optional.of(reviewModel.getUser());
        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }

    public boolean canDeleteReviewById(Authentication authentication, long reviewId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final ReviewModel reviewModel = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);
        final Optional<UserModel> userModel = Optional.of(reviewModel.getUser());
        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }

    public boolean userEditHimself(Authentication authentication, long userId) {
        //TODO
        return false;
    }
}
