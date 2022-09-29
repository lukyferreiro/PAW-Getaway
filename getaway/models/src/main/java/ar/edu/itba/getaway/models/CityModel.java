package ar.edu.itba.getaway.models;

public class CityModel {
    private final Long cityId;
    private final Long countryId;
    private final String cityName;

    public CityModel(Long cityId, Long countryId, String cityName) {
        this.cityId = cityId;
        this.countryId = countryId;
        this.cityName = cityName;
    }

    public Long getId() {
        return cityId;
    }
    public Long getCountryId() {
        return countryId;
    }
    public String getName() {
        return cityName;
    }

}
