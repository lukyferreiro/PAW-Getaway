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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CitiesController {

    @Autowired
    CityService cityService;


    @Autowired
    CountryService countryService;

    
    @RequestMapping(path = "/create_experience",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CityModel> getCities(@RequestParam String country){

       Optional<CountryModel> currentCountry = countryService.getIdByCountryName(country);

        List<CityModel> cityModels = cityService.getByCountryId(currentCountry.get().getId());

        return cityModels;
    }

}
