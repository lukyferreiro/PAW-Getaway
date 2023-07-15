package ar.edu.itba.getaway.webapp.constraints;

import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class EmailNotUsedValidator implements ConstraintValidator<EmailNotUsed, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(EmailNotUsed constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<UserModel> user = userService.getUserByEmail(value);
        return !user.isPresent() ;
    }
}
