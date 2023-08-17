package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.interfaces.exceptions.DuplicateExperienceException;
import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.persistence.ExperienceDao;
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

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.*;

//TODO: statements involving filters can not be prepared because HSQL does not support LOWER

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:experience-dao-test.sql")
public class ExperienceDaoTest {
    /**
     * Data for tests
     **/

    //User data
    private static final RoleModel USER_MODEL = new RoleModel(2L, Roles.USER);
    private static final RoleModel NOT_VERIFIED_MODEL = new RoleModel(4L, Roles.NOT_VERIFIED);
    private static final Collection<RoleModel> DEFAULT_ROLES_MODELS = new ArrayList<>(Arrays.asList(USER_MODEL, NOT_VERIFIED_MODEL));

    private static final String DEFAULT_TYPE = "JPG";
    private static final byte[] DEFAULT_IMG_OBJECT = {1, 2, 3, 4};

    private final static ImageModel U_IMAGE_1 = new ImageModel(15L, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    private final static ImageModel U_IMAGE_2 = new ImageModel(16L, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);

    private final static UserModel USER_1 = new UserModel(1L, "contra1", "owner", "user", "owner@mail.com", DEFAULT_ROLES_MODELS, U_IMAGE_1);
    private final static UserModel USER_2 = new UserModel(2L, "contra2", "owner2", "user2", "owner2@mail.com", DEFAULT_ROLES_MODELS, U_IMAGE_2);

    //Experience data
    private final static CategoryModel CATEGORY_1 = new CategoryModel(1L, "Aventura");
    private final static CategoryModel CATEGORY_2 = new CategoryModel(2L, "Gastronomia");
    private final static CategoryModel CATEGORY_6 = new CategoryModel(6L, "Historico");

    private final static CountryModel COUNTRY_1 = new CountryModel(1L, "Test Country");

    private final static CityModel CITY_1 = new CityModel(1L, COUNTRY_1, "Test City One");
    private final static CityModel CITY_2 = new CityModel(2L, COUNTRY_1, "Test City Two");
    private final static CityModel CITY_3 = new CityModel(3L, COUNTRY_1, "Test City Three");

    private final ImageModel IMAGE_ADV_1 = new ImageModel(1, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    private final ImageModel IMAGE_ADV_2 = new ImageModel(7, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    private final ImageModel IMAGE_ADV_3 = new ImageModel(8, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    private final ImageModel IMAGE_GAS = new ImageModel(2, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    private final ImageModel IMAGE_GAS2 = new ImageModel(3, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    private final ImageModel IMAGE_TO_DELETE = new ImageModel(50, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    private final ImageModel IMAGE_TO_RECOMMEND = new ImageModel(51, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    private final ImageModel IMAGE_HIDDEN = new ImageModel(52, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);

    private final ExperienceModel DEFAULT_ADV = new ExperienceModel(1L, "testaventura", "diraventura", null, "owner@mail.com", null, 0.0, CITY_1, CATEGORY_1, USER_1, IMAGE_ADV_1, true, 0);
    private final ExperienceModel DEFAULT_GAS = new ExperienceModel(2L, "testgastro", "dirgastro", null, "owner@mail.com", null, 1000.0, CITY_1, CATEGORY_2, USER_1, IMAGE_GAS, true, 0);
    private final ExperienceModel DEFAULT_GAS2 = new ExperienceModel(3L, "testgastro2", "dirgastro2", null, "owner@mail.com", null, 1000.0, CITY_1, CATEGORY_2, USER_2, IMAGE_GAS2, true, 0);
    private final ExperienceModel DEFAULT_ADV2 = new ExperienceModel(7L, "testaventura2", "diraventura2", null, "owner2@mail.com", null, 1500.0, CITY_1, CATEGORY_1, USER_2, IMAGE_ADV_2, true, 0);
    private final ExperienceModel DEFAULT_ADV3 = new ExperienceModel(8L, "testaventura3", "diraventura3", null, "owner2@mail.com", null, 2000.0, CITY_2, CATEGORY_1, USER_2, IMAGE_ADV_3, true, 0);
    private final ExperienceModel TO_DELETE_EXP = new ExperienceModel(50L, "todelete", "delete", null, "owner@mail.com", null, null, CITY_3, CATEGORY_6, USER_2, IMAGE_TO_DELETE, true, 0);
    private final ExperienceModel TO_RECOMMEND_EXP = new ExperienceModel(51L, "torecommend", "recommend1", null, "owner@mail.com", null, null, CITY_3, CATEGORY_2, USER_2, IMAGE_TO_RECOMMEND, true, 0);
    private final ExperienceModel HIDDEN_EXP = new ExperienceModel(52L, "hidden", "hidden", null, "owner@mail.com", null, null, CITY_3, CATEGORY_6, USER_2, IMAGE_HIDDEN, false, 0);

    private final Long DEFAULT_SCORE = 0L;
    private final Double DEFAULT_MAX_PRICE = 100000D;
    private final OrderByModel NO_ORDER = null;
    private final String EMPTY_STRING = "";
    private final Integer PAGE_SIZE = 3;

    private final ImageModel IMAGE = new ImageModel(5, DEFAULT_IMG_OBJECT, DEFAULT_TYPE);
    /****/

    @Autowired
    private DataSource ds;
    @Autowired
    private ExperienceDao experienceDao;
    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Rollback
    public void testCreateExperience() {

        ExperienceModel experienceModel = null;

        try {
            experienceModel = experienceDao.createExperience("TestCreate", "DirectionCreate", null, "owner@mail.com", null, null, CITY_1, CATEGORY_1, USER_1, IMAGE);
            assertNotNull(experienceModel);
        } catch (DuplicateExperienceException e) {
            e.printStackTrace();
        }

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

    @Test
    @Rollback
    public void testUpdateExperience() {
        ExperienceModel experienceModel = new ExperienceModel(1L, "TestUpdate", "DirectionUpdate", "newdesc", "newemail", "newsite", 235.0, CITY_2, CATEGORY_2, USER_1, IMAGE, true, 0);
        experienceDao.updateExperience(experienceModel);

        ExperienceModel experienceModelOptional = em.find(ExperienceModel.class, experienceModel.getExperienceId());
        assertNotNull(experienceModelOptional);

        assertEquals("TestUpdate", experienceModelOptional.getExperienceName());
        assertEquals("DirectionUpdate", experienceModelOptional.getAddress());
        assertEquals("newdesc", experienceModelOptional.getDescription());
        assertEquals("newsite", experienceModelOptional.getSiteUrl());
        assertEquals("newemail", experienceModelOptional.getEmail());
        assertEquals(new Double(235), experienceModelOptional.getPrice());

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + experienceModelOptional.getExperienceId()));
        assertEquals(USER_1, experienceModelOptional.getUser());
        assertEquals(CATEGORY_2, experienceModelOptional.getCategory());
        assertEquals(CITY_2, experienceModelOptional.getCity());
    }

    @Test
    @Rollback
    public void testDeleteExperience() {
        ExperienceModel toDeleteExperience = em.find(ExperienceModel.class, TO_DELETE_EXP.getExperienceId());
        experienceDao.deleteExperience(toDeleteExperience);
        em.flush();
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + TO_DELETE_EXP.getExperienceId()));
    }

    @Test
    public void testGetExperienceById() {
        Optional<ExperienceModel> experienceModel = experienceDao.getExperienceById(1L);
        assertTrue(experienceModel.isPresent());
        assertEquals(DEFAULT_ADV, experienceModel.get());
        //testing averageScore formula
        assertEquals(2, experienceModel.get().getAverageScore());
    }

    @Test
    public void testGetVisibleExperienceById() {
        Optional<ExperienceModel> experienceModel = experienceDao.getVisibleExperienceById(1L, null);
        assertTrue(experienceModel.isPresent());
        assertEquals(DEFAULT_ADV, experienceModel.get());
        //testing averageScore formula
        assertEquals(2, experienceModel.get().getAverageScore());
    }

    @Test
    public void testGetNotVisibleOwnedExperienceById() {
        UserModel owner = em.find(UserModel.class, USER_2.getUserId());
        Optional<ExperienceModel> experienceModel = experienceDao.getVisibleExperienceById(52L, owner);
        assertTrue(experienceModel.isPresent());
        assertEquals(HIDDEN_EXP, experienceModel.get());
    }

    @Test
    public void testGetNotVisibleNotOwnedExperienceById() {
        Optional<ExperienceModel> experienceModel = experienceDao.getVisibleExperienceById(52L, null);
        assertFalse(experienceModel.isPresent());
    }

//    @Test
//    public void testListByCategory() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(CATEGORY_1, EMPTY_STRING, DEFAULT_MAX_PRICE, DEFAULT_SCORE, null, NO_ORDER, 1, PAGE_SIZE);
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryAndCity() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(CATEGORY_1, EMPTY_STRING, DEFAULT_MAX_PRICE, DEFAULT_SCORE, CITY_1, NO_ORDER, 1, PAGE_SIZE);
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryAndPrice() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(CATEGORY_1, EMPTY_STRING,1750.0, DEFAULT_SCORE, null, NO_ORDER, 1, PAGE_SIZE);
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryPriceAndCity() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(CATEGORY_1, EMPTY_STRING,1250.0, DEFAULT_SCORE, CITY_1, NO_ORDER, 1, PAGE_SIZE);
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryAndScore() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(CATEGORY_1, EMPTY_STRING, DEFAULT_MAX_PRICE, 3L, null, NO_ORDER, 1, PAGE_SIZE);
//
//        assertFalse(experienceModelList.isEmpty());
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
//    }

//    @Test
//    public void testListByCategoryCityAndScore() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(CATEGORY_1, EMPTY_STRING, DEFAULT_MAX_PRICE, 3L, CITY_1, NO_ORDER, 1, PAGE_SIZE);
//
//        assertFalse(experienceModelList.isEmpty());
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryPriceAndScore() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(CATEGORY_1, EMPTY_STRING, 1750.0, 3L, null, NO_ORDER, 1, PAGE_SIZE);
//
//        assertFalse(experienceModelList.isEmpty());
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryPriceCityAndScoreOne() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(CATEGORY_1, EMPTY_STRING, 1750.0, 3L, CITY_1, NO_ORDER, 1, PAGE_SIZE);
//
//        assertFalse(experienceModelList.isEmpty());
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryPriceCityAndScoreTwo() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(CATEGORY_1, EMPTY_STRING, 1750.0, 3L, CITY_2, NO_ORDER, 1, PAGE_SIZE);
//        assertTrue(experienceModelList.isEmpty());
//    }

    @Test
    public void testListByBestRanked() {
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByBestRanked(CATEGORY_1, 6);

        assertFalse(experienceModelList.isEmpty());
        assertEquals(DEFAULT_ADV3, experienceModelList.get(0));
        assertEquals(DEFAULT_ADV2, experienceModelList.get(1));
        assertEquals(DEFAULT_ADV, experienceModelList.get(2));
    }

//    @Test
//    public void testListExperiencesSearch() {
//        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesSearch("aventura2", NO_ORDER, 1, PAGE_SIZE);
//        assertFalse(experienceModelList.isEmpty());
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }

//    @Test
//    public void testGetCountByName() {
//        assertEquals(1, experienceDao.countListByFilter(null, "aventura2", 10000D, 0L, null));
//    }

    @Test
    public void testListExperiencesSearchByUser() {
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesSearchByUser("test", USER_1, NO_ORDER, 1, PAGE_SIZE);
        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_GAS));
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
    }

    @Test
    public void testGetCountExperiencesByUser() {
        assertEquals(2, experienceDao.getCountExperiencesByUser("test", USER_1));
    }


    @Test
    public void testListExperiencesFavsByUser() {
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesFavsByUser(USER_1, NO_ORDER,1,PAGE_SIZE);
        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_GAS));
    }

