package ar.edu.itba.getaway.interfaces.exceptions;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException() { super("errors.NotFound.review"); }

}
