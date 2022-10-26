package ar.edu.itba.getaway.models;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;
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

    @OneToOne
    @JoinColumn(name = "imgId")
    private ImageModel experienceImage;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "experienceId")
//    private List<ReviewModel> experienceReviews;

    @Column(name = "observable", nullable = false)
    private Boolean observable;

    @Column(name = "views", nullable = false)
    private Integer views;

    @Formula(value = "(select coalesce(ceiling(avg(reviews.score)),0) from reviews where reviews.experienceId = experienceId)")
    private Long averageScore;

    @Formula(value = "(select count(*) from reviews where reviews.experienceId = experienceId)")
    private Long reviewCount;


    /* default */
    protected ExperienceModel() {
        // Just for Hibernate
    }

    public ExperienceModel(String experienceName, String address, String description, String email, String siteUrl, Double price, CityModel city, CategoryModel category, UserModel user, ImageModel experienceImage, Boolean observable, Integer views) {
        this.experienceName = experienceName;
        this.address = address;
        this.description = description;
        this.siteUrl = siteUrl;
        this.email = email;
        this.category = category;
        this.price = price;
        this.city = city;
        this.user = user;
        this.experienceImage = experienceImage;
        this.observable = observable;
        this.views = views;
    }

    public ExperienceModel(Long experienceId, String experienceName, String address, String description, String email, String siteUrl, Double price, CityModel city, CategoryModel category, UserModel user, ImageModel experienceImage, Boolean observable, Integer views) {
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
        this.experienceImage = experienceImage;
        this.observable = observable;
        this.views = views;
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

    public Boolean getObservable() {
        return observable;
    }

    public void setObservable(Boolean observable) {
        this.observable = observable;
    }

    public Integer getViews() {
        return views;
    }

    public void increaseViews() {
        this.views++ ;
    }

    public ImageModel getExperienceImage() {
        return experienceImage;
    }

    public void setExperienceImage(ImageModel experienceImage) {
        this.experienceImage = experienceImage;
    }

//    public List<ReviewModel> getExperienceReviews(Integer page, Integer pageSize) {
//        Integer fromIndex = (page - 1) * pageSize;
//        Integer toIndex = Math.min((fromIndex + pageSize), experienceReviews.size());
//        return experienceReviews.subList(fromIndex, toIndex);
//    }

    @Transient
    public Long getReviewCount() {
        return reviewCount;
    }

    @Transient
    public Long getAverageScore() {
        return averageScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExperienceModel)) {
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
