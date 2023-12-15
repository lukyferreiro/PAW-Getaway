package ar.edu.itba.getaway.webapp.security.api;

import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.interfaces.services.TokensService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.webapp.security.exceptions.InvalidUsernamePasswordException;
import ar.edu.itba.getaway.webapp.security.models.BasicAuthToken;
import ar.edu.itba.getaway.webapp.security.models.JwtTokenDetails;
import ar.edu.itba.getaway.webapp.security.models.MyUserDetails;
import ar.edu.itba.getaway.webapp.security.services.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BasicAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthTokenService authTokenService;
    private final TokensService tokensService;

    @Autowired
    public BasicAuthProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserService userService, AuthTokenService authTokenService, TokensService tokensService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authTokenService = authTokenService;
        this.tokensService = tokensService;
    }

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
        final UserModel user = userService.getUserByEmail(credentials[0]).orElseThrow(() -> new BadCredentialsException("Bad credentials"));

        //Si me autentico con Basic email:verifyToken, verifico
        if (!tokensService.validateVerificationToken(user, credentials[1])) {
            userService.verifyAccount(credentials[1]).orElseThrow(UserNotFoundException::new);
        } else if (!passwordEncoder.matches(credentials[1], user.getPassword())) {
            throw new BadCredentialsException("Bad username/password combination");
        }

        final MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(credentials[0]);
        //TODO ver aca que token enviar
        final String authToken = authTokenService.createRefreshToken(userDetails);
        final JwtTokenDetails tokenDetails = authTokenService.validateTokenAndGetDetails(authToken);
        final BasicAuthToken trustedAuth = new BasicAuthToken(credentials[0], credentials[1], userDetails.getAuthorities(), tokenDetails);
        trustedAuth.setToken(authToken);
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
