package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.*;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ExperienceDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String email;
    private Double price;
    private Long score;
    private Integer views;
    private String siteUrl;
    private boolean isFav;
    private boolean observable;
    private boolean hasImage;
    private Long reviewCount;
    private URI self;
    private URI imageUrl;
    private URI reviewsUrl;
    private URI cityURL;
    private URI userUrl;
    private URI categoryUrl;
    private String cityName;
    private String countryName;
    private String categoryName;
    private HashMap<String, String> user = new HashMap<>();

    public static Collection<ExperienceDto> mapExperienceToDto(Collection<ExperienceModel> experiences, UriInfo uriInfo) {
        return experiences.stream().map(exp -> new ExperienceDto(exp, uriInfo)).collect(Collectors.toList());
    }

    public static UriBuilder getExperienceUriBuilder(ExperienceModel experience, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone().path("experiences").path(String.valueOf(experience.getExperienceId()));
    }

    public ExperienceDto() {
        // Used by Jersey
    }

    //UriInfo no se pa q se usa
    public ExperienceDto(ExperienceModel experience, UriInfo uriInfo) {
        final UriBuilder uriBuilder = getExperienceUriBuilder(experience, uriInfo);
        this.id = experience.getExperienceId();
        this.name = experience.getExperienceName();
        this.description = experience.getDescription();
        this.address = experience.getAddress();
        this.email = experience.getEmail();
        this.price = experience.getPrice();
        this.score = experience.getAverageScore();
        this.siteUrl = experience.getSiteUrl();
        this.views = experience.getViews();
        this.isFav = experience.getIsFav();
        this.observable = experience.getObservable();
        this.hasImage = experience.getImage() != null;
        this.reviewCount = experience.getReviewCount();
        this.self = uriBuilder.build();
        this.imageUrl = uriBuilder.clone().path("experienceImage").build();
        this.reviewsUrl = uriBuilder.clone().path("reviews").build();
        this.cityURL = uriInfo.getBaseUriBuilder().path("location").path("cities").path(String.valueOf(experience.getCity().getCityId())).build();
        this.userUrl = uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(experience.getUser().getUserId())).build();
        this.categoryUrl = uriInfo.getBaseUriBuilder().path("experiences").path("categories").path(String.valueOf(experience.getCategory().getCategoryId())).build();
        this.cityName = experience.getCity().getCityName();
        this.countryName = experience.getCity().getCountry().getCountryName();
        this.categoryName = experience.getCategory().getCategoryName();
        final UserModel user = experience.getUser();
        this.user.put("id", String.valueOf(user.getUserId()));
        this.user.put("name", user.getName());
        this.user.put("surname", user.getSurname());
        this.user.put("hasImage", String.valueOf(user.getImage() != null));
        this.user.put("profileImageUrl", uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(user.getUserId())).path("profileImage").build().toString());
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Long getScore() {
        return score;
    }
    public void setScore(Long score) {
        this.score = score;
    }
    public Integer getViews() {
        return views;
    }
    public void setViews(Integer views) {
        this.views = views;
    }
    public URI getSelf() {
        return self;
    }
    public void setSelf(URI self) {
        this.self = self;
    }
    public URI getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(URI imageUrl) {
        this.imageUrl = imageUrl;
    }
    public URI getReviewsUrl() {
        return reviewsUrl;
    }
    public void setReviewsUrl(URI reviewsUrl) {
        this.reviewsUrl = reviewsUrl;
    }
    public URI getCityURL() {
        return cityURL;
    }
    public void setCityURL(URI cityURL) {
        this.cityURL = cityURL;
    }
    public URI getUserUrl() {
        return userUrl;
    }
    public void setUserUrl(URI userUrl) {
        this.userUrl = userUrl;
    }
    public URI getCategoryUrl() {
        return categoryUrl;
    }
    public void setCategoryUrl(URI categoryUrl) {
        this.categoryUrl = categoryUrl;
    }
    public Long getReviewCount() {
        return reviewCount;
    }
    public void setReviewCount(Long reviewCount) {
        this.reviewCount = reviewCount;
    }
    public boolean isFav() {
        return isFav;
    }
    public void setFav(boolean fav) {
        isFav = fav;
    }
    public boolean isObservable() {
        return observable;
    }
    public void setObservable(boolean observable) {
        this.observable = observable;
    }
    public String getSiteUrl() {
        return siteUrl;
    }
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
    public boolean isHasImage() {
        return hasImage;
    }
    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public HashMap<String, String> getUser() {
        return user;
    }
    public void setUser(HashMap<String, String> user) {
        this.user = user;
    }
}
