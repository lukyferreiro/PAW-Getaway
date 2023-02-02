package ar.edu.itba.getaway.webapp.security.models;

import ar.edu.itba.getaway.models.Roles;

import java.time.ZonedDateTime;
import java.util.Set;

public class AuthToken {

    private final String id;
    private final String email;
    private final Set<Roles> roles;
    private final ZonedDateTime issuedDate;
    private final ZonedDateTime expirationDate;

    public AuthToken(String id, String email, Set<Roles> roles, ZonedDateTime issuedDate, ZonedDateTime expirationDate) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
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
