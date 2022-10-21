//package ar.edu.itba.getaway.models;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Table(name = "imagesExperiences")
//public class ImageExperienceModel {
//
//    @Id
//    @Column(name = "imgId")
//    private Long imgId;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "imgId")
//    private ImageModel image;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "experienceId")
//    private ExperienceModel experience;
//
//    @Column(name = "isCover", nullable = false)
//    private boolean isCover;
//
//    /* default */
//    protected ImageExperienceModel() {
//        // Just for Hibernate
//    }
//
//    public ImageExperienceModel(ImageModel image, ExperienceModel experience, boolean isCover ) {
//        this.image = image;
//        this.experience = experience;
//        this.isCover = isCover;
//    }
//
//    public Long getImgId() {
//        return imgId;
//    }
//    public void setImgId(Long imgId) {
//        this.imgId = imgId;
//    }
//    public ImageModel getImage() {
//        return image;
//    }
//    public void setImage(ImageModel image) {
//        this.image = image;
//    }
//    public ExperienceModel getExperience() {
//        return experience;
//    }
//    public void setExperience(ExperienceModel experience) {
//        this.experience = experience;
//    }
//    public boolean isCover() {
//        return isCover;
//    }
//    public void setCover(boolean cover) {
//        isCover = cover;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o){
//            return true;
//        }
//        if (!(o instanceof ImageExperienceModel)){
//            return false;
//        }
//        ImageExperienceModel image = (ImageExperienceModel) o;
//        return this.image.equals(image.image);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(image);
//    }
//
//}