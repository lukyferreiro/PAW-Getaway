package ar.edu.itba.getaway.models;

public class ImageModel {
    private final long imageId;
    private final byte[] image;

    public ImageModel(long imageId, byte[] image ) {
        this.imageId = imageId;
        this.image = image;
    }

    public long getId() {
        return imageId;
    }

    public byte[] getImage() {
        return image;
    }
}