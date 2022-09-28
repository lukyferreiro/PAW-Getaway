package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.persistence.CityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

    @Override
    public List<CityModel> listAll() {
        LOGGER.debug("Retrieving all cities");
        return cityDao.listAll();
    }

    @Override
    public Optional<CityModel> getById(long cityId) {
        LOGGER.debug("Retrieving city with name {}", cityId);
        return cityDao.getById(cityId);
    }

    @Override
    public List<CityModel> getByCountryId(long countryId) {
        LOGGER.debug("Retrieving all cities of country with id {}", countryId);
        return cityDao.getByCountryId(countryId);
    }

    @Override
    public Optional<CityModel> getIdByName(String cityName) {
        LOGGER.debug("Retrieving city with name {}", cityName);
        return cityDao.getIdByName(cityName);
    }

}