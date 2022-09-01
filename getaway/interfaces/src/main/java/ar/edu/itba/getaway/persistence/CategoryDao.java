package Interfaces.Necessary.Category;

import Models.Necessary.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    CategoryModel create (CategoryModel categoryModel);
    boolean update (long categoryId, CategoryModel categoryModel);
    boolean delete (long categoryId);
    List<CategoryModel> list();
    Optional<CategoryModel> getByID (long categoryId);
}
