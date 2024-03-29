package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.ImageModel;

import java.util.Optional;

public interface ImageDao {
    ImageModel createImg(byte[] image, String mimeType);

    void updateImg(byte[] image, String mimeType, ImageModel imageModel);

    void deleteImg(ImageModel imageModel);

    Optional<ImageModel> getImgById(long imageId);
}
