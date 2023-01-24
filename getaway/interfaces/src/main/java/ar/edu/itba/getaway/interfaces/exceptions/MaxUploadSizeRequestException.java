package ar.edu.itba.getaway.interfaces.exceptions;

public class MaxUploadSizeRequestException extends RuntimeException {
    public MaxUploadSizeRequestException() { super("experienceForm.validation.imageSize"); }
}
