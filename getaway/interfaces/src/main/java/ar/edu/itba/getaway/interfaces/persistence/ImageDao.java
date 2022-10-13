package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ImageExperienceModel;
import ar.edu.itba.getaway.models.ImageModel;

import java.util.Optional;

public interface ImageDao {
    ImageModel createImg (byte[] image);
    void updateImg(byte[] image, ImageModel imageModel) ;
    void deleteImg (ImageModel imageModel);
    Optional<ImageModel> getImgById (Long imageId);
//    ImageExperienceModel createExperienceImg (byte[] image, ExperienceModel experience, boolean isCover);
    Optional<ImageModel> getImgByExperience (ExperienceModel experience);
}
