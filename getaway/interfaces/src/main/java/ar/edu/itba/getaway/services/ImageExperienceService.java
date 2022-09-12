package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ImageExperienceModel;

import java.util.List;
import java.util.Optional;

public interface ImageExperienceService {
    ImageExperienceModel create (long imageId, long experienceId, boolean isCover);
    boolean update (long imageId, ImageExperienceModel imageExperienceModel);
    boolean delete (long imageId);
    List<ImageExperienceModel> listAll();
    Optional<ImageExperienceModel> getById (long imageId);

}