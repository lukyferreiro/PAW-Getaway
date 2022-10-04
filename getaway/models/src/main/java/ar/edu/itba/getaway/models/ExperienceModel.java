package ar.edu.itba.getaway.models;

public class ExperienceModel {
    private final Long experienceId;
    private String experienceName, address, description, siteUrl, categoryName, email;
    private Double price;
    private Long cityId, categoryId;
    private final Long userId;
    private Long imageExperienceId;
    private boolean hasImage;

    public ExperienceModel(Long experienceId, String experienceName, String address, String description, String email, String siteUrl, Double price, Long cityId, Long categoryId, Long userId, Long imageExperienceId, boolean hasImage) {
        this.experienceId = experienceId;
        this.experienceName = experienceName;
        this.address = address;
        this.description = description;
        this.siteUrl = siteUrl;
        this.email = email;
        this.categoryName = ExperienceCategory.values()[(int) (categoryId - 1)].name();
        this.price = price;
        this.cityId = cityId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.imageExperienceId = imageExperienceId;
        this.hasImage = hasImage;
    }

    public Long getExperienceId() {
        return experienceId;
    }
    public String getExperienceName() {
        return experienceName;
    }
    public void setExperienceName(String experienceName) {
        this.experienceName = experienceName;
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
    public Long getCityId() {
        return cityId;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public Long getUserId() {
        return userId;
    }
    public Long getImageExperienceId() {
        return imageExperienceId;
    }
    public void setImageExperienceId(Long imageExperienceId) {
        this.imageExperienceId = imageExperienceId;
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
        return this.experienceId.equals(other.experienceId) && this.experienceName.equals(other.experienceName) &&
                this.address.equals(other.address) && this.cityId.equals(other.cityId);
    }
}
