package ar.edu.itba.getaway.models;

public class ImageModel {
    private final Long imageId;
    private final byte[] image;

    public ImageModel(Long imageId, byte[] image ) {
        this.imageId = imageId;
        this.image = image;
    }

    public Long getImageId() {
        return imageId;
    }
    public byte[] getImage() {
        return image;
    }
}