package ar.edu.itba.getaway.webapp.security.models;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BasicAuthToken extends UsernamePasswordAuthenticationToken {

    private String token;
    private AuthTokenDetails tokenDetails;

    public BasicAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities,
                          AuthTokenDetails tokenDetails) {
        super(principal, credentials, authorities);
        this.tokenDetails = tokenDetails;
    }


    public BasicAuthToken(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return (String) super.getPrincipal();
    }

    @Override
    public String getCredentials() {
        return (String) super.getCredentials();
    }

    @Override
    public Object getDetails() {
        return this.tokenDetails;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }
}
