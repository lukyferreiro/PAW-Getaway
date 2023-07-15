package ar.edu.itba.getaway.webapp.constraints;

import ar.edu.itba.getaway.interfaces.services.CategoryService;
import ar.edu.itba.getaway.models.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class NotExistingCategoryValidator implements ConstraintValidator<NotExistingCategory, Long> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public void initialize(NotExistingCategory constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Optional<CategoryModel> category = categoryService.getCategoryById(value);
        return !category.isPresent() ;
    }
}