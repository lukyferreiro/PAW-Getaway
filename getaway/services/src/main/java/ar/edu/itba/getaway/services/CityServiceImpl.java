package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.persistence.CityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private CityDao cityDao;

    @Autowired
    public CityServiceImpl(CityDao cityDao){
        this.cityDao = cityDao;
    }

    @Override
    public CityModel create (CityModel cityModel){
        return cityDao.create(cityModel);
    }

    @Override
    public boolean update(long cityId, CityModel cityModel){
        return cityDao.update(cityId, cityModel);
    }

    @Override
    public boolean delete (long cityId){
        return cityDao.delete(cityId);
    }

    @Override
    public List<CityModel> listAll() {
        return cityDao.listAll();
    }

    @Override
    public Optional<CityModel> getById (long cityId){
        return cityDao.getById(cityId);
    }
}