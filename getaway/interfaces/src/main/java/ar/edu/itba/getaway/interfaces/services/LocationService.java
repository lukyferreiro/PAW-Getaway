package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<CityModel> listAllCities();

    Optional<CityModel> getCityById(long cityId);

    List<CityModel> getCitiesByCountry(CountryModel country);

    Optional<CityModel> getCityByName(String cityName);

    List<CountryModel> listAllCountries();

    Optional<CountryModel> getCountryById(long countryId);

    Optional<CountryModel> getCountryByName();
}
