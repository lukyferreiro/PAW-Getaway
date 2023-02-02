package ar.edu.itba.getaway.webapp.security.services;

import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.controller.apiControllers.ExperienceController;
import ar.edu.itba.getaway.webapp.security.models.BasicAuthToken;
import ar.edu.itba.getaway.webapp.security.models.JwtAuthToken;
import ar.edu.itba.getaway.webapp.security.models.MyUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacadeImpl implements AuthFacade {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFacadeImpl.class);


    @Override
    public UserModel getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof BasicAuthToken) {
            LOGGER.info("Instance of basic auth");
            final String username = (String) authentication.getPrincipal();
            return userService.getUserByEmail(username).orElseThrow(UserNotFoundException::new);
        }
        else if (authentication instanceof JwtAuthToken) {
            LOGGER.info("Instance of jwt token auth");

            return userService.getUserByEmail(((MyUserDetails)(authentication.getPrincipal())).getUsername()).orElseThrow(UserNotFoundException::new);
        }
        else /*Case: anonymous*/ {
            LOGGER.info("Instance of anonymous user");
            return null;
        }
    }

    @Override
    public Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    @Override
    public boolean isVerifiedUser() {
        return getCurrentUser().isVerified();
    }

    @Override
    public boolean isProviderUser() {
        return getCurrentUser().isProvider();
    }


}
