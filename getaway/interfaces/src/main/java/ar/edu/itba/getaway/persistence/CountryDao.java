package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CountryModel;

import java.util.List;
import java.util.Optional;

public interface CountryDao {
    CountryModel create(CountryModel countryModel);
    boolean update(long countryId, CountryModel countryModel);
    boolean delete(long countryId);
    Optional<CountryModel> getById (long countryId);
    List<CountryModel> listAll();
}

