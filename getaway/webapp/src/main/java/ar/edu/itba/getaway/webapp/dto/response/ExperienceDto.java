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
    private String contactEmail;
    private Double price;
    private Long avgScore;
    private Integer views;
    private String selfUrl;
    private String imageUrl;
    private String reviewsUrl;
    private Long reviewsCount;
    private CountryDto countryUrl;
    private CityDto city;
    private UserDto user;
    private CategoryModel category;
    private boolean isFav;

    public static Collection<Collection<ExperienceDto>> mapExperienceListToDto (List<List<ExperienceModel>> experiences, UriInfo uriInfo) {
        Collection<Collection<ExperienceDto>> toReturnList = new ArrayList<>();
        for (Collection<ExperienceModel> subList: experiences) {
            toReturnList.add(mapExperienceToDto(subList, uriInfo));
        }
        return toReturnList;
    }

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
        this.contactEmail = experience.getEmail();
        this.price = experience.getPrice();
        this.avgScore = experience.getAverageScore();
        this.views = experience.getViews();
        this.selfUrl = uriBuilder.build().toString();
        this.imageUrl = uriBuilder.clone().path("image").build().toString();    // /experience/{id}/image
        this.reviewsUrl = uriBuilder.clone().path("reviews").build().toString();    // /experience/{id}/reviews
        this.reviewsCount = experience.getReviewCount();
        this.countryUrl = new CountryDto(experience.getCity().getCountry(), uriInfo);
        this.city = new CityDto(experience.getCity());
        this.user = new UserDto(experience.getUser(), uriInfo);
        this.category = experience.getCategory();
        this.isFav = experience.getIsFav();
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
    public String getContactEmail() {
        return contactEmail;
    }
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Long getAvgScore() {
        return avgScore;
    }
    public void setAvgScore(Long avgScore) {
        this.avgScore = avgScore;
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
    public Long getReviewsCount() {
        return reviewsCount;
    }
    public void setReviewsCount(Long reviewsCount) {
        this.reviewsCount = reviewsCount;
    }
    public CountryDto getCountryUrl() {
        return countryUrl;
    }
    public void setCountryUrl(CountryDto countryUrl) {
        this.countryUrl = countryUrl;
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
    public CategoryModel getCategory() {
        return category;
    }
    public void setCategory(CategoryModel category) {
        this.category = category;
    }
    public boolean isFav() {
        return isFav;
    }
    public void setFav(boolean fav) {
        isFav = fav;
    }
}
