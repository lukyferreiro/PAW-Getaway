package ar.edu.itba.getaway.interfaces.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() { super("errors.NotFound.user"); }
}
