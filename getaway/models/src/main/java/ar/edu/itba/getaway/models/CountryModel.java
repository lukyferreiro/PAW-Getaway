package ar.edu.itba.getaway.models;

public class CountryModel {
    private final long countryId;
    private final String countryName;

    public CountryModel(long countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public long getId() {
        return countryId;
    }
    public String getName() {
        return countryName;
    }
}