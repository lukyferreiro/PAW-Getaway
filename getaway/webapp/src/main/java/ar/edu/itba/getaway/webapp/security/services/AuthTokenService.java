package ar.edu.itba.getaway.webapp.security.services;

import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.webapp.security.models.AuthTokenDetails;

import java.util.Set;

public interface AuthTokenService {
    String issueToken(String username, Set<Roles> authorities);
    AuthTokenDetails parseToken(String token);
    String refreshToken(AuthTokenDetails currentAuthenticationTokenDetails);
}

