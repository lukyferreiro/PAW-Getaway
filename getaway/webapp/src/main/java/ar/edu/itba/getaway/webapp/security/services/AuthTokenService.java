package ar.edu.itba.getaway.webapp.security.services;

import ar.edu.itba.getaway.webapp.security.models.JwtTokenDetails;
import ar.edu.itba.getaway.webapp.security.models.MyUserDetails;

public interface AuthTokenService {
    String createAccessToken(final MyUserDetails userDetails);
    String createRefreshToken(final MyUserDetails userDetails);
    JwtTokenDetails validateTokenAndGetDetails(final String token);
}

