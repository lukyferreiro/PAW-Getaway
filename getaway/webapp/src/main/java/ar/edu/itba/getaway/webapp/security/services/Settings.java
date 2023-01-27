package ar.edu.itba.getaway.webapp.security.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class Settings {

    @Value("${authentication.jwt.secret}")
    private String secret;

    @Value("${authentication.jwt.clockSkew}")
    private Long clockSkew;

    @Value("${authentication.jwt.audience}")
    private String audience;

    @Value("${authentication.jwt.issuer}")
    private String issuer;

    @Value("${authentication.jwt.claimNames.authorities}")
    private String authoritiesClaimName;


    public String getSecret() {
        return secret;
    }

    public Long getClockSkew() {
        return clockSkew;
    }

    public String getAudience() {
        return audience;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getAuthoritiesClaimName() {
        return authoritiesClaimName;
    }

}
