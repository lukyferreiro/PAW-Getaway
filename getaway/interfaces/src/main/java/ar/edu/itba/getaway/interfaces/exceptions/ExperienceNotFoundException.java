package ar.edu.itba.getaway.interfaces.exceptions;

public class ExperienceNotFoundException extends RuntimeException {
    public ExperienceNotFoundException() {
        super("errors.NotFound.experience");
    }
}
