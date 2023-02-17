package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    Optional<CityModel> getCityById(long cityId);

    List<CityModel> getCitiesByCountry(CountryModel country);

    Optional<CityModel> getCityByName(String cityName);

    List<CountryModel> listAllCountries();

    Optional<CountryModel> getCountryById(long countryId);

    Optional<CountryModel> getCountryByName(String countryName);
}
