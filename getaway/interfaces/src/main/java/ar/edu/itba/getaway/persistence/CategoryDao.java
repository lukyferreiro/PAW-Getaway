package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    CategoryModel create (CategoryModel categoryModel);
    boolean update (long categoryId, CategoryModel categoryModel);
    boolean delete (long categoryId);
    List<CategoryModel> listAll();
    Optional<CategoryModel> getById (long categoryId);
}
