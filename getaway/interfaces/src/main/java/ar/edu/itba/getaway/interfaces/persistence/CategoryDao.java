package ar.edu.itba.getaway.interfaces.persistence;

import ar.edu.itba.getaway.models.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    List<CategoryModel> listAllCategories ();
    Optional<CategoryModel> getCategoryById (Long categoryId);
    Optional<CategoryModel> getCategoryByName (String categoryName);
    Integer getCategoriesCount ();
}
