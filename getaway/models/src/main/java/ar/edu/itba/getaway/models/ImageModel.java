package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "images")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_imgId_seq")
    @SequenceGenerator(sequenceName = "images_imgId_seq", name = "images_imgId_seq", allocationSize = 1)
    @Column(name = "imgId", nullable = false)
    private long imageId;

    @Column(name = "imageObject", nullable = true, length = 30000000)
    @Basic(fetch = FetchType.LAZY, optional = false)
    private byte[] image;

    /* default */
    protected ImageModel() {
        // Just for Hibernate
    }

    public ImageModel(byte[] image ) {
        this.image = image;
    }

    public ImageModel( long imageId, byte[] image) {
        this.image = image;
        this.imageId = imageId;
    }

    public long getImageId() {
        return imageId;
    }
    public void setImageId(long imageId) {
        this.imageId = imageId;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof ImageModel)){
            return false;
        }
        ImageModel image = (ImageModel) o;
        return this.getImageId() == image.getImageId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId);
    }
}