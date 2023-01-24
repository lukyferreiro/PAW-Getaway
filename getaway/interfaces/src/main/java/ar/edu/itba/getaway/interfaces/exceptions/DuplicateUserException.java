package ar.edu.itba.getaway.interfaces.exceptions;

public class DuplicateUserException extends Exception {
    public DuplicateUserException() {
        super("validation.user.DuplicateEmail");
    }
}
