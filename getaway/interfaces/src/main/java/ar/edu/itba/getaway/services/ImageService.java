package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    ImageModel create (byte[] image);
    boolean update (long imageId, ImageModel imageModel);
    boolean delete (long imageId);
    List<ImageModel> listAll();
    Optional<ImageModel> getById (long imageId);
    Optional<ImageModel> getByExperienceId(long experienceId);
}
