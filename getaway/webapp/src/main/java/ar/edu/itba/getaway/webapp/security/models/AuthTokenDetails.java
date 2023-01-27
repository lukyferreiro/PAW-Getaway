package ar.edu.itba.getaway.webapp.security.models;

import ar.edu.itba.getaway.models.Roles;

import java.time.ZonedDateTime;
import java.util.Set;

public class AuthTokenDetails {

    private final String id;
    private final String username;
    private final Set<Roles> roles;
    private final ZonedDateTime issuedDate;
    private final ZonedDateTime expirationDate;

    public AuthTokenDetails(String id, String username, Set<Roles> roles, ZonedDateTime issuedDate, ZonedDateTime expirationDate) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public ZonedDateTime getIssuedDate() {
        return issuedDate;
    }

    public ZonedDateTime getExpirationDate() {
        return expirationDate;
    }

}
