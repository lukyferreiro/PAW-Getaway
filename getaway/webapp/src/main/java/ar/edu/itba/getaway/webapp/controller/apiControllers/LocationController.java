package ar.edu.itba.getaway.webapp.controller.apiControllers;

import ar.edu.itba.getaway.interfaces.exceptions.CountryNotFoundException;
import ar.edu.itba.getaway.interfaces.services.LocationService;
import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;
import ar.edu.itba.getaway.webapp.dto.response.CityDto;
import ar.edu.itba.getaway.webapp.dto.response.CountryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Collection;

@Path("location")
@Component
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Context
    UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    @GET
    @Path("/country")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getCountries() {
        LOGGER.info("Called /locations/contry GET");

        Collection<CountryModel> countries = locationService.listAllCountries();
        Collection<CountryDto> countryDtos = CountryDto.mapCountryToDto(countries, uriInfo);

        return Response.ok(new GenericEntity<Collection<CountryDto>>(countryDtos) {}).build();
    }

    @GET
    @Path("/country/{id}/cities")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getCountryCities(@PathParam("id") final long id) {
        LOGGER.info("Called /locations/country/{}/cities GET", id);

        CountryModel country = locationService.getCountryById(id).orElseThrow(CountryNotFoundException::new);
        Collection<CityModel> cities = locationService.getCitiesByCountry(country);
        Collection<CityDto> citiesDtos = CityDto.mapCityToDto(cities);

        return Response.ok(new GenericEntity<Collection<CityDto>>(citiesDtos) {}).build();
    }
}
