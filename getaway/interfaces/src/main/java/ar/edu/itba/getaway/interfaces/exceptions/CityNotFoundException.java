package ar.edu.itba.getaway.interfaces.exceptions;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException() {
        super("errors.NotFound.city");
    }
}
