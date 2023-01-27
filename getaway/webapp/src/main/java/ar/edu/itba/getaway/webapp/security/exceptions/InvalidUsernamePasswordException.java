package ar.edu.itba.getaway.webapp.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidUsernamePasswordException extends AuthenticationException {
    public InvalidUsernamePasswordException(String message) {
        super(message);
    }
}
