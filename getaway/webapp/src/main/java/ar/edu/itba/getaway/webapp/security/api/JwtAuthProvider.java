package ar.edu.itba.getaway.webapp.security.api;

import ar.edu.itba.getaway.webapp.security.models.JwtTokenDetails;
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

    private final AuthTokenService authenticationTokenService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthProvider(UserDetailsService userDetailsService, AuthTokenService authenticationTokenService) {
        this.userDetailsService = userDetailsService;
        this.authenticationTokenService = authenticationTokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String token = (String) authentication.getCredentials();
        final JwtTokenDetails jwtTokenDetails = authenticationTokenService.validateTokenAndGetDetails(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenDetails.getEmail());
        return new JwtAuthToken(userDetails, jwtTokenDetails, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthToken.class.isAssignableFrom(authentication));
    }
}
