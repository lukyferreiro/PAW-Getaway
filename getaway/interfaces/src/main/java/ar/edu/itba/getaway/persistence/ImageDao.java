package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ImageExperienceModel;
import ar.edu.itba.getaway.models.ImageModel;

import java.util.Optional;

public interface ImageDao {
    ImageModel createImg (byte[] image);
    boolean updateImg (byte[] image, long imageId);
    boolean deleteImg (long imageId);
    Optional<ImageModel> getImgById (long imageId);
    ImageExperienceModel createExperienceImg (byte[] image, long experienceId, boolean isCover);
    Optional<ImageModel> getImgByExperienceId (long experienceId);
}
