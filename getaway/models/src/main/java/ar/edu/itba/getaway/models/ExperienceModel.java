package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "experiences")
public class ExperienceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "experiences_experienceId_seq")
    @SequenceGenerator(sequenceName = "experiences_experienceId_seq", name = "experiences_experienceId_seq", allocationSize = 1)
    @Column(name = "experienceId")
    private Long experienceId;
    @Column(name = "experienceName", nullable = false, unique = true)
    private String experienceName;
    @Column(name = "price", nullable = true)
    private Double price;
    @Column(name = "address", nullable = false, unique = true)
    private String address;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "siteUrl", nullable = true)
    private String siteUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityId", unique = true)
    private CityModel city;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private CategoryModel category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserModel user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imgId")
    private ImageExperienceModel imageExperience;

    /* default */
    protected ExperienceModel() {
        // Just for Hibernate
    }

    public ExperienceModel(String experienceName, String address, String description, String email, String siteUrl, Double price, CityModel city, CategoryModel category, UserModel user, ImageExperienceModel imageExperience) {
        this.experienceName = experienceName;
        this.address = address;
        this.description = description;
        this.siteUrl = siteUrl;
        this.email = email;
        this.category = category;
        this.price = price;
        this.city = city;
        this.user = user;
        this.imageExperience = imageExperience;
    }

    public ExperienceModel(Long experienceId, String experienceName, String address, String description, String email, String siteUrl, Double price, CityModel city, CategoryModel category, UserModel user, ImageExperienceModel imageExperience) {
        this.experienceId = experienceId;
        this.experienceName = experienceName;
        this.address = address;
        this.description = description;
        this.siteUrl = siteUrl;
        this.email = email;
        this.category = category;
        this.price = price;
        this.city = city;
        this.user = user;
        this.imageExperience = imageExperience;
    }

    public Long getExperienceId() {
        return experienceId;
    }
    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
    }
    public String getExperienceName() {
        return experienceName;
    }
    public void setExperienceName(String experienceName) {
        this.experienceName = experienceName;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
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
    public CityModel getCity() {
        return city;
    }
    public void setCity(CityModel city) {
        this.city = city;
    }
    public CategoryModel getCategory() {
        return category;
    }
    public void setCategory(CategoryModel category) {
        this.category = category;
    }
    public UserModel getUser() {
        return user;
    }
    public void setUser(UserModel user) {
        this.user = user;
    }
    public ImageExperienceModel getImageExperience() {
        return imageExperience;
    }
    public void setImageExperience(ImageExperienceModel imageExperience) {
        this.imageExperience = imageExperience;
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
                this.address.equals(other.address) && this.city.equals(other.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experienceId);
    }
}
