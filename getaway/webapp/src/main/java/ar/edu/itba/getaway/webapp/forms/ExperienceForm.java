package ar.edu.itba.getaway.webapp.forms;

import ar.edu.itba.getaway.models.ExperienceCategory;
import ar.edu.itba.getaway.services.CategoryService;
import ar.edu.itba.getaway.services.CityService;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class ExperienceForm {

    @Size(min=3, max=255)
    @Pattern(regexp = "^[A-Za-z0-9 ,_.-]+$")
    @NotEmpty
    private String activityName;

    @NotEmpty
    private String activityCategory;

    @NotEmpty
    private String activityCountry;

    @NotEmpty
    private String activityCity;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9 ,_.-]+$")
    @Size(min=5)
    private String activityAddress;

    @NotEmpty
    @Pattern(regexp = "^(0|([1-9][0-9]*))(\\.[0-9]{1,2})?$")
    private String activityPrice;

    @NotEmpty
    @URL
    private String activityUrl;

    @NotEmpty
    @Pattern(regexp = "^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$")
    private String activityMail;

    private MultipartFile activityImg;

    @NotEmpty
    @Size(min=10)
    private String activityInfo;

    private List<String> activityTags;

//    @Autowired
//    private CityService cityService;
//    @Autowired
//    private CategoryService categoryService;

    public String getActivityAddress() {
        return activityAddress;
    }
    public String getActivityCity() {
        return activityCity;
    }
    public String getActivityCountry() {
        return activityCountry;
    }
    public String getActivityUrl() {
        return activityUrl;
    }
    public String getActivityCategory() {
        return activityCategory;
    }
    public long getActivityCategoryId() {
        for (int i = 0; i < ExperienceCategory.values().length ; i++){
            if(ExperienceCategory.values()[i].getName().equals(activityCategory)){
                return i ;
            }
        }
        return -1;
    }
    public MultipartFile getActivityImg() {
        return activityImg;
    }
    public String getActivityInfo() {
        return activityInfo;
    }
    public String getActivityMail() {
        return activityMail;
    }
    public String getActivityName() {
        return activityName;
    }
    public List<String> getActivityTags() {
        return activityTags;
    }
    public String getActivityPrice() {
        return activityPrice;
    }
    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }
    public void setActivityCategory(String activityCategory) {
        this.activityCategory = activityCategory;
    }
    public void setActivityImg(MultipartFile activityImg) {
        this.activityImg = activityImg;
    }
    public void setActivityInfo(String activityInfo) {
        this.activityInfo = activityInfo;
    }
    public void setActivityMail(String activityMail) {
        this.activityMail = activityMail;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public void setActivityTags(List<String> activityTags) {
        this.activityTags = activityTags;
    }
    public void setActivityCountry(String activityCountry){
        this.activityCountry = activityCountry;
    }
    public void setActivityCity(String activityCity) {
        this.activityCity = activityCity;
    }
    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }
    public void setActivityPrice(String activityPrice) {
        this.activityPrice = activityPrice;
    }
}
