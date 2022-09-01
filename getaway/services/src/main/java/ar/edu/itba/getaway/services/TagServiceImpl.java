package Interfaces.Necessary.Tag;

import Models.Necessary.TagModel;

import java.util.List;
import java.util.Optional;

public class TagServiceImpl {
    @Autowired
    private TagDao tagDao;

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
    public List<TagModel> list() {
        return tagDao.list();
    }

    @Override
    public Optional<TagModel> getById (long tagId){
        return tagDao.getByID(tagId);
    }
}
