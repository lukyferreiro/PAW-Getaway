package ar.edu.itba.getaway.webapp.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidAuthTokenException extends AuthenticationException {

    public InvalidAuthTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
