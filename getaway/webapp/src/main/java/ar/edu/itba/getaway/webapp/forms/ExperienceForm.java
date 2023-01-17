package ar.edu.itba.getaway.webapp.forms;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ExperienceForm {

    @NotEmpty
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"·#$%&=:¿?!¡/.-]*$")
    private String experienceName;

    @NotNull
    private Long experienceCategory;

    @NotEmpty
    private String experienceCountry;

    @NotEmpty
    private String experienceCity;

    @NotEmpty
    @Size(min = 5, max = 100)
    @Pattern(regexp = "^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"·#$%&=:¿?!¡/.-]*$")
    private String experienceAddress;

    @Size(max = 7)
    @Pattern(regexp = "^((0|([1-9][0-9]*))(\\.[0-9]{1,2})?)?$")
    private String experiencePrice;

    // https://regexr.com/39nr7
    @Size(max = 500)
    @Pattern(regexp = "^([(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*))?$")
    private String experienceUrl;

    @NotEmpty
    @Size(max = 255)
    @Pattern(regexp = "^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$")
    private String experienceMail;

    private MultipartFile experienceImg;

    @Size(max = 500)
    @Pattern(regexp = "^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\";$%#&=:¿?!¡\\n\\s\\t/.-])*$")
    private String experienceInfo;


    public String getExperienceAddress() {
        return experienceAddress;
    }

    public String getExperienceCity() {
        return experienceCity;
    }

    public String getExperienceCountry() {
        return experienceCountry;
    }

    public String getExperienceUrl() {
        return experienceUrl;
    }

    public Long getExperienceCategory() {
        return experienceCategory;
    }

    public MultipartFile getExperienceImg() {
        return experienceImg;
    }

    public String getExperienceInfo() {
        return experienceInfo;
    }

    public String getExperienceMail() {
        return experienceMail;
    }

    public String getExperienceName() {
        return experienceName;
    }

    public String getExperiencePrice() {
        return experiencePrice;
    }

    public void setExperienceAddress(String experienceAddress) {
        this.experienceAddress = experienceAddress;
    }

    public void setExperienceCategory(Long experienceCategory) {
        this.experienceCategory = experienceCategory;
    }

    public void increaseExperienceCategory(){
        if(this.experienceCategory != null){
            this.experienceCategory++;
        }
    }

    public void setExperienceImg(MultipartFile experienceImg) {
        this.experienceImg = experienceImg;
    }

    public void setExperienceInfo(String experienceInfo) {
        this.experienceInfo = experienceInfo;
    }

    public void setExperienceMail(String experienceMail) {
        this.experienceMail = experienceMail;
    }

    public void setExperienceName(String experienceName) {
        this.experienceName = experienceName;
    }

    public void setExperienceCountry(String experienceCountry) {
        this.experienceCountry = experienceCountry;
    }

    public void setExperienceCity(String experienceCity) {
        this.experienceCity = experienceCity;
    }

    public void setExperienceUrl(String experienceUrl) {
        this.experienceUrl = experienceUrl;
    }

    public void setExperiencePrice(String experiencePrice) {
        this.experiencePrice = experiencePrice;
    }

}
