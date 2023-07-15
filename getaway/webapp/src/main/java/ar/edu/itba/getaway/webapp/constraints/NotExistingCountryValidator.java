package ar.edu.itba.getaway.webapp.constraints;

import ar.edu.itba.getaway.interfaces.services.LocationService;
import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;


public class NotExistingCountryValidator implements ConstraintValidator<NotExistingCountry, Long> {

    @Autowired
    private LocationService locationService;

    @Override
    public void initialize(NotExistingCountry constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Optional<CountryModel> country = locationService.getCountryById(value);
        return !country.isPresent() ;
    }
}