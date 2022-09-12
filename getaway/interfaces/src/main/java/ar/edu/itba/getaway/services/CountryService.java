package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CountryModel;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    CountryModel create (CountryModel countryModel);
    boolean update (long countryId, CountryModel countryModel);
    boolean delete (long countryId);
    Optional<CountryModel> getById (long countryId);
    List<CountryModel> listAll();

    Optional<CountryModel> getIdByCountryName(String country);
}
