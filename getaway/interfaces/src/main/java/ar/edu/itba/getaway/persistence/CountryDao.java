package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CountryModel;

import java.util.List;
import java.util.Optional;

public interface CountryDao {
    Optional<CountryModel> getById (long countryId);
    List<CountryModel> listAll ();
}

