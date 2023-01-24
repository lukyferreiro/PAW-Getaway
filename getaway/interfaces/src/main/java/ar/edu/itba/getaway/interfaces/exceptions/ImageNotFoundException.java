package ar.edu.itba.getaway.interfaces.exceptions;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException() { super("errors.NotFound.image"); }
}
