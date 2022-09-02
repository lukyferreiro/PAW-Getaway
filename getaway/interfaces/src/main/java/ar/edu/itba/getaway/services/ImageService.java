package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ImageModel;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    ImageModel create (ImageModel imageModel);
    boolean update (long imageId, ImageModel imageModel);
    boolean delete (long imageId);
    List<ImageModel> listAll();
    Optional<ImageModel> getById (long imageId);
}
