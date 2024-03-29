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

    @Column(name = "imgMimeType", nullable = true)
    private String mimeType;

    /* default */
    protected ImageModel() {
        // Just for Hibernate
    }

    public ImageModel(byte[] image, String mimeType) {
        this.image = image;
        this.mimeType = mimeType;
    }

    public ImageModel(long imageId, byte[] image, String mimeType) {
        this.image = image;
        this.imageId = imageId;
        this.mimeType = mimeType;
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
    public String getMimeType() {
        return mimeType;
    }
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageModel)) {
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