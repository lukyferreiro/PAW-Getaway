package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CityModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface CityService {
    List<CityModel> listAll ();
    Optional<CityModel> getById (long cityId);
    List<CityModel> getByCountryId (long countryId);
    Optional<CityModel> getIdByName (String cityName);
//    List<CityModel> getByCountryName(@RequestParam String country);
}
