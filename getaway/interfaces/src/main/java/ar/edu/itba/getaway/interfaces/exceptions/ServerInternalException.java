package ar.edu.itba.getaway.interfaces.exceptions;

public class ServerInternalException extends RuntimeException{
    public ServerInternalException() { super("errors.ServerError"); }
}
