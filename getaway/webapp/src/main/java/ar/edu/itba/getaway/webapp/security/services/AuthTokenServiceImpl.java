package ar.edu.itba.getaway.webapp.security.services;

import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.webapp.security.exceptions.ExpiredAuthTokenException;
import ar.edu.itba.getaway.webapp.security.exceptions.InvalidAuthTokenException;
import ar.edu.itba.getaway.webapp.security.models.JwtTokenDetails;
import ar.edu.itba.getaway.webapp.security.models.JwtTokenType;
import ar.edu.itba.getaway.webapp.security.models.MyUserDetails;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthTokenServiceImpl implements AuthTokenService {

    private final Settings settings;
    @Autowired
    private URL appBaseUrl;

    @Autowired
    public AuthTokenServiceImpl(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String createAccessToken(final MyUserDetails userDetails) {
        return createToken(new Date(System.currentTimeMillis() + settings.getValidForAccess()), userDetails, JwtTokenType.ACCESS);
    }

    @Override
    public String createRefreshToken(final MyUserDetails userDetails) {
        return createToken(new Date(System.currentTimeMillis() + settings.getValidForRefresh()), userDetails, JwtTokenType.REFRESH);
    }

    private String createToken(final Date expiresAt, final MyUserDetails userDetails, final JwtTokenType tokenType) {
        final JWTCreator.Builder token =  JWT.create()
                .withJWTId(generateTokenIdentifier())
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(expiresAt)
                .withIssuer(settings.getIssuer())
                .withAudience(settings.getAudience())
                .withClaim(settings.getAuthoritiesClaim(), mapToAuthority(userDetails.getAuthorities()))
                .withClaim(settings.getTokenTypeClaim(), tokenType.getType())
                .withClaim(settings.getIsVerifiedClaim(), userDetails.isVerified())
                .withClaim(settings.getIsProviderClaim(), userDetails.isProvider())
                .withClaim(settings.getUserIdClaim(), userDetails.getUserId())
                .withClaim(settings.getNameClaim(), userDetails.getName())
                .withClaim(settings.getSurnameClaim(), userDetails.getSurname())
                .withClaim(settings.getHasImageClaim(), userDetails.hasImage());

        if (userDetails.hasImage()) {
            token.withClaim(settings.getProfileImageUrlClaim(), appBaseUrl.toString() + "api/users/" + userDetails.getUserId() + "/profileImage");
        }

        return token.sign(Algorithm.HMAC256(settings.getSecret().getBytes()));
    }

    @Override
    public JwtTokenDetails validateTokenAndGetDetails(String token) {
        try {
            final DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(settings.getSecret().getBytes()))
                    .withIssuer(settings.getIssuer())
                    .withAudience(settings.getAudience())
                    .build().verify(token);

            return new JwtTokenDetails.Builder()
                    .withId(decodedJWT.getId())
                    .withEmail(decodedJWT.getSubject())
                    .withAuthorities(decodedJWT.getClaim(settings.getAuthoritiesClaim()).asList(String.class))
                    .withIssuedDate(decodedJWT.getIssuedAt())
                    .withExpirationDate(decodedJWT.getExpiresAt())
                    .withToken(token)
                    .withTokenType(JwtTokenType.getByType(decodedJWT.getClaim(settings.getTokenTypeClaim()).asString()))
                    .build();

        } catch (TokenExpiredException e) {
            throw new ExpiredAuthTokenException("The access token expired", e);
        } catch (InvalidClaimException e) {
            throw new InvalidAuthTokenException("Invalid value for claim \"" + e.getMessage() + "\"", e);
        } catch (Exception e) {
            throw new InvalidAuthTokenException("Invalid token", e);
        }
    }

    private List<String> mapToAuthority(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(grantedAuthority -> Roles.valueOf(grantedAuthority.toString()).name())
                .collect(Collectors.toList());
    }

    private String generateTokenIdentifier() {
        return UUID.randomUUID().toString();
    }
}

