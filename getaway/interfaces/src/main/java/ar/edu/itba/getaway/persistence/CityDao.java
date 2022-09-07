package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CityModel;

import java.util.List;
import java.util.Optional;

public interface CityDao {
    CityModel create(CityModel cityModel);
    boolean update(long cityId, CityModel cityModel);
    boolean delete(long cityId);
    Optional<CityModel> getById (long cityId);
    List<CityModel> listAll();
    List<CityModel> getByCountryId (long countryId);
    Optional<CityModel> getIdByName(String cityName);
}
