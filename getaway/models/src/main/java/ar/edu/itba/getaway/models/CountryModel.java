package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "countries")
public class CountryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countries_countryId_seq")
    @SequenceGenerator(sequenceName = "countries_countryId_seq", name = "countries_countryId_seq", allocationSize = 1)
    @Column(name = "countryId")
    private Long countryId;
    @Column(name = "countryName", nullable = false, unique = true)
    private String countryName;

    /* default */
    protected CountryModel() {
        // Just for Hibernate
    }

    public CountryModel(String countryName) {
        this.countryName = countryName;
    }

    //Constructor used in testing
    public CountryModel(Long countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public Long getCountryId() {
        return countryId;
    }
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof CountryModel)){
            return false;
        }
        CountryModel other = (CountryModel) o;
        return this.countryId.equals(other.countryId) && this.countryName.equals(other.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId);
    }

}