    @Test
    public void testGetCountListExperiencesFavsByUser() {
        assertEquals(2, experienceDao.getCountListExperiencesFavsByUser(USER_1));
    }

    @Test
    public void testGetRecommendedByFavs() {
        List<ExperienceModel> recommended = experienceDao.getRecommendedByFavs(USER_1, PAGE_SIZE);
        assertNotNull(recommended);
        assertFalse(recommended.isEmpty());
        assertEquals(1, recommended.size());
        assertTrue(recommended.contains(DEFAULT_ADV3));
    }

    @Test
    public void testGetRecommendedByViews() {
        List<Long> alreadyAdded = new ArrayList<>();
        alreadyAdded.add(8L);
        List<ExperienceModel> recommended = experienceDao.getRecommendedByViews(USER_1, PAGE_SIZE, alreadyAdded);
        assertNotNull(recommended);
        assertFalse(recommended.isEmpty());
        assertEquals(1, recommended.size());
        assertTrue(recommended.contains(DEFAULT_ADV2));
    }

    @Test
    public void testGetBestRanked() {
        List<Long> alreadyAdded = new ArrayList<>();
        alreadyAdded.add(8L);
        alreadyAdded.add(7L);
        List<ExperienceModel> recommended = experienceDao.getRecommendedBestRanked(PAGE_SIZE, alreadyAdded);
        assertNotNull(recommended);
        assertFalse(recommended.isEmpty());
        assertEquals(3, recommended.size());

        assertTrue(recommended.contains(DEFAULT_ADV));
        assertTrue(recommended.contains(DEFAULT_GAS));
        assertTrue(recommended.contains(DEFAULT_GAS2));
    }

