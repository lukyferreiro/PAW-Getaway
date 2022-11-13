package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.persistence.ReviewDao;
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
import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import java.time.LocalDate;
import java.util.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:review-dao-test.sql")
public class ReviewDaoTest {
    /**
     * Data for tests
     **/

    //User data
    private static final RoleModel USER_MODEL = new RoleModel(2L, Roles.USER);
    private static final RoleModel NOT_VERIFIED_MODEL = new RoleModel(4L, Roles.NOT_VERIFIED);
    private static final Collection<RoleModel> DEFAULT_ROLES_MODELS = new ArrayList<>(Arrays.asList(USER_MODEL, NOT_VERIFIED_MODEL));
    private final static UserModel USER_1 = new UserModel(1L, "contra1", "owner", "user", "owner@mail.com", DEFAULT_ROLES_MODELS, null);

    //Experience data
    private final static CategoryModel CATEGORY_1 = new CategoryModel(1L, "Aventura");
    private final static CountryModel COUNTRY_1 = new CountryModel(1L, "Test Country");
    private final static CityModel CITY_1 = new CityModel(1L, COUNTRY_1, "Test City One");
    private final ImageModel IMAGE_ADV_1 = new ImageModel(1, null);
    private final ExperienceModel DEFAULT_ADV = new ExperienceModel(1L, "testaventura", "diraventura", null, "mail@aventura1.com", null, 0.0, CITY_1, CATEGORY_1, USER_1, IMAGE_ADV_1, true, 0);

    //Reviews data
    private final ReviewModel R1 = new ReviewModel(1L, "Title1", "Desc1", 1L, DEFAULT_ADV, LocalDate.of(2022, 1, 1), USER_1);
    private final ReviewModel R2 = new ReviewModel(2L, "Title2", "Desc2", 2L, DEFAULT_ADV, LocalDate.of(2022, 1, 1), USER_1);
    private final ReviewModel R3 = new ReviewModel(3L, "Title3", "Desc3", 3L, DEFAULT_ADV, LocalDate.of(2022, 1, 1), USER_1);

    private final Integer PAGE_SIZE = 12;
    /****/

    @Autowired
    private DataSource ds;
    @Autowired
    private ReviewDao reviewDao;
    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Rollback
    public void testCreateReview() {
        final ReviewModel reviewModel = reviewDao.createReview("TestTitle", "TestDesc", 3L, DEFAULT_ADV, LocalDate.of(2022, 1, 1), USER_1);
        assertNotNull(reviewModel);
        assertEquals("TestTitle", reviewModel.getTitle());
        assertEquals("TestDesc", reviewModel.getDescription());
        assertEquals(3L, reviewModel.getScore());
        assertEquals(DEFAULT_ADV, reviewModel.getExperience());
        assertEquals(LocalDate.of(2022, 1, 1), reviewModel.getReviewDate());
        assertEquals(USER_1, reviewModel.getUser());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviews", "reviewId = " + reviewModel.getReviewId()));
    }

    @Test
    public void testGetReviewsByExperience() {
        final List<ReviewModel> reviewModelList = reviewDao.getReviewsByExperience(DEFAULT_ADV, 1, PAGE_SIZE);
        assertNotNull(reviewModelList);
        assertFalse(reviewModelList.isEmpty());
        assertTrue(reviewModelList.contains(R1));
        assertTrue(reviewModelList.contains(R2));
        assertTrue(reviewModelList.contains(R3));
    }

    @Test
    public void testGetReviewById() {
        final Optional<ReviewModel> reviewModel = reviewDao.getReviewById(1L);
        assertTrue(reviewModel.isPresent());
        assertEquals(R1, reviewModel.get());
    }

    @Test
    public void testGetReviewsByUser() {
        final List<ReviewModel> reviewModelList = reviewDao.getReviewsByUser(USER_1, 1, PAGE_SIZE);
        assertFalse(reviewModelList.isEmpty());
        assertTrue(reviewModelList.contains(R1));
        assertTrue(reviewModelList.contains(R3));
    }

    @Test
    @Rollback
    public void testDeleteReview() {
//        reviewDao.deleteReview(R1);
        ReviewModel toDeleteReview = reviewDao.getReviewById(1L).orElse(R1);
        reviewDao.deleteReview(toDeleteReview);
        em.flush();
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviews", "reviewId = " + toDeleteReview.getReviewId()));
    }

    @Test
    @Rollback
    public void testUpdateReview() {
        reviewDao.updateReview(new ReviewModel(R1.getReviewId(), "TitleUpdate", "DescUpdate", 5L, R1.getExperience(), R1.getReviewDate(), R1.getUser()));
        Optional<ReviewModel> reviewModel = reviewDao.getReviewById(1L);
        assertTrue(reviewModel.isPresent());
        assertEquals("TitleUpdate", reviewModel.get().getTitle());
        assertEquals("DescUpdate", reviewModel.get().getDescription());
        assertEquals(5L, reviewModel.get().getScore());
    }
}
