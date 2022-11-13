package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.interfaces.persistence.FavAndViewExperienceDao;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.persistence.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.*;

import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:favandviewexperience-dao-test.sql")
public class FavAndViewExperienceDaoTest {
    /**
     * Data for tests
     **/

    //User data
    private static final RoleModel USER_MODEL = new RoleModel(2L, Roles.USER);
    private static final RoleModel NOT_VERIFIED_MODEL = new RoleModel(4L, Roles.NOT_VERIFIED);
    private static final Collection<RoleModel> DEFAULT_ROLES_MODELS = new ArrayList<>(Arrays.asList(USER_MODEL, NOT_VERIFIED_MODEL));

    private final static ImageModel U_IMAGE_1 = new ImageModel(15L, null);
    private final static ImageModel U_IMAGE_2 = new ImageModel(16L, null);

    private final static UserModel USER_1 = new UserModel(1L, "contra1", "owner", "user", "owner@mail.com", DEFAULT_ROLES_MODELS, U_IMAGE_1);
    private final static UserModel USER_2 = new UserModel(2L, "contra2", "owner2", "user2", "owner2@mail.com", DEFAULT_ROLES_MODELS, U_IMAGE_2);

    //Experience data
    private final static CategoryModel CATEGORY_1 = new CategoryModel(1L, "Aventura");
    private final static CategoryModel CATEGORY_2 = new CategoryModel(2L, "Gastronomia");

    private final static CountryModel COUNTRY_1 = new CountryModel(1L, "Test Country");

    private final static CityModel CITY_1 = new CityModel(1L, COUNTRY_1, "Test City One");
    private final static CityModel CITY_2 = new CityModel(2L, COUNTRY_1, "Test City Two");

    private final ImageModel IMAGE_ADV_1 = new ImageModel(1, null);
    private final ImageModel IMAGE_ADV_2 = new ImageModel(7, null);
    private final ImageModel IMAGE_ADV_3 = new ImageModel(8, null);
    private final ImageModel IMAGE_GAS = new ImageModel(4, null);

    private final ExperienceModel DEFAULT_ADV = new ExperienceModel(1L, "testaventura", "diraventura", null, "mail@aventura1.com", null, 0.0, CITY_1, CATEGORY_1, USER_1, IMAGE_ADV_1, true, 0);
    private final ExperienceModel DEFAULT_GAS = new ExperienceModel(2L, "testgastro", "dirgastro", null, null, null, 1000.0, CITY_1, CATEGORY_2, USER_1, IMAGE_GAS, true, 0);
    private final ExperienceModel DEFAULT_ADV2 = new ExperienceModel(7L, "testaventura2", "diraventura2", null, null, null, 1500.0, CITY_1, CATEGORY_1, USER_2, IMAGE_ADV_2, true, 0);
    private final ExperienceModel DEFAULT_ADV3 = new ExperienceModel(8L, "testaventura3", "diraventura3", null, null, null, 2000.0, CITY_2, CATEGORY_1, USER_2, IMAGE_ADV_3, true, 0);

    /****/

    @Autowired
    private DataSource ds;
    @Autowired
    private FavAndViewExperienceDao favAndViewExperienceDao;
    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Rollback
    public void testAddFav() {
        favAndViewExperienceDao.addFav(USER_1, DEFAULT_GAS);
        em.flush();
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "favuserexperience", String.format("userId = " + USER_1.getUserId()) + " AND experienceid = " + DEFAULT_GAS.getExperienceId()));
    }

    @Test
    @Rollback
    public void testDeleteFav() {
        favAndViewExperienceDao.deleteFav(USER_1, DEFAULT_ADV);
        em.flush();
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "favuserexperience", String.format("userId = " + USER_1.getUserId()) + " AND experienceid = " + DEFAULT_ADV.getExperienceId()));
    }

    @Test
    @Rollback
    public void testAddViewed() {
        favAndViewExperienceDao.addViewed(USER_1, DEFAULT_GAS);
        em.flush();
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "viewed", String.format("userId = " + USER_1.getUserId()) + " AND experienceid = " + DEFAULT_GAS.getExperienceId()));
    }
}
