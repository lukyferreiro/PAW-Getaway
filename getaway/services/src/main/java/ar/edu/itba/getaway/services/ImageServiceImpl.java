package Interfaces.Necessary.Image;

import Models.Necessary.ImageModel;

import java.util.List;
import java.util.Optional;

public class ImageServiceImpl {
    @Autowired
    private ImageDao imageDao;

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
    public List<ImageModel> list() {
        return imageDao.list();
    }

    @Override
    public Optional<ImageModel> getById (long imageId){
        return imageDao.getByID(imageId);
    }
}
