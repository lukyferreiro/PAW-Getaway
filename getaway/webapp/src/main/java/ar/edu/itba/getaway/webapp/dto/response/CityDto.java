package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.CityModel;

import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

public class CityDto implements Serializable {

    private long id;
    private String name;
    private CountryDto countryDto;
    private URI self;

    public static Collection<CityDto> mapCityToDto(Collection<CityModel> cities, UriInfo uriInfo) {
        return cities.stream().map(city -> new CityDto(city, uriInfo)).collect(Collectors.toList());
    }

    public CityDto() {
        // Used by Jersey
    }

    public CityDto(CityModel city, UriInfo uriInfo) {
        this.id = city.getCityId();
        this.name = city.getCityName();
        this.countryDto = new CountryDto(city.getCountry(), uriInfo);
        this.self = uriInfo.getBaseUriBuilder()
                .path("location")
                .path("cities")
                .path(String.valueOf(city.getCityId()))
                .build();       // /location/cities/{cityId}
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
    public CountryDto getCountryDto() {
        return countryDto;
    }
    public void setCountryDto(CountryDto countryDto) {
        this.countryDto = countryDto;
    }
    public URI getSelf() {
        return self;
    }
    public void setSelf(URI self) {
        this.self = self;
    }
}
