package ar.edu.itba.getaway.webapp.forms;

import ar.edu.itba.getaway.models.ExperienceCategory;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class  ExperienceForm {

    @NotEmpty
    @Size(min = 3, max = 255)
    @Pattern(regexp = "^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'·#$%&=:¿?!¡/.-]*$")
    private String activityName;

    @NotNull
    private Integer activityCategory;

    @NotEmpty
    private String activityCountry;

    @NotEmpty
    private String activityCity;

    @NotEmpty
    @Size(min = 5, max = 200)
    @Pattern(regexp = "^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,'_.-]*$")
    private String activityAddress;

    @Pattern(regexp = "^((0|([1-9][0-9]*))(\\.[0-9]{1,2})?)?$")
    private String activityPrice;

    // https://regexr.com/39nr7
    @Pattern(regexp = "^([(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*))?$")
    private String activityUrl;

    @NotEmpty
    @Pattern(regexp = "^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$")
    private String activityMail;

    private MultipartFile activityImg;

    @Size(max = 500)
    @Pattern(regexp = "^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,';$%#&=:¿?!¡\\n\\s\\t/.-])*$")
    private String activityInfo;


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
    public Integer getActivityCategory() {
        return activityCategory;
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
    public String getActivityPrice() {
        return activityPrice;
    }
    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }
    public void setActivityCategory(Integer activityCategory) {
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
    public void setActivityCountry(String activityCountry) {
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
