package Interfaces.Necessary.Image;

import Models.Necessary.ImageModel;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    ImageModel create (ImageModel imageModel);
    boolean update (long imageId, ImageModel imageModel);
    boolean delete (long imageId);
    List<ImageModel> list();
    Optional<ImageModel> getByID (long imageId);
}
