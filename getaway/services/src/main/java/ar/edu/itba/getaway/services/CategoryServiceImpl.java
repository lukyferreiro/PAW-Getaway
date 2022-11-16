package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.interfaces.persistence.CategoryDao;
import ar.edu.itba.getaway.interfaces.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public List<CategoryModel> listAllCategories() {
        LOGGER.debug("Retrieving all categories");
        return categoryDao.listAllCategories();
    }

    @Override
    public Optional<CategoryModel> getCategoryById(long categoryId) {
        LOGGER.debug("Retrieving category with id {}", categoryId);
        return categoryDao.getCategoryById(categoryId);
    }

    @Override
    public Optional<CategoryModel> getCategoryByName(String categoryName) {
        LOGGER.debug("Retrieving category with name {}", categoryName);
        return categoryDao.getCategoryByName(categoryName);
    }

}