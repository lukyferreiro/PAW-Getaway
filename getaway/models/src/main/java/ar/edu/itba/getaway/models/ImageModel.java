package ar.edu.itba.getaway.models;

public class ImageModel {
    private final long imageId;
    private final MultipartFile image;

    public ImageModel(long imageId, MultipartFile image ) {
        this.imageId = imageId;
    }

    public long getId() {
        return imageId;
    }

    public MultipartFile getImage() {
        return image;
    }
}