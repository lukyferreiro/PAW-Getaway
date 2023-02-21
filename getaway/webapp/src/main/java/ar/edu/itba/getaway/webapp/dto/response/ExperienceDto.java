package ar.edu.itba.getaway.webapp.dto.response;


import ar.edu.itba.getaway.models.*;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    private String selfUrl;
    private String siteUrl;
    private String imageUrl;
    private String reviewsUrl;
    private Long reviewCount;
    private CountryDto country;
    private CityDto city;
    private UserDto user;
    private CategoryDto category;
    private boolean isFav;
    private boolean observable;
    private boolean hasImage;

    public static Collection<ExperienceDto> mapExperienceToDto(Collection<ExperienceModel> experiences, UriInfo uriInfo) {
        return experiences.stream().map(exp -> new ExperienceDto(exp, uriInfo)).collect(Collectors.toList());
    }

    public static UriBuilder getExperienceUriBuilder(ExperienceModel experience, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone().path("experiences/experience").path(String.valueOf(experience.getExperienceId()));
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
        this.selfUrl = uriBuilder.build().toString();
        this.imageUrl = uriBuilder.clone().path("image").build().toString();    // /experience/{id}/image
        this.reviewsUrl = uriBuilder.clone().path("reviews").build().toString();    // /experience/{id}/reviews
        this.reviewCount = experience.getReviewCount();
        this.country = new CountryDto(experience.getCity().getCountry(), uriInfo);
        this.city = new CityDto(experience.getCity());
        this.user = new UserDto(experience.getUser(), uriInfo);
        this.category = new CategoryDto(experience.getCategory());
        this.isFav = experience.getIsFav();
        this.observable = experience.getObservable();
        this.hasImage = experience.getImage() != null;
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
    public String getSelfUrl() {
        return selfUrl;
    }
    public void setSelfUrl(String selfUrl) {
        this.selfUrl = selfUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getReviewsUrl() {
        return reviewsUrl;
    }
    public void setReviewsUrl(String reviewsUrl) {
        this.reviewsUrl = reviewsUrl;
    }
    public Long getReviewCount() {
        return reviewCount;
    }
    public void setReviewCount(Long reviewCount) {
        this.reviewCount = reviewCount;
    }
    public CountryDto getCountry() {
        return country;
    }
    public void setCountry(CountryDto country) {
        this.country = country;
    }
    public CityDto getCity() {
        return city;
    }
    public void setCity(CityDto city) {
        this.city = city;
    }
    public UserDto getUser() {
        return user;
    }
    public void setUser(UserDto user) {
        this.user= user;
    }
    public CategoryDto getCategory() {
        return category;
    }
    public void setCategory(CategoryDto category) {
        this.category = category;
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
}
