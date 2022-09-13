package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CountryModel;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    Optional<CountryModel> getById (long countryId);
    List<CountryModel> listAll ();
}
