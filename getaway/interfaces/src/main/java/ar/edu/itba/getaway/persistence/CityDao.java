package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CityModel;

import java.util.List;
import java.util.Optional;

public interface CityDao {
    List<CityModel> listAll ();
    Optional<CityModel> getById (long cityId);
    List<CityModel> getByCountryId (long countryId);
    Optional<CityModel> getIdByName (String cityName);
}
