package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.ImageModel;

import java.util.Optional;

public interface ImageService {
    ImageModel createImg(byte[] image, String mimeType);

    void updateImg(byte[] image, String mimeType, ImageModel imageModel);

    void deleteImg(ImageModel imageModel);

    Optional<ImageModel> getImgById(long imageId);
}
