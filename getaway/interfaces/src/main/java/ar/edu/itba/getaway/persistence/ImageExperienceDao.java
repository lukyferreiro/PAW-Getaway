package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ImageExperienceModel;

import java.util.List;
import java.util.Optional;

public interface ImageExperienceDao {
    ImageExperienceModel create (ImageExperienceModel imageExperienceModel);
    boolean update (long imageId, ImageExperienceModel imageExperienceModel);
    boolean delete (long imageId);
    List<ImageExperienceModel> listAll();
    Optional<ImageExperienceModel> getById (long imageId);
}
