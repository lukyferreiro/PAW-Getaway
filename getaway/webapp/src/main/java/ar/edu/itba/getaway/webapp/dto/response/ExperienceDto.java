package ar.edu.itba.getaway.webapp.dto.response;


import ar.edu.itba.getaway.models.*;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.stream.Collectors;

public class ExperienceDto {

    private Long id;

    private String name;

    private String description;

    private String url;

    private String address;

    private String contact_email;

    private Double price;

    private CityModel cityModel;

    private CategoryModel categoryModel;

    private Long avg_score;
    private Integer views;
    private ImageModel imageModel;
    private byte[] image;
    private UserModel userModel;
    public static Collection<ExperienceDto> mapExperienceToDto(Collection<ExperienceModel> experiences, UriInfo uriInfo) {
        return experiences.stream().map(exp -> new ExperienceDto(exp, uriInfo)).collect(Collectors.toList());
    }

    public ExperienceDto() {
        // Used by Jersey
    }

    //UriInfo no se pa q se usa
    public ExperienceDto(ExperienceModel experienceModel, UriInfo uriInfo) {
        this.id = experienceModel.getExperienceId();
        this.name = experienceModel.getExperienceName();
        this.price = experienceModel.getPrice();
        this.address = experienceModel.getAddress();
        this.avg_score = experienceModel.getAverageScore();
        this.views = experienceModel.getViews();
        this.cityModel = experienceModel.getCity();
        this.categoryModel = experienceModel.getCategory();
        this.imageModel = experienceModel.getExperienceImage();
        this.description = experienceModel.getDescription();
        this.contact_email = experienceModel.getEmail();
        this.image = experienceModel.getImage();
        this.userModel = experienceModel.getUser();
        this.url = experienceModel.getSiteUrl();
    }

    public static UriBuilder getExperienceUriBuilder(ExperienceModel experience, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone().path("experience").path(String.valueOf(experience.getExperienceId()));
    }

    public Integer getViews() {
        return views;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public CityModel getCityModel() {
        return cityModel;
    }

    public Double getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    public Long getAvg_score() {
        return avg_score;
    }

    public byte[] getImage() {
        return image;
    }

    public ImageModel getImageModel() {
        return imageModel;
    }

    public String getAddress() {
        return address;
    }

    public String getContact_email() {
        return contact_email;
    }

    public String getUrl() {
        return url;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAvg_score(Long avg_score) {
        this.avg_score = avg_score;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setImageModel(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
