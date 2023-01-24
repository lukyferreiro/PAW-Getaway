package ar.edu.itba.getaway.interfaces.exceptions;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException() {
        super("errors.NotFound.country");
    }
}
