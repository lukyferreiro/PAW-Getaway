package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.persistence.CityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Autowired
    public CityServiceImpl(CityDao cityDao){
        this.cityDao = cityDao;
    }

    @Override
    public List<CityModel> listAll() {
        return cityDao.listAll();
    }

    @Override
    public Optional<CityModel> getById (long cityId){
        return cityDao.getById(cityId);
    }

    @Override
    public List<CityModel> getByCountryId(long countryId) {
        return cityDao.getByCountryId(countryId);
    }

    @Override
    public Optional<CityModel> getIdByName(String cityName) {
        return cityDao.getIdByName(cityName);
    }
}