package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.persistence.ExperienceDao;
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
import static org.junit.Assert.*;

import javax.sql.DataSource;
import java.util.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:experience-dao-test.sql")
public class ExperienceDaoTest {
    /** Data for tests **/

    //User data
    private static final RoleModel USER_MODEL = new RoleModel(2L, Roles.USER);
    private static final RoleModel NOT_VERIFIED_MODEL = new RoleModel(4L, Roles.NOT_VERIFIED);
    private static final Collection<RoleModel> DEFAULT_ROLES_MODELS = new ArrayList<>(Arrays.asList(USER_MODEL, NOT_VERIFIED_MODEL));

    private final static UserModel USER_1 = new UserModel(1L, "contra1", "owner", "user", "owner@mail.com", DEFAULT_ROLES_MODELS, null);
    private final static UserModel USER_2 = new UserModel(2L, "contra2", "owner2", "user2", "owner2@mail.com", DEFAULT_ROLES_MODELS, null);

    //Experience data
    private final static CategoryModel CATEGORY_1 = new CategoryModel(1L, "Aventura");

    private final static CountryModel COUNTRY_1 = new CountryModel(1L, "Test Country");

    private final static CityModel CITY_1 = new CityModel(1L, COUNTRY_1, "Test City One");
    private final static CityModel CITY_2 = new CityModel(2L, COUNTRY_1, "Test City Two");

    private final ImageModel IMAGE_ADV_1 = new ImageModel(1, null);
    private final ImageModel IMAGE_ADV_2 = new ImageModel(2, null);
    private final ImageModel IMAGE_ADV_3 = new ImageModel(3, null);

    private final ExperienceModel DEFAULT_ADV = new ExperienceModel(1L, "testaventura", "diraventura", null, "mail@aventura1.com", null, 0.0, CITY_1, CATEGORY_1, USER_1, IMAGE_ADV_1, true, 0);
    private final ExperienceModel DEFAULT_ADV2 = new ExperienceModel(7L, "testaventura2", "diraventura2", null, null, null, 1500.0, CITY_1, CATEGORY_1, USER_2, IMAGE_ADV_2, true, 0);
    private final ExperienceModel DEFAULT_ADV3 = new ExperienceModel(8L, "testaventura3", "diraventura3", null, null, null, 2000.0, CITY_2, CATEGORY_1, USER_2, IMAGE_ADV_3, true, 0);

    private final List<ExperienceModel> DEF_LIST_ALL = new ArrayList<>(Arrays.asList(DEFAULT_ADV, DEFAULT_ADV2, DEFAULT_ADV3));

    private final Long DEFAULT_SCORE = 0L;
    private final Optional<OrderByModel> NO_ORDER = Optional.empty();
    private final Long NO_CITY = (long) -1;
    private final Integer PAGE_SIZE = 3;

    private final ImageModel IMAGE = new ImageModel(1, null);
    /****/

    @Autowired
    private DataSource ds;

    @Autowired
    private ExperienceDao experienceDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Rollback
    public void testCreateExperience(){
        final ExperienceModel experienceModel= experienceDao.createExperience("TestCreate", "DirectionCreate", null, "owner@mail.com", null, null, CITY_1, CATEGORY_1, USER_1, IMAGE);
        assertNotNull(experienceModel);
        assertEquals("TestCreate", experienceModel.getExperienceName());
        assertEquals("DirectionCreate", experienceModel.getAddress());
        assertNull(experienceModel.getDescription());
        assertNull(experienceModel.getSiteUrl());
        assertNull(experienceModel.getPrice());
        assertEquals("owner@mail.com", experienceModel.getEmail());
        assertEquals(CITY_1, experienceModel.getCity());
        assertEquals(CATEGORY_1, experienceModel.getCategory());
        assertEquals(USER_1, experienceModel.getUser());

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + experienceModel.getExperienceId()));
    }

