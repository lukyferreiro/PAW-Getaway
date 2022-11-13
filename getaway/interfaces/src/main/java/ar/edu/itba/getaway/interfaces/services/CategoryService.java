package ar.edu.itba.getaway.interfaces.services;

import ar.edu.itba.getaway.models.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryModel> listAllCategories();

    Optional<CategoryModel> getCategoryById(long categoryId);

    Optional<CategoryModel> getCategoryByName(String categoryName);

    int getCategoriesCount();
}
