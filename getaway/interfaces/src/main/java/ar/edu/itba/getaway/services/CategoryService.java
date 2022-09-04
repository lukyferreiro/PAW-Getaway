package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryModel create (CategoryModel categoryModel);
    boolean update (long categoryId, CategoryModel categoryModel);
    boolean delete (long categoryId);
    List<CategoryModel> listAll();
    Optional<CategoryModel> getById (long categoryId);
    Optional<CategoryModel> getId(String category);
}
