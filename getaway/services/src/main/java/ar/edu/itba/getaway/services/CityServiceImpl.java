package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;
import ar.edu.itba.getaway.persistence.CityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

    @Override
    public List<CityModel> listAll() {
        return cityDao.listAll();
    }

    @Override
    public Optional<CityModel> getById(long cityId) {
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

    @Autowired
    CountryService countryService;

    @RequestMapping(path = "/create_experience", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Override
    public List<CityModel> getByCountryName(@RequestParam String country) {
        List<CountryModel> countryModels = countryService.listAll();
        List<CityModel> cityModels = new ArrayList<>();
        for (int j = 0; j < countryModels.size(); j++) {
            if (countryModels.get(j).getName().equals(country)) {
                return this.getByCountryId(j + 1);
            }
        }
        return cityModels;
    }
}