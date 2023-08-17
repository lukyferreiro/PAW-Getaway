package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.CountryModel;
import ar.edu.itba.getaway.models.UserModel;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

public class CountryDto implements Serializable {
    private long id;
    private String name;
    private URI self;
    private URI citiesUrl;

    public static Collection<CountryDto> mapCountryToDto(Collection<CountryModel> countries, UriInfo uriInfo) {
        return countries.stream().map(country -> new CountryDto(country, uriInfo)).collect(Collectors.toList());
    }

    public static UriBuilder getCountryUriBuilder(CountryModel country, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone().path("location").path("countries").path(String.valueOf(country.getCountryId()));
    }

    public CountryDto() {
        // Used by Jersey
    }

    public CountryDto(CountryModel country, UriInfo uriInfo) {
        final UriBuilder uriBuilder = getCountryUriBuilder(country, uriInfo);
        this.id = country.getCountryId();
        this.name = country.getCountryName();
        this.self =  uriBuilder.clone().build();
        this.citiesUrl = uriBuilder.clone().path("cities").build();
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
    public URI getCitiesUrl() {
        return citiesUrl;
    }
    public void setCitiesUrl(URI citiesUrl) {
        this.citiesUrl = citiesUrl;
    }
    public URI getSelf() {
        return self;
    }
    public void setSelf(URI self) {
        this.self = self;
    }
}
