package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;
import ar.edu.itba.getaway.persistence.LocationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);

    @Override
    public List<CityModel> listAllCities() {
        LOGGER.debug("Retrieving all cities");
        return locationDao.listAllCities();
    }

    @Override
    public Optional<CityModel> getCityById(Long cityId) {
        LOGGER.debug("Retrieving city with id {}", cityId);
        return locationDao.getCityById(cityId);
    }

    @Override
    public List<CityModel> getCitiesByCountryId(Long countryId) {
        LOGGER.debug("Retrieving all cities of country with id {}", countryId);
        return locationDao.getCitiesByCountryId(countryId);
    }

    @Override
    public Optional<CityModel> getCityByName(String cityName) {
        LOGGER.debug("Retrieving city with name {}", cityName);
        return locationDao.getCityByName(cityName);
    }

    @Override
    public List<CountryModel> listAllCountries() {
        LOGGER.debug("Retrieving all countries");
        return locationDao.listAllCountries();
    }

    @Override
    public Optional<CountryModel> getCountryById(Long countryId) {
        LOGGER.debug("Retrieving country with id {}", countryId);
        return locationDao.getCountryById(countryId);
    }

    @Override
    public Optional<CountryModel> getCountryByName(String countryName) {
        LOGGER.debug("Retrieving country with name {}", countryName);
        return locationDao.getCountryByName(countryName);
    }
}