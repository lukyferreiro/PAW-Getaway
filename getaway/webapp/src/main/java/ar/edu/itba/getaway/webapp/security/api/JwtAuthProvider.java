package ar.edu.itba.getaway.webapp.security.api;

import ar.edu.itba.getaway.webapp.security.models.AuthToken;
import ar.edu.itba.getaway.webapp.security.models.JwtAuthToken;
import ar.edu.itba.getaway.webapp.security.services.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthProvider implements AuthenticationProvider {

    @Autowired
    private AuthTokenService authenticationTokenService;

    //TODO aca creo que deberia ser MyUserDetailsService
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String token = (String) authentication.getCredentials();
        final AuthToken authToken = authenticationTokenService.parseToken(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authToken.getEmail());
        return new JwtAuthToken(userDetails, authToken, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthToken.class.isAssignableFrom(authentication));
    }
}
