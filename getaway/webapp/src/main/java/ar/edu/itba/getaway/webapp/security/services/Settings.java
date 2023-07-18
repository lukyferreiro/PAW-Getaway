package ar.edu.itba.getaway.webapp.security.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class Settings {

    @Value("${authentication.jwt.secret}")
    private String secret;
    @Value("${authentication.jwt.issuer}")
    private String issuer;
    @Value("${authentication.jwt.audience}")
    private String audience;
    @Value("${authentication.jwt.validForAccess}")
    private Long validForAccess;
    @Value("${authentication.jwt.validForRefresh}")
    private Long validForRefresh;
    @Value("${authentication.jwt.claimName.authorities}")
    private String authoritiesClaim;
    @Value("${authentication.jwt.claimName.tokenType}")
    private String tokenTypeClaim;
    @Value("${authentication.jwt.claimName.isVerified}")
    private String isVerifiedClaim;
    @Value("${authentication.jwt.claimName.isProvider}")
    private String isProviderClaim;
    @Value("${authentication.jwt.claimName.userId}")
    private String userIdClaim;
    @Value("${authentication.jwt.claimName.profileImageUrl}")
    private String profileImageUrlClaim;
    @Value("${authentication.jwt.claimName.name}")
    private String nameClaim;
    @Value("${authentication.jwt.claimName.surname}")
    private String surnameClaim;
    @Value("${authentication.jwt.claimName.hasImage}")
    private String hasImageClaim;

    public String getSecret() {
        return secret;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getAudience() {
        return audience;
    }

    public Long getValidForAccess() {
        return validForAccess;
    }

    public Long getValidForRefresh() {
        return validForRefresh;
    }

    public String getAuthoritiesClaim() {
        return authoritiesClaim;
    }

    public String getTokenTypeClaim() {
        return tokenTypeClaim;
    }

    public String getIsVerifiedClaim() {
        return isVerifiedClaim;
    }

    public String getIsProviderClaim() {
        return isProviderClaim;
    }

    public String getUserIdClaim() {
        return userIdClaim;
    }

    public String getProfileImageUrlClaim() {
        return profileImageUrlClaim;
    }

    public String getNameClaim() {
        return nameClaim;
    }

    public String getSurnameClaim() {
        return surnameClaim;
    }

    public String getHasImageClaim() {
        return hasImageClaim;
    }
}
