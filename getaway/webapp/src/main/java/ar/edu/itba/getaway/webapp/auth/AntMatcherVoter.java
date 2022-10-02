package ar.edu.itba.getaway.webapp.auth;

import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AntMatcherVoter {

    @Autowired
    private UserService userService;

    public boolean canEditExperienceById(Authentication authentication , Long experienceId){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userModel = userService.getUserByExperienceId(experienceId);
        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }

    public boolean canDeleteExperienceById(Authentication authentication , Long experienceId){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userModel = userService.getUserByExperienceId(experienceId);
        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }

    public boolean canEditReviewById(Authentication authentication , Long reviewId){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userModel = userService.getUserByReviewId(reviewId);
        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }

    public boolean canDeleteReviewById(Authentication authentication , Long reviewId){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userModel = userService.getUserByReviewId(reviewId);
        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }
}
