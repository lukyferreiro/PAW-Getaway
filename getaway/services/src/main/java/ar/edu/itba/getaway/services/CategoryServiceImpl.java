package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.persistence.CategoryDao;
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
    public List<CategoryModel> listAll() {
        LOGGER.debug("Retrieving all categories");
        return categoryDao.listAll();
    }

    @Override
    public Optional<CategoryModel> getById(long categoryId) {
        LOGGER.debug("Retrieving category with id {}", categoryId);
        return categoryDao.getById(categoryId);
    }

    @Override
    public Optional<CategoryModel> getByName(String categoryName) {
        LOGGER.debug("Retrieving category with name {}", categoryName);
        return categoryDao.getByName(categoryName);
    }
}