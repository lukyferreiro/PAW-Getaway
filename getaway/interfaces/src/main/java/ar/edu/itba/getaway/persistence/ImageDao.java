package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.exceptions.DuplicateImageException;
import ar.edu.itba.getaway.models.ImageExperienceModel;
import ar.edu.itba.getaway.models.ImageModel;

import java.util.List;
import java.util.Optional;

public interface ImageDao {
    ImageModel createImg (byte[] image) throws DuplicateImageException;
    boolean updateImg (byte[] image, long imageId);
    boolean deleteImg (long imageId);
    List<ImageModel> listAllImg ();
    Optional<ImageModel> getImgById (long imageId);
    ImageExperienceModel createExperienceImg (byte[] image, long experienceId, boolean isCover) throws DuplicateImageException;
    List<ImageExperienceModel> listAllExperienceImg ();
    Optional<ImageModel> getImgByExperienceId (long experienceId);
}
