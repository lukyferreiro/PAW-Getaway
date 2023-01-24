package ar.edu.itba.getaway.interfaces.exceptions;

public class IllegalOperationException extends RuntimeException {
    public IllegalOperationException() {
        super("errors.illegalOperation");
    }
}
