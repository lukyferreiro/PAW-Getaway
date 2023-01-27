package ar.edu.itba.getaway.webapp.security.api;

import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.security.exceptions.InvalidUsernamePasswordException;
import ar.edu.itba.getaway.webapp.security.models.AuthTokenDetails;
import ar.edu.itba.getaway.webapp.security.models.BasicAuthToken;
import ar.edu.itba.getaway.webapp.security.services.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BasicAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthTokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final BasicAuthToken auth = (BasicAuthToken) authentication;
        final String[] credentials;
        try {
            credentials = new String(Base64.getDecoder().decode(auth.getToken())).split(":");
        } catch (IllegalArgumentException iae) {
            throw new BadCredentialsException("Invalid basic header");
        }
        if (credentials.length != 2) {
            throw new InvalidUsernamePasswordException("Invalid username/password");
        }
        final UserModel maybeUser = userService.getUserByEmail(credentials[0]).orElseThrow(() -> new BadCredentialsException("Bad credentials"));
        if (!passwordEncoder.matches(credentials[1], maybeUser.getPassword())) {
            throw new BadCredentialsException("Bad username/password combination");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(credentials[0]);
        final String authenticationToken = tokenService.issueToken(credentials[0], mapToAuthority(userDetails.getAuthorities()));
        final AuthTokenDetails tokenDetails = tokenService.parseToken(authenticationToken);
        final BasicAuthToken trustedAuth = new BasicAuthToken(credentials[0], credentials[1], userDetails.getAuthorities(), tokenDetails);
        trustedAuth.setToken(authenticationToken);
        return trustedAuth;
    }

    private Set<Roles> mapToAuthority(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(grantedAuthority -> Roles.valueOf(grantedAuthority.toString())).collect(Collectors.toSet());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (BasicAuthToken.class.isAssignableFrom(authentication));
    }
}
