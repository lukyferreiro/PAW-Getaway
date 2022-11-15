package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.interfaces.persistence.CategoryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDaoImpl.class);

    @Override
    public List<CategoryModel> listAllCategories() {
        LOGGER.debug("List all categories");
        return em.createQuery("FROM CategoryModel", CategoryModel.class).getResultList();
    }

    @Override
    public Optional<CategoryModel> getCategoryById(long categoryId) {
        LOGGER.debug("Get category with id {}", categoryId);
        final TypedQuery<CategoryModel> query = em.createQuery("FROM CategoryModel WHERE categoryId = :categoryId", CategoryModel.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<CategoryModel> getCategoryByName(String categoryName) {
        LOGGER.debug("Get category with name {}", categoryName);
        final TypedQuery<CategoryModel> query = em.createQuery("FROM CategoryModel WHERE categoryName = :categoryName", CategoryModel.class);
        query.setParameter("categoryName", categoryName);
        return query.getResultList().stream().findFirst();
    }

//    @Override
//    public int getCategoriesCount() {
//        LOGGER.debug("Get count of categories");
//        return em.createQuery("FROM CategoryModel", CategoryModel.class).getResultList().size();
//    }

}
