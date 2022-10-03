package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ImageExperienceModel;
import ar.edu.itba.getaway.models.ImageModel;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    ImageModel createImg (byte[] image);
    boolean updateImg (byte[] image, long imageId);
    boolean deleteImg (long imageId);
    Optional<ImageModel> getImgById (long imageId);
    ImageExperienceModel createExperienceImg (byte[] image, long experienceId, boolean isCover);
    Optional<ImageModel> getImgByExperienceId (long experienceId);
}
