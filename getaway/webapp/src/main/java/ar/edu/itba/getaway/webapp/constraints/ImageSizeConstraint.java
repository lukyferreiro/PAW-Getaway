package ar.edu.itba.getaway.webapp.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {InvalidImageSizeValidator.class})
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface ImageSizeConstraint {

    String message() default "Image is too big";

    long size();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        long[] value();
    }
}