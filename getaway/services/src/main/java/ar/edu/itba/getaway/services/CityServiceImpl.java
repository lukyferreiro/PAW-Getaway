package Interfaces.Necessary.City;


import Models.Necessary.CityModel;

import java.util.List;
import java.util.Optional;

public class CityServiceImpl {
    @Autowired
    private CityDao cityDao;

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
    public List<CityModel> list() {
        return cityDao.list();
    }

    @Override
    public Optional<CityModel> getById (long cityId){
        return cityDao.getById(cityId);
    }
}