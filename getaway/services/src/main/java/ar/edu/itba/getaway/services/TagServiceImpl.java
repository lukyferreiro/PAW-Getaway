package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.TagModel;
import ar.edu.itba.getaway.persistence.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao){
        this.tagDao = tagDao;
    }

    @Override
    public TagModel create (TagModel tagModel){
        return tagDao.create(tagModel);
    }

    @Override
    public boolean update(long tagId, TagModel tagModel){
        return tagDao.update(tagId, tagModel);
    }

    @Override
    public boolean delete (long tagId){
        return tagDao.delete(tagId);
    }

    @Override
    public List<TagModel> listAll() {
        return tagDao.listAll();
    }

    @Override
    public Optional<TagModel> getById (long tagId){
        return tagDao.getById(tagId);
    }
}
