package ar.edu.itba.getaway.webapp.controller.queryParamsValidators;

public class InvalidRequestParamsException extends RuntimeException {

    public InvalidRequestParamsException(String message) {
        super(message);
    }
}
