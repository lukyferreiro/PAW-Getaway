package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryModel> listAll ();
    Optional<CategoryModel> getById (long categoryId);
    Optional<CategoryModel> getByName (String categoryName);
}
