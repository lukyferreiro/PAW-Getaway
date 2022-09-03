package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.TagModel;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    TagModel create (TagModel tagModel);
    boolean update (long tagId, TagModel tagModel);
    boolean delete (long tagId);
    List<TagModel> listAll();
    Optional<TagModel> getById (long tagId);
}
