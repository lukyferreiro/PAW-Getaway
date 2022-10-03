package ar.edu.itba.getaway.models;

public class CountryModel {
    private final Long countryId;
    private final String countryName;

    public CountryModel(Long countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public Long getId() {
        return countryId;
    }
    public String getName() {
        return countryName;
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
        return this.countryId.equals(other.countryId);
    }
}