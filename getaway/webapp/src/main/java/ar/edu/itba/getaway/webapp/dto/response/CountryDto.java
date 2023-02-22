package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.CountryModel;

import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

public class CountryDto implements Serializable {
    private long id;
    private String name;
    private String citiesUrl;

    public static Collection<CountryDto> mapCountryToDto(Collection<CountryModel> countries, UriInfo uriInfo) {
        return countries.stream().map(country -> new CountryDto(country, uriInfo)).collect(Collectors.toList());
    }

    public CountryDto() {
        // Used by Jersey
    }

    public CountryDto(CountryModel country, UriInfo uriInfo) {
        this.id = country.getCountryId();
        this.name = country.getCountryName();
        this.citiesUrl = uriInfo.getBaseUriBuilder()
                .path("location")
                .path("country")
                .path(String.valueOf(country.getCountryId()))
                .path("cities")
                .build().toString();    // /location/country/{id}/cities
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCitiesUrl() {
        return citiesUrl;
    }

    public void setCitiesUrl(String citiesUrl) {
        this.citiesUrl = citiesUrl;
    }
}
