package ar.edu.itba.getaway.webapp.constraints;

import ar.edu.itba.getaway.interfaces.services.LocationService;
import ar.edu.itba.getaway.models.CityModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class NotExistingCityValidator implements ConstraintValidator<NotExistingCity, Long> {

    @Autowired
    private LocationService locationService;

    @Override
    public void initialize(NotExistingCity constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Optional<CityModel> city = locationService.getCityById(value);
        return !city.isPresent() ;
    }
}
