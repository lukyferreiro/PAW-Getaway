package ar.edu.itba.getaway.webapp.dto.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class NewExperienceDto {

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    @Length(min = 3, max = 50, message = "Size.experienceForm.experienceName")
    @Pattern(regexp = "^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"·#$%&=:¿?!¡/.-]*$", message = "Pattern.experienceForm.experienceName")
    private String name;

    @NotNull(message = "NotNull")
    private Long category;

    @NotNull(message = "NotNull")
    private Long country;

    @NotNull(message = "NotNull")
    private Long city;

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    @Length(min = 5, max = 100, message = "Size.experienceForm.experienceAddress")
    @Pattern(regexp = "^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"·#$%&=:¿?!¡/.-]*$", message = "Pattern.experienceForm.experienceAddress")
    private String address;

    @Range(max = 9999999, message = "Size.experienceForm.experiencePrice")
    private Double price;

    // https://regexr.com/39nr7
    @Length(max = 500, message = "Size.experienceForm.experienceUrl")
    @Pattern(regexp = "^([(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*))?$", message = "Pattern.experienceForm.experienceUrl")
    private String url;

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    @Length(max = 255, message = "Size.experienceForm.experienceMail")
    @Pattern(regexp = "^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$", message = "Pattern.experienceForm.experienceMail")
    private String mail;

    @Length(max = 500, message = "Size.experienceForm.experienceInfo")
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

    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }

    public long getCity() {
        return city;
    }

    public void setCity(long city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
