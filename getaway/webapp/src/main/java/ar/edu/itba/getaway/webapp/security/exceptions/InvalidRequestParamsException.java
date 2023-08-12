package ar.edu.itba.getaway.webapp.security.exceptions;

public class InvalidRequestParamsException extends RuntimeException {

    public InvalidRequestParamsException(String message) {
        super(message);
    }
}
