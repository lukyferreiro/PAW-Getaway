package ar.edu.itba.getaway.models;

public class CityModel {
    private final long cityId;
    private final long countryId;
    private final String cityName, countryName;

    public CityModel(long cityId, long countryId, String cityName, String countryName) {
        this.cityId = cityId;
        this.countryId = countryId;
        this.cityName = cityName;
        this.countryName = countryName;
    }

    public long getId() {
        return cityId;
    }

    public long getCountryId() {
        return countryId;
    }

    public String getName() {
        return cityName;
    }

    public String getCountryName() {
        return countryName;
    }
}
