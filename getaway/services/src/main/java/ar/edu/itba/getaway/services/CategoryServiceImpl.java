package Interfaces.Necessary.Category;

import Models.Necessary.CategoryModel;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl {
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public CategoryModel create (CategoryModel categoryModel){
        return categoryDao.create(categoryModel);
    }

    @Override
    public boolean update(long categoryId, CategoryModel categoryModel){
        return categoryDao.update(categoryId, categoryModel);
    }

    @Override
    public boolean delete (long categoryId){
        return categoryDao.delete(categoryId);
    }

    @Override
    public List<CategoryModel> list() {
        return categoryDao.list();
    }

    @Override
    public Optional<CategoryModel> getById (long categoryId){
        return categoryDao.getByID(categoryId);
    }
}