//    @Test
//    @Rollback
//    public void testUpdateExperience() {
//        ExperienceModel exp = new ExperienceModel(1L, "TestUpdate", "DirectionUpdate", "newdesc", "newemail", "newsite", 235.0, CITY_2, CATEGORY_2, USER_1);
//        experienceDao.updateExperience(exp);
//
//        Optional<ExperienceModel> experienceModelOptional = experienceDao.getExperienceById(1L);
//        assertTrue(experienceModelOptional.isPresent());
//
//        ExperienceModel experienceModel=experienceModelOptional.get();
//
//        assertEquals("TestUpdate", experienceModel.getExperienceName());
//        assertEquals("DirectionUpdate", experienceModel.getAddress());
//        assertEquals("newdesc", experienceModel.getDescription());
//        assertEquals("newsite", experienceModel.getSiteUrl());
//        assertEquals("newemail", experienceModel.getEmail());
//        assertEquals(new Double(235), experienceModel.getPrice());
//
//        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + experienceModel.getExperienceId()));
//        assertEquals(USER_1, experienceModel.getUser());
//        assertEquals(CATEGORY_2, experienceModel.getCategory());
//        assertEquals(CITY_2, experienceModel.getCity());
//    }
//
//    @Test
//    @Rollback
//    public void testDeleteExperience() {
//        experienceDao.deleteExperience(DEFAULT_ADV);
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + 1));
//    }
//
//    @Test
//    public void testGetExperienceById() {
//        Optional<ExperienceModel> experienceModel = experienceDao.getExperienceById(1L);
//        assertTrue(experienceModel.isPresent());
//        assertEquals(DEFAULT_ADV,experienceModel.get());
//    }

//    @Test
//    public void testListByCategory() {
//        final Optional<Double> maxPrice = experienceDao.getMaxPriceByCategory(CATEGORY_1);
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, maxPrice.get(), DEFAULT_SCORE, NO_CITY, NO_ORDER, 1, PAGE_SIZE);
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryAndCity() {
//        final Optional<Double> maxPrice = experienceDao.getMaxPriceByCategory(CATEGORY_1);
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, maxPrice.get(), DEFAULT_SCORE, 1L, NO_ORDER, 1, PAGE_SIZE);
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryAndPrice() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, 1750.0, DEFAULT_SCORE, NO_CITY, NO_ORDER, 1, PAGE_SIZE);
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryPriceAndCity() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, 1250.0, DEFAULT_SCORE, 1L, NO_ORDER, 1, PAGE_SIZE);
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testGetByUserId() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByUserId(2L, 1L);
//        assertFalse(experienceModelList.isEmpty());
//
//        //User 1 experiences
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        //User 2 experiences
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryAndScore(){
//        final Optional<Double> maxPrice = experienceDao.getMaxPriceByCategory(CATEGORY_1);
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, maxPrice.get(), 3L, NO_CITY, NO_ORDER, 1, PAGE_SIZE);
//
//        assertFalse(experienceModelList.isEmpty());
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryCityAndScore() {
//        final Optional<Double> maxPrice = experienceDao.getMaxPriceByCategory(CATEGORY_1);
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, maxPrice.get(), 3L, 1L, NO_ORDER, 1, PAGE_SIZE);
//
//        assertFalse(experienceModelList.isEmpty());
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryPriceAndScore() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, 1750.0, 3L, NO_CITY, NO_ORDER, 1, PAGE_SIZE);
//
//        assertFalse(experienceModelList.isEmpty());
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryPriceCityAndScoreOne() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, 1750.0, 3L, 1L, NO_ORDER, 1, PAGE_SIZE);
//
//        assertFalse(experienceModelList.isEmpty());
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryPriceCityAndScoreTwo() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, 1750.0, 3L, 2L, NO_ORDER, 1, PAGE_SIZE);
//
//        assertTrue(experienceModelList.isEmpty());
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByBestRanked() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByBestRanked(1L);
//
//        assertFalse(experienceModelList.isEmpty());
//        assertEquals( DEFAULT_ADV3, experienceModelList.get(0));
//        assertEquals( DEFAULT_ADV2, experienceModelList.get(1));
//        assertEquals( DEFAULT_ADV, experienceModelList.get(2));
//    }
//
//    @Test
//    public void testListExperiencesFavsByUserId() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesFavsByUserId(1L, NO_ORDER, 1, 6);
//
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_NIG));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
//    }
//
//    @Test
//    public void testGetCountExperiencesFavsByUserId() {
//        Integer count = experienceDao.getCountExperiencesFavsByUserId(1L);
//        assertEquals(Optional.of(2).get(), count );
//    }
//
//    @Test
//    public void testGetByName() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByName("testave", NO_ORDER,1, PAGE_SIZE);
//
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
//        assertFalse(experienceModelList.contains(DEFAULT_NIG));
//    }
//
//    @Test
//    public void testGetCountByName() {
//        assertEquals(new Integer(3), experienceDao.getCountByName("testave"));
//    }
//
//    @Test
//    public void testHasExperiencesByUserIdTrue(){
//        assertFalse(experienceDao.hasExperiencesByUserId(1L));
//    }
//
//    @Test
//    public void testHasExperiencesByUserIdFalse() {
//        assertTrue(experienceDao.hasExperiencesByUserId(3L));
//    }
}
