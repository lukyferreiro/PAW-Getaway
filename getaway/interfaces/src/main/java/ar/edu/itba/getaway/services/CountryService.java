package Interfaces.Necessary.Country;

import Models.Necessary.CountryModel;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    CountryModel create (CountryModel countryModel);
    boolean update (long countryId, CountryModel countryModel);
    boolean delete (long countryId);
    List<CountryModel> list();
    Optional<CountryModel> getByID (long countryId);
}
