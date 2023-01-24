package ar.edu.itba.getaway.interfaces.exceptions;

public class ContentExpectedException extends RuntimeException  {
    public ContentExpectedException() {
        super("errors.contentExpected");
    }
}
