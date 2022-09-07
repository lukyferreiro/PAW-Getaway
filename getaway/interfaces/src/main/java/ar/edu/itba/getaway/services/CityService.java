package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CityModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface CityService {
    CityModel create (CityModel cityModel);
    boolean update (long cityId, CityModel cityModel);
    boolean delete (long cityId);
    List<CityModel> listAll();
    Optional<CityModel> getById (long cityId);
    List<CityModel> getByCountryId (long countryId);
    List<CityModel> getByCountryName(@RequestParam String country);
}
