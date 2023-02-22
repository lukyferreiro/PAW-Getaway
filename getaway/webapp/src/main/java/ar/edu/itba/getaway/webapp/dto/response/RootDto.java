package ar.edu.itba.getaway.webapp.dto.response;

import javax.ws.rs.core.UriInfo;

public class RootDto {

    private String usersUrl;
    private String userExperiencesUrl;
    private String experiencesUrl;
    private String countryUrl;
    private String citiesUrl;

    public RootDto() {
    }

    public RootDto(UriInfo uriInfo) {
        final String baseUrl = uriInfo.getBaseUriBuilder().build().toString();
        //TODO check estas urls de la api
        usersUrl = baseUrl + "users/{id}";
        userExperiencesUrl = baseUrl+"users/{id}/experiences/{?,order,page}";
        experiencesUrl = baseUrl+"experiences/{category}/{?order,price,score,city,page}";
        countryUrl = baseUrl+"location/country";
        citiesUrl = baseUrl+"location/country/{id}/cities";
    }

    public String getUsersUrl() {
        return usersUrl;
    }
    public void setUsersUrl(String usersUrl) {
        this.usersUrl = usersUrl;
    }
    public String getUserExperiencesUrl() {
        return userExperiencesUrl;
    }
    public void setUserExperiencesUrl(String userExperiencesUrl) {
        this.userExperiencesUrl = userExperiencesUrl;
    }
    public String getExperiencesUrl() {
        return experiencesUrl;
    }
    public void setExperiencesUrl(String experiencesUrl) {
        this.experiencesUrl = experiencesUrl;
    }
    public String getCountryUrl() {
        return countryUrl;
    }
    public void setCountryUrl(String countryUrl) {
        this.countryUrl = countryUrl;
    }
    public String getCitiesUrl() {
        return citiesUrl;
    }
    public void setCitiesUrl(String citiesUrl) {
        this.citiesUrl = citiesUrl;
    }
}
