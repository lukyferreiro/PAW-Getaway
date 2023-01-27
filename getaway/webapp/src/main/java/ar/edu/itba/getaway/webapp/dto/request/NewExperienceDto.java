package ar.edu.itba.getaway.webapp.dto.request;

import ar.edu.itba.getaway.webapp.constraints.ImageTypeConstraint;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewExperienceDto {

    @NotEmpty(message = "...")
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"·#$%&=:¿?!¡/.-]*$", message = "...")
    private String name;

    @NotNull(message = "...")
    private Long category;

    @NotEmpty(message = "...")
    private String country;

    @NotEmpty(message = "...")
    private String city;

    @NotEmpty(message = "...")
    @Size(min = 5, max = 100, message = "...")
    @Pattern(regexp = "^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\"·#$%&=:¿?!¡/.-]*$", message = "...")
    private String address;

    @Size(max = 7, message = "...")
    @Pattern(regexp = "^((0|([1-9][0-9]*))(\\.[0-9]{1,2})?)?$", message = "...")
    private String price;

    // https://regexr.com/39nr7
    @Size(max = 500, message = "...")
    @Pattern(regexp = "^([(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*))?$", message = "...")
    private String url;

    @NotEmpty(message = "")
    @Size(max = 255, message = "...")
    @Pattern(regexp = "^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$", message = "...")
    private String mail;

    @Size(max = 500, message = "...")
    @Pattern(regexp = "^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°\";$%#&=:¿?!¡\\n\\s\\t/.-])*$", message = "...")
    private String description;

    @ImageTypeConstraint(contentType = {"image/png", "image/jpeg", "image/jpg"}, message = "experienceForm.validation.imageFormat")
    @FormDataParam("images")
    private FormDataBodyPart image;

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

    public FormDataBodyPart getImage() {
        return image;
    }

    public void setImage(FormDataBodyPart image) {
        this.image = image;
    }
}