    @Test
    public void getReviewedExperiencesId() {
        List<Long> reviewedIds = experienceDao.reviewedExperiencesId(USER_1);
        assertNotNull(reviewedIds);
        assertFalse(reviewedIds.isEmpty());
        assertEquals(3, reviewedIds.size());
        assertTrue(reviewedIds.contains(1L));
        assertTrue(reviewedIds.contains(7L));
        assertTrue(reviewedIds.contains(8L));
    }

    @Test
    public void testGetRecommendedByReviewsCity() {
        List<Long> reviewedIds = new ArrayList<>();
        reviewedIds.add(1L);
        reviewedIds.add(7L);
        reviewedIds.add(8L);
        List<ExperienceModel> recommended = experienceDao.getRecommendedByReviewsCity(USER_1, PAGE_SIZE, new ArrayList<>(), reviewedIds);
        assertNotNull(recommended);
        assertFalse(recommended.isEmpty());
        assertEquals(1, recommended.size());
        assertTrue(recommended.contains(DEFAULT_GAS2));
    }

    @Test
    public void testGetRecommendedByReviewsProvider() {
        List<Long> reviewedIds = new ArrayList<>();
        reviewedIds.add(1L);
        reviewedIds.add(7L);
        reviewedIds.add(8L);
        List<ExperienceModel> recommended = experienceDao.getRecommendedByReviewsProvider(USER_1, PAGE_SIZE, new ArrayList<>(), reviewedIds);
        assertNotNull(recommended);
        assertFalse(recommended.isEmpty());
        assertEquals(3, recommended.size());
        assertTrue(recommended.contains(TO_DELETE_EXP));
        assertTrue(recommended.contains(TO_RECOMMEND_EXP));
        assertTrue(recommended.contains(DEFAULT_GAS2));
    }

    @Test
    public void testGetRecommendedByReviewsCategory() {
        List<Long> reviewedIds = new ArrayList<>();
        reviewedIds.add(1L);
        reviewedIds.add(7L);
        reviewedIds.add(8L);
        List<ExperienceModel> recommended = experienceDao.getRecommendedByReviewsCategory(USER_1, PAGE_SIZE, new ArrayList<>(), reviewedIds);
        assertNotNull(recommended);
        assertFalse(recommended.isEmpty());
        assertEquals(1, recommended.size());
        assertTrue(recommended.contains(TO_RECOMMEND_EXP));
    }
}
