package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.TagModel;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    List<TagModel> listAll ();
    Optional<TagModel> getById (long tagId);
}
