package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.persistence.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    public ImageServiceImpl(ImageDao imageDao){
        this.imageDao = imageDao;
    }

    @Override
    public ImageModel create (ImageModel imageModel){
        return imageDao.create(imageModel);
    }

    @Override
    public boolean update(long imageId, ImageModel imageModel){
        return imageDao.update(imageId, imageModel);
    }

    @Override
    public boolean delete (long imageId){
        return imageDao.delete(imageId);
    }

    @Override
    public List<ImageModel> listAll() {
        return imageDao.listAll();
    }

    @Override
    public Optional<ImageModel> getById (long imageId){
        return imageDao.getById(imageId);
    }
}