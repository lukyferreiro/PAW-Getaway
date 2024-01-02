package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.CountryNotFoundException;
import ar.edu.itba.getaway.interfaces.services.LocationService;
import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;
import ar.edu.itba.getaway.webapp.dto.response.CityDto;
import ar.edu.itba.getaway.webapp.dto.response.CountryDto;
import ar.edu.itba.getaway.webapp.security.api.CustomMediaType;
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

    @Context
    private UriInfo uriInfo;
    private final LocationService locationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // Endpoint para obtener el listado completo de países
    @GET
    @Path("/countries")
    @Produces(value = {CustomMediaType.COUNTRY_LIST_V1})
    public Response getCountries() {
        LOGGER.info("Called /location/countries GET");
        final Collection<CountryModel> countries = locationService.listAllCountries();

        if (countries.isEmpty()) {
            return Response.noContent().build();
        }

        final Collection<CountryDto> countryDtos = CountryDto.mapCountryToDto(countries, uriInfo);
        return Response.ok(new GenericEntity<Collection<CountryDto>>(countryDtos) {}).build();
    }

    // Endpoint para obtener el listado completo de países
    @GET
    @Path("/countries/{countryId:[0-9]+}")
    @Produces(value = {CustomMediaType.COUNTRY_V1})
    public Response getCountryById(
            @PathParam("countryId") final long id
    ) {
        LOGGER.info("Called /location/countries/{} GET", id);
        final CountryModel country = locationService.getCountryById(id).orElseThrow(CountryNotFoundException::new);
        return Response.ok(new CountryDto(country, uriInfo)).build();
    }

    // Endpoint para obtener el listado completo de ciudades del país {id}
    @GET
    @Path("/countries/{countryId:[0-9]+}/cities")
    @Produces(value = {CustomMediaType.CITY_LIST_V1})
    public Response getCitiesByCountryId(
            @PathParam("countryId") final long id
    ) {
        LOGGER.info("Called /location/countries/{}/cities GET", id);
        final Collection<CityModel> cities = locationService.getCitiesByCountry(id);

        if (cities.isEmpty()) {
            return Response.noContent().build();
        }

        final Collection<CityDto> citiesDtos = CityDto.mapCityToDto(cities, uriInfo);
        return Response.ok(new GenericEntity<Collection<CityDto>>(citiesDtos) {}).build();
    }

    // Endpoint para obtener información de la ciudad {id}
    @GET
    @Path("/cities/{cityId:[0-9]+}")
    @Produces(value = {CustomMediaType.CITY_V1})
    public Response getCityById(
            @PathParam("cityId") final long id
    ) {
        LOGGER.info("Called /location/cities/{} GET", id);
        final CityModel city = locationService.getCityById(id).orElseThrow(CityNotFoundException::new);
        return Response.ok(new CityDto(city, uriInfo)).build();
    }
}
