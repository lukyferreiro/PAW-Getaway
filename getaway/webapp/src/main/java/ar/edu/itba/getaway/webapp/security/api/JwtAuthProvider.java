package ar.edu.itba.getaway.webapp.security.api;

import ar.edu.itba.getaway.webapp.security.models.AuthTokenDetails;
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

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String token = (String) authentication.getCredentials();
        final AuthTokenDetails authTokenDetails = authenticationTokenService.parseToken(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authTokenDetails.getUsername());
        return new JwtAuthToken(userDetails, authTokenDetails, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthToken.class.isAssignableFrom(authentication));
    }
}
