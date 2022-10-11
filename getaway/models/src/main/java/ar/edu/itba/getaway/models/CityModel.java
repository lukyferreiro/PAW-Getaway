package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cities")
public class CityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cities_cityId_seq")
    @SequenceGenerator(sequenceName = "cities_cityId_seq", name = "cities_cityId_seq", allocationSize = 1)
    @Column(name = "cityId")
    private Long cityId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "countryId")
    private CountryModel country;
    @Column(name = "cityName", nullable = false)
    private String cityName;

    /* default */
    protected CityModel() {
        // Just for Hibernate
    }

    public CityModel(CountryModel country, String cityName) {
        this.country = country;
        this.cityName = cityName;
    }

    public Long getCityId() {
        return cityId;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    public CountryModel getCountry() {
        return country;
    }
    public void setCountry(CountryModel country) {
        this.country = country;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    @Override
    public int hashCode() {
        return Objects.hash(cityId);
    }
}
