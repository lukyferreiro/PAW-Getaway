package ar.edu.itba.getaway.persistence.config;


import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;
import ar.edu.itba.getaway.persistence.CategoryDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:category-dao-test.sql")
public class CategoryDaoTest {
    /** Data for tests **/

    private final static CategoryModel CATEGORY_1 = new CategoryModel(1, "Aventura");
    private final static CategoryModel CATEGORY_2 = new CategoryModel(2, "Gastronomia");
    private final static CategoryModel CATEGORY_3 = new CategoryModel(3, "Hoteleria");
    private final static CategoryModel CATEGORY_4 = new CategoryModel(4, "Relax");
    private final static CategoryModel CATEGORY_5 = new CategoryModel(5, "Vida_nocturna");
    private final static CategoryModel CATEGORY_6 = new CategoryModel(6, "Historico");

    private final static List<CategoryModel> CATEGORIES = new ArrayList<>(Arrays.asList(CATEGORY_1, CATEGORY_2, CATEGORY_3, CATEGORY_4, CATEGORY_5, CATEGORY_6));

    /****/

    @Autowired
    private DataSource ds;

    @Autowired
    private CategoryDao categoryDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testListAll() {
        List<CategoryModel> categoryModels = categoryDao.listAll();
        assertEquals(CATEGORIES.size(), categoryModels.size());
        assertTrue(categoryModels.containsAll(CATEGORIES));
    }

    @Test
    public void testGetById() {
        Optional<CategoryModel> categoryModel = categoryDao.getById(2);
        assertTrue(categoryModel.isPresent());
        assertEquals(CATEGORY_2, categoryModel.get());
    }

    @Test
    public void testGetByName() {
        Optional<CategoryModel> categoryModel = categoryDao.getByName("Vida_nocturna");
        assertTrue(categoryModel.isPresent());
        assertEquals(CATEGORY_5, categoryModel.get());
    }
}
