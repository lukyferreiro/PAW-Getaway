package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<CityModel> listAllCities();
    Optional<CityModel> getCityById (Long cityId);
    List<CityModel> getCitiesByCountryId (Long countryId);
    Optional<CityModel> getCityByName (String cityName);
    List<CountryModel> listAllCountries ();
    Optional<CountryModel> getCountryById (Long countryId);
    Optional<CountryModel> getCountryByName (String countryName);
}
