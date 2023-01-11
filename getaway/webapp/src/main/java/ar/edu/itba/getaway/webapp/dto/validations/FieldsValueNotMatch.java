package ar.edu.itba.getaway.webapp.dto.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FieldsValueNotMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsValueNotMatch {

    String message() default "Fields values can't match";

    String field();

    String fieldNotMatch();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}