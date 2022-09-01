package Interfaces.Necessary.Tag;

import Models.Necessary.TagModel;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    TagModel create (TagModel tagModel);
    boolean update (long tagId, TagModel tagModel);
    boolean delete (long tagId);
    List<TagModel> list();
    Optional<TagModel> getByID (long tagId);
}
