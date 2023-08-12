package ar.edu.itba.getaway.webapp.security.api;

import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.ReviewNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.security.exceptions.InvalidRequestParamsException;
import ar.edu.itba.getaway.webapp.security.models.BasicAuthToken;
import ar.edu.itba.getaway.webapp.security.models.JwtAuthToken;
import ar.edu.itba.getaway.webapp.security.models.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AntMatcherVoter {
    @Autowired
    private UserService userService;

    private UserModel getUser(Authentication authentication) {
        if(authentication instanceof BasicAuthToken) {
            final String username = (String) authentication.getPrincipal();
            return userService.getUserByEmail(username).orElseThrow(UserNotFoundException::new);
        }
        else if (authentication instanceof JwtAuthToken) {
            return userService.getUserByEmail(((MyUserDetails)(authentication.getPrincipal())).getUsername()).orElseThrow(UserNotFoundException::new);
        }
        else /*Case: anonymous*/ {
            return null;
        }
    }

    public boolean canEditExperienceById(Authentication authentication, long experienceId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userModel = userService.getUserByExperienceId(experienceId);
        final UserModel user = getUser(authentication);

        if(userModel.isPresent()) {
            return userModel.get().equals(user);
        }

        throw new ExperienceNotFoundException();

    }

    public boolean canDeleteExperienceById(Authentication authentication, long experienceId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userModel = userService.getUserByExperienceId(experienceId);
        final UserModel user = getUser(authentication);

        if(userModel.isPresent()) {
            return userModel.get().equals(user);
        }

        throw new ExperienceNotFoundException();

    }

    public boolean canEditReviewById(Authentication authentication, long reviewId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userModel = userService.getUserByReviewId(reviewId);
        final UserModel user = getUser(authentication);

        if(userModel.isPresent()) {
            return userModel.get().equals(user);
        }

        throw new ReviewNotFoundException();

    }

    public boolean canDeleteReviewById(Authentication authentication, long reviewId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userModel = userService.getUserByReviewId(reviewId);
        final UserModel user = getUser(authentication);

        if(userModel.isPresent()) {
            return userModel.get().equals(user);
        }

        throw new ReviewNotFoundException();
    }

    public boolean userEditHimself(Authentication authentication, long userId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;

        final Optional<UserModel> userToEdit = userService.getUserById(userId); //Usuario que quiero editar
        final UserModel user = getUser(authentication); //Usuario autenticado

        if (user.isVerified()) {
            if(userToEdit.isPresent()) {
                return userToEdit.get().equals(user);
            }
        }
        return false;
    }

    public boolean accessUserInfo(Authentication authentication, long userId) {
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        final Optional<UserModel> userToAccess = userService.getUserById(userId); //Usuario que quiero acceder
        final UserModel user = getUser(authentication);   //Usuario autenticado
        return userToAccess.map(userModel -> userModel.equals(user)).orElse(false);
    }

    public boolean checkGetReviews(Authentication authentication, Long userId, Long experienceId){
        if(userId != null && experienceId != null){
            throw new InvalidRequestParamsException("errors.InvalidParam.getReviews");
        }
        if(userId != null && experienceId == null){
            accessUserInfo(authentication,userId);
        }
        return true;
    }

}
