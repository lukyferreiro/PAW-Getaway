package ar.edu.itba.getaway.webapp.security.api.handlers;

import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.security.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication instanceof BasicAuthToken) {
            response.addHeader("Authorization", "Bearer " + ((BasicAuthToken) authentication).getToken());

            // Reenviar mail de verificación automáticamente al loguear un usuario no verificado
            final String username = ((BasicAuthToken) authentication).getPrincipal();
            final UserModel user = userService.getUserByEmail(username).orElseThrow(UserNotFoundException::new);
            if (!user.isVerified()) {
                userService.resendVerificationToken(user);
            }
        }
    }
}
