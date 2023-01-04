package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.CityModel;

import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.stream.Collectors;

@XmlType(name = "")
public class CityDto {

    private long id;
    private String name;

    public static Collection<CityDto> mapCityToDto(Collection<CityModel> states) {
        return states.stream().map(CityDto::new).collect(Collectors.toList());
    }

    public CityDto() {
        // Used by Jersey
    }

    public CityDto(CityModel city) {
        this.id = city.getCityId();
        this.name = city.getCityName();
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
}
