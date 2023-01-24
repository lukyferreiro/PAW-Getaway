package ar.edu.itba.getaway.interfaces.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("errors.NotFound.category");
    }
}
