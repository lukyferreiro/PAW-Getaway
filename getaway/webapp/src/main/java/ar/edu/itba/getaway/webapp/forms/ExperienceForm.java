package ar.edu.itba.getaway.webapp.forms;

import ar.edu.itba.getaway.models.ExperienceCategory;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class ExperienceForm {

    private String activityName;
    private String activityCategory;

    private String activityCity;
    private String activityAddress;

    private long activityPrice;
    private String activityUrl;
    @NotEmpty
    @Email
    private String activityMail;
    private String activityImg;
    private String activityInfo;
    private List<String> activityTags;

    public String getActivityAddress() {
        return activityAddress;
    }

    public String getActivityCity() {
        return activityCity;
    }
    public String getActivityUrl() {
        return activityUrl;
    }

    public String getActivityCategory() {
        return activityCategory;
    }

    public long getActivityCategoryId() {

        for (int i = 0; i< ExperienceCategory.values().length ; i++){
            if(ExperienceCategory.values()[i].getName() == activityCategory){
                return i + 1;
            }
        }

        return -1;
    }

    public String getActivityImg() {
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

    public long getActivityPrice() {
        return activityPrice;
    }

    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }

    public void setActivityCategory(String activityCategory) {
        this.activityCategory = activityCategory;
    }

    public void setActivityImg(String activityImg) {
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

    public void setActivityCity(String activityCity) {
        this.activityCity = activityCity;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public void setActivityPrice(long activityPrice) {
        this.activityPrice = activityPrice;
    }
}
