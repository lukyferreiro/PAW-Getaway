package Interfaces.Necessary.Country;

import Models.Necessary.CountryModel;

import java.util.List;
import java.util.Optional;

public interface CountryDao {
    CountryModel create(CountryModel countryModel);
    boolean update(int countryId, CountryModel countryModel);
    boolean delete(int countryId);
    Optional<CountryModel> getById(long countryId);
    List<CountryModel> list();
}

