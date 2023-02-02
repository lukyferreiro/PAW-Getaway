package ar.edu.itba.getaway.webapp.security.services;

import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.webapp.security.exceptions.ExpiredAuthTokenException;
import ar.edu.itba.getaway.webapp.security.exceptions.InvalidAuthTokenException;
import ar.edu.itba.getaway.webapp.security.models.AuthToken;
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

//    @Value("${authentication.jwt.validFor}")
//    private Long validFor;

    @Autowired
    Settings settings;

    @Override
    public String issueToken(String email, Set<Roles> roles) {
        final String id = UUID.randomUUID().toString();
        final ZonedDateTime issuedDate = ZonedDateTime.now();
        final ZonedDateTime expirationDate = issuedDate.plusSeconds(settings.getValidFor());
        final AuthToken authToken = new AuthToken(id, email, roles, issuedDate, expirationDate);
        return createToken(authToken);
    }

    @Override
    public String refreshToken(AuthToken currentAuthToken) {
        final ZonedDateTime issuedDate = ZonedDateTime.now();
        final ZonedDateTime expirationDate = issuedDate.plusSeconds(settings.getValidFor());
        final AuthToken newTokenDetails = new AuthToken(
                currentAuthToken.getId(), currentAuthToken.getEmail(),
                currentAuthToken.getRoles(), issuedDate, expirationDate);
        return createToken(newTokenDetails);
    }

    @Override
    public AuthToken parseToken(String token) {
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(settings.getSecret())
                    .requireAudience(settings.getAudience())
                    .setAllowedClockSkewSeconds(settings.getClockSkew())
                    .parseClaimsJws(token)
                    .getBody();

            return new AuthToken(extractTokenIdFromClaims(claims),
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

    private String createToken(AuthToken authToken) {
        return Jwts.builder()
                .setId(authToken.getId())
                .setIssuer(settings.getIssuer())
                .setAudience(settings.getAudience())
                .setSubject(authToken.getEmail())
                .setIssuedAt(Date.from(authToken.getIssuedDate().toInstant()))
                .setExpiration(Date.from(authToken.getExpirationDate().toInstant()))
                .claim(settings.getAuthoritiesClaimName(), authToken.getRoles())
                .signWith(SignatureAlgorithm.HS512, settings.getSecret())
                .compact();
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

