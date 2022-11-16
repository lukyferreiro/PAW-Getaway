package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<CityModel> listAllCities();

    Optional<CityModel> getCityById(long cityId);

    Optional<CityModel> getCityByName(String cityName);

    Optional<CountryModel> getCountryByName();
}
