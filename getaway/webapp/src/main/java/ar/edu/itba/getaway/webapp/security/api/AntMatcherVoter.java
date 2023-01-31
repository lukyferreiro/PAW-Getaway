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
        final ExperienceModel experienceModel = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final UserModel userModel = experienceModel.getUser();
        UserModel user = getUser(authentication);
        System.out.println(user.getEmail());
        return userModel.equals(user);
    }

    public boolean canDeleteExperienceById(Authentication authentication, long experienceId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final ExperienceModel experienceModel = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        final UserModel userModel = experienceModel.getUser();
        //        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
        UserModel user = getUser(authentication);
        System.out.println(user.getEmail());
        return userModel.equals(user);
    }

    public boolean canEditReviewById(Authentication authentication, long reviewId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final ReviewModel reviewModel = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);
        final UserModel userModel = reviewModel.getUser();

        UserModel user = getUser(authentication);
        System.out.println(user.getEmail());

//        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
        return userModel.equals(user);
    }

    public boolean canDeleteReviewById(Authentication authentication, long reviewId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final ReviewModel reviewModel = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);
        final UserModel userModel = reviewModel.getUser();
//        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);

        UserModel user = getUser(authentication);
        System.out.println(user.getEmail());
        return userModel.equals(user);
    }

    public boolean userEditHimself(Authentication authentication, long userId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        //Usuario que quiero editar
        final Optional<UserModel> userToEdit = userService.getUserById(userId);

        UserModel user = getUser(authentication);
        System.out.println(user.getEmail());

        if (user.isVerified()) {
            if(userToEdit.isPresent()) {
                return userToEdit.get().equals(user);
            }
        }
        return false;
//        return userModel.map(model -> model.getEmail().equals(authentication.getName())).orElse(false);
    }
}
