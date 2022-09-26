package ar.edu.itba.getaway.models;

public class ExperienceModel {
    private final long experienceId;
    private String experienceName, address, description, siteUrl, categoryName;
    private Double price;
    private long cityId, categoryId;
    private final long userId;
    private boolean hasImage;

    public ExperienceModel(long experienceId, String experienceName, String address, String description, String siteUrl, Double price, long cityId, long categoryId, long userId, boolean hasImage) {
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
        this.hasImage = hasImage;
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

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof ExperienceModel)){
            return false;
        }
        ExperienceModel other = (ExperienceModel) o;
        return this.experienceId == other.experienceId && this.experienceName.equals(other.experienceName) &&
                this.address.equals(other.address) && this.cityId == other.cityId ;
    }
}
