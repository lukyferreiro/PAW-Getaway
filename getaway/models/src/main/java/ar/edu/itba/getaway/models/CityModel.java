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

    public Long getCityId() {
        return cityId;
    }
    public Long getCountryId() {
        return countryId;
    }
    public String getCityName() {
        return cityName;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof CityModel)){
            return false;
        }
        CityModel other = (CityModel) o;
        return this.cityId.equals(other.cityId);
    }
}
