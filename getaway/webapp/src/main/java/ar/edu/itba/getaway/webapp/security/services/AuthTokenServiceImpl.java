package ar.edu.itba.getaway.webapp.security.services;

import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.webapp.security.exceptions.ExpiredAuthTokenException;
import ar.edu.itba.getaway.webapp.security.exceptions.InvalidAuthTokenException;
import ar.edu.itba.getaway.webapp.security.models.AuthTokenDetails;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AuthTokenServiceImpl implements AuthTokenService {

    @Value("${authentication.jwt.validFor}")
    private Long validFor;

    @Autowired
    Settings settings;
//    @Autowired
//    TokenIssuer tokenIssuer;
//
//    @Autowired
//    TokenParser tokenParser;

    @Override
    public String issueToken(String username, Set<Roles> roles) {
        final String id = UUID.randomUUID().toString();
        final ZonedDateTime issuedDate = ZonedDateTime.now();
        final ZonedDateTime expirationDate = issuedDate.plusSeconds(validFor);
        final AuthTokenDetails authTokenDetails = new AuthTokenDetails(id, username, roles, issuedDate, expirationDate);
//        return tokenIssuer.issueToken(authTokenDetails);
        return issueToken(authTokenDetails);
    }

//    @Override
//    public AuthTokenDetails parseToken(String token) {
//        return tokenParser.parseToken(token);
//    }

    @Override
    public String refreshToken(AuthTokenDetails currentAuthTokenDetails) {
        final ZonedDateTime issuedDate = ZonedDateTime.now();
        final ZonedDateTime expirationDate = issuedDate.plusSeconds(validFor);
        final AuthTokenDetails newTokenDetails = new AuthTokenDetails(
                currentAuthTokenDetails.getId(), currentAuthTokenDetails.getUsername(),
                currentAuthTokenDetails.getRoles(), issuedDate, expirationDate);
//        return tokenIssuer.issueToken(newTokenDetails);
        return issueToken(newTokenDetails);
    }

    public String issueToken(AuthTokenDetails authTokenDetails) {
        return Jwts.builder()
                .setId(authTokenDetails.getId())
                .setIssuer(settings.getIssuer())
                .setAudience(settings.getAudience())
                .setSubject(authTokenDetails.getUsername())
                .setIssuedAt(Date.from(authTokenDetails.getIssuedDate().toInstant()))
                .setExpiration(Date.from(authTokenDetails.getExpirationDate().toInstant()))
                .claim(settings.getAuthoritiesClaimName(), authTokenDetails.getRoles())
                .signWith(SignatureAlgorithm.HS512, settings.getSecret())
                .compact();
    }

    @Override
    public AuthTokenDetails parseToken(String token) {
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(settings.getSecret())
                    .requireAudience(settings.getAudience())
                    .setAllowedClockSkewSeconds(settings.getClockSkew())
                    .parseClaimsJws(token)
                    .getBody();

            return new AuthTokenDetails(extractTokenIdFromClaims(claims),
                    extractUsernameFromClaims(claims),
                    extractAuthoritiesFromClaims(claims),
                    extractIssuedDateFromClaims(claims),
                    extractExpirationDateFromClaims(claims));

        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            throw new InvalidAuthTokenException("Invalid token", e);
        } catch (ExpiredJwtException e) {
            throw new ExpiredAuthTokenException("The access token expired", e);
        } catch (InvalidClaimException e) {
            throw new InvalidAuthTokenException("Invalid value for claim \"" + e.getClaimName() + "\"", e);
        } catch (Exception e) {
            throw new InvalidAuthTokenException("Invalid token", e);
        }
    }

    private String extractTokenIdFromClaims(@NotNull Claims claims) {
        return (String) claims.get(Claims.ID);
    }


    private String extractUsernameFromClaims(@NotNull Claims claims) {
        return claims.getSubject();
    }

    private Set<Roles> extractAuthoritiesFromClaims(@NotNull Claims claims) {
        List<String> rolesAsString = (List<String>) claims.getOrDefault(settings.getAuthoritiesClaimName(), new ArrayList<>());
        return rolesAsString.stream().map(Roles::valueOf).collect(Collectors.toSet());
    }

    private ZonedDateTime extractIssuedDateFromClaims(@NotNull Claims claims) {
        return ZonedDateTime.ofInstant(claims.getIssuedAt().toInstant(), ZoneId.systemDefault());
    }

    private ZonedDateTime extractExpirationDateFromClaims(@NotNull Claims claims) {
        return ZonedDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }
}

