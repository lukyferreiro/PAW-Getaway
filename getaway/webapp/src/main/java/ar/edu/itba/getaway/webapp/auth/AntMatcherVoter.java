package ar.edu.itba.getaway.webapp.auth;

import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.ReviewNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.interfaces.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
public class AntMatcherVoter {
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    //TODO: Check with @LukyFerreiro
    public boolean canEditExperienceById(Authentication authentication , Long experienceId){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final ExperienceModel experienceModel = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final Optional<UserModel> userModel = Optional.of(experienceModel.getUser());
        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }

    public boolean canDeleteExperienceById(Authentication authentication , Long experienceId){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final ExperienceModel experienceModel = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final Optional<UserModel> userModel = Optional.of(experienceModel.getUser());
        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }

    public boolean canEditReviewById(Authentication authentication , Long reviewId){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final ReviewModel reviewModel = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);
        final Optional<UserModel> userModel = Optional.of(reviewModel.getUser());
        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }

    public boolean canDeleteReviewById(Authentication authentication , Long reviewId){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final ReviewModel reviewModel = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);
        final Optional<UserModel> userModel = Optional.of(reviewModel.getUser());
        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }
}
