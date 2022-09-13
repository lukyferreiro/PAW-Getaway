package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    List<CategoryModel> listAll ();
    Optional<CategoryModel> getById (long categoryId);
    Optional<CategoryModel> getByName (String categoryName);
}
