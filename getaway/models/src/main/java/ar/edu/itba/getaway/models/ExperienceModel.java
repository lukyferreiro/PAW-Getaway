package ar.edu.itba.getaway.models;

import java.util.Objects;

public class ExperienceModel {
    private final long experienceId;
    private String experienceName, address, description, siteUrl, categoryName;
    private Double price;
    private long cityId, categoryId;
    private final long userId;

    public ExperienceModel(long experienceId, String experienceName, String address, String description, String siteUrl, Double price, long cityId, long categoryId, long userId) {
        this.experienceId = experienceId;
        this.experienceName = experienceName;
        this.address = address;
        this.description = description;
        this.siteUrl = siteUrl;
        this.categoryName = ExperienceCategory.values()[(int) (categoryId - 1)].name();
        this.price = price;
        this.cityId = cityId;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public long getId() {
        return experienceId;
    }
    public String getName() {
        return experienceName;
    }
    public void setName(String name) {
        this.experienceName = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getCityId() {
        return cityId;
    }
    public void setCityId(long cityId) {
        this.cityId = cityId;
    }
    public long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public long getUserId() {
        return userId;
    }
    public String getSiteUrl() {
        return siteUrl;
    }
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExperienceModel)) return false;
        ExperienceModel that = (ExperienceModel) o;
        return experienceId == that.experienceId && Double.compare(that.price, price) == 0 && cityId == that.cityId && categoryId == that.categoryId && userId == that.userId && Objects.equals(experienceName, that.experienceName) && Objects.equals(address, that.address) && Objects.equals(description, that.description) && Objects.equals(siteUrl, that.siteUrl) && Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experienceId, experienceName, address, description, siteUrl, categoryName, price, cityId, categoryId, userId);
    }
}
