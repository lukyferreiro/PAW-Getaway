package ar.edu.itba.getaway.webapp.dto.request;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewExperienceDto {

    @NotEmpty(message = "NotEmpty")
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"·#$%&=:¿?!¡/.-]*$", message = "Pattern.experienceForm.experienceName")
    private String name;

    @NotNull(message = "NotNull")
    private Long category;

    @NotEmpty(message = "NotEmpty")
    private String country;

    @NotEmpty(message = "NotEmpty")
    private String city;

    @NotEmpty(message = "NotEmpty")
    @Size(min = 5, max = 100, message = "Size.experienceForm.experienceAddress")
    @Pattern(regexp = "^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"·#$%&=:¿?!¡/.-]*$", message = "Pattern.experienceForm.experienceAddress")
    private String address;

    @Size(max = 7, message = "Size.experienceForm.experiencePrice")
    @Pattern(regexp = "^((0|([1-9][0-9]*))(\\.[0-9]{1,2})?)?$", message = "Pattern.experienceForm.experiencePrice")
    private String price;

    // https://regexr.com/39nr7
    @Size(max = 500, message = "Size.experienceForm.experienceUrl")
    @Pattern(regexp = "^([(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*))?$", message = "Pattern.experienceForm.experienceUrl")
    private String url;

    @NotEmpty(message = "NotEmpty")
    @Size(max = 255, message = "Size.experienceForm.experienceMail")
    @Pattern(regexp = "^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$", message = "Pattern.experienceForm.experienceMail")
    private String mail;

    @Size(max = 500, message = "Size.experienceForm.experienceInfo")
    @Pattern(regexp = "^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\";$%#&=:¿?!¡\\n\\s\\t/.-])*$", message = "Pattern.experienceForm.experienceInfo")
    private String description;

    public NewExperienceDto() {
        // Used by Jersey
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public void increaseExperienceCategory(){
        if(this.category != null){
            this.category++;
        }
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
