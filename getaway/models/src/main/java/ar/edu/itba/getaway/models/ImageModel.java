package ar.edu.itba.getaway.models;

import org.springframework.web.multipart.MultipartFile;

public class ImageModel {
    private final long imageId;
    private final MultipartFile image;

    public ImageModel(long imageId, MultipartFile image ) {
        this.imageId = imageId;
        this.image = image;
    }

    public long getId() {
        return imageId;
    }

    public MultipartFile getImage() {
        return image;
    }
}