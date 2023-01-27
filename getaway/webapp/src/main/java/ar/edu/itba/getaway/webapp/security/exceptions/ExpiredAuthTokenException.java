package ar.edu.itba.getaway.webapp.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class ExpiredAuthTokenException extends AuthenticationException {
    public ExpiredAuthTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
