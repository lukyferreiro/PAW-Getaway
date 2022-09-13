package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.TagModel;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<TagModel> listAll ();
    Optional<TagModel> getById (long tagId);
}
