package ar.edu.itba.getaway.webapp.security.api.handlers;

import ar.edu.itba.getaway.webapp.security.models.BasicAuthToken;
import ar.edu.itba.getaway.webapp.security.models.JwtAuthToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if(authentication instanceof BasicAuthToken) {
            response.addHeader("Authorization", "Bearer " + ((BasicAuthToken) authentication).getToken());
        }
        //TODO check
//        if(authentication instanceof JwtAuthToken){
//            response.addHeader("Authorization", "Bearer " + ((JwtAuthToken) authentication).getToken());
//        }
    }
}
