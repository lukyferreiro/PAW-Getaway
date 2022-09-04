package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.persistence.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao){
        this.categoryDao = categoryDao;
    }

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
    public List<CategoryModel> listAll() {
        return categoryDao.listAll();
    }

    @Override
    public Optional<CategoryModel> getById (long categoryId){
        return categoryDao.getById(categoryId);
    }

    @Override
    public Optional<CategoryModel> getId(String category) {
        return categoryDao.getId(category);
    }
}