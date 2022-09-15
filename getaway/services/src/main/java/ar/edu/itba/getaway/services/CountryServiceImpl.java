package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CountryModel;
import ar.edu.itba.getaway.persistence.CountryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDao countryDao;

    @Autowired
    public CountryServiceImpl(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public List<CountryModel> listAll() {
        return countryDao.listAll();
    }

    @Override
    public Optional<CountryModel> getById(long countryId) {
        return countryDao.getById(countryId);
    }

    @Override
    public Optional<CountryModel> getIdByCountryName(String country) {
        return countryDao.getIdByCountryName(country);
    }
}

