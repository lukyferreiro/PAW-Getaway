package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;
import ar.edu.itba.getaway.services.CityService;
import ar.edu.itba.getaway.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CitiesController {

    @Autowired
    CityService cityService;


    @Autowired
    CountryService countryService;

    
    @RequestMapping(path = "/create_experience",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CityModel> getCities(@RequestParam String country){
        List<CountryModel> countryModels = countryService.listAll();
        List<CityModel> cityModels = new ArrayList<>();
        boolean flag = true;
        for (int j = 0; j<countryModels.size() && flag ; j++){
            if(countryModels.get(j).getName().equals(country)){
                return cityService.getByCountryId(j + 1);
            }
        }

        return cityModels;
    }

}
