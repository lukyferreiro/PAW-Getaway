package ar.edu.itba.getaway.webapp.constraints;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InvalidImageSizeValidator implements ConstraintValidator<ImageSizeConstraint, CommonsMultipartFile> {

    private long maxSize;
    public void initialize(ImageSizeConstraint constraintAnnotation) {
        this.maxSize = constraintAnnotation.size();
    }

    public boolean isValid(CommonsMultipartFile file, ConstraintValidatorContext constraintContext) {
        if (file == null){
            return true;
        }
        return file.getSize() <= maxSize*1024*1024;
    }
}


