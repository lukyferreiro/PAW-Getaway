package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.persistence.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<CategoryModel> listAll() {
        return categoryDao.listAll();
    }

    @Override
    public Optional<CategoryModel> getById(long categoryId) {
        return categoryDao.getById(categoryId);
    }

    @Override
    public Optional<CategoryModel> getByName(String categoryName) {
        return categoryDao.getByName(categoryName);
    }
}