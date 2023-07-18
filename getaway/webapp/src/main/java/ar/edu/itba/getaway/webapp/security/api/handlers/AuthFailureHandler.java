package ar.edu.itba.getaway.webapp.security.api.handlers;

import ar.edu.itba.getaway.webapp.security.api.AuthFailureMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        AuthFailureMapper.handleFailure(request, response, exception, HttpStatus.UNAUTHORIZED);
    }
}
