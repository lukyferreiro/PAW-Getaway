package ar.edu.itba.getaway.webapp.security.models;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BasicAuthToken extends UsernamePasswordAuthenticationToken {

    private String token;
    private String refreshToken;
    private JwtTokenDetails tokenDetails;

    public BasicAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, JwtTokenDetails tokenDetails) {
        super(principal, credentials, authorities);
        this.tokenDetails = tokenDetails;
    }

    public BasicAuthToken(String token) {
        super(null, null);
        this.token = token;
        this.refreshToken = null;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
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
