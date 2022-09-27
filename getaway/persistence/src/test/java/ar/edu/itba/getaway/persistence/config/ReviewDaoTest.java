package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;
import ar.edu.itba.getaway.persistence.ReviewDao;
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
import javax.sql.DataSource;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:review-dao-test.sql")
public class ReviewDaoTest {
    /** Data for tests **/

    private final ReviewModel R1 = new ReviewModel(1, "Title1", "Desc1", 1, 1, new Date(2022,1,1), 1);
    private final ReviewModel R2 = new ReviewModel(2, "Title2", "Desc2", 2, 1, new Date(2022,1,1), 2);
    private final ReviewModel R3 = new ReviewModel(3, "Title3", "Desc3", 3, 1, new Date(2022,1,1), 3);

    private final Double ADV1_REV = new Double(2);
    private final Double ADV2_REV = new Double(4);
    private final Double ADV3_REV = new Double(5);

    private final ReviewUserModel U1R1 = new ReviewUserModel(1, "Title1", "Desc1", 1, 1, new Date(2022,1,1), 1, "owner", "user", new Long(0));
    private final ReviewUserModel U1R4 = new ReviewUserModel(4, "Title4", "Desc4", 3, 7, new Date(2022,1,1), 1, "owner", "user", new Long(0));
    private final ReviewUserModel U1R7 = new ReviewUserModel(7, "Title7", "Desc7", 5, 8, new Date(2022,1,1), 1, "owner", "user", new Long(0));

    /****/

    @Autowired
    private DataSource ds;

    @Autowired
    private ReviewDao reviewDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Rollback
    public void testCreateReview() {
        final ReviewModel reviewModel = reviewDao.create("TestTitle", "TestDesc", 3, 1, new Date(2022, 1,1), 4);
        assertNotNull(reviewModel);
        assertEquals("TestTitle", reviewModel.getTitle());
        assertEquals("TestDesc", reviewModel.getDescription());
        assertEquals(3, reviewModel.getScore());
        assertEquals(1, reviewModel.getExperienceId());
        assertEquals(new Date(2022, 1,1), reviewModel.getReviewDate());
        assertEquals(4, reviewModel.getUserId());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviews", "reviewId = " + reviewModel.getReviewId()));
    }

    @Test
    public void testGetReviewsFromId() {
        final List<ReviewModel> reviewModelList = reviewDao.getReviewsFromId(1);
        assertNotNull(reviewModelList);
        assertFalse(reviewModelList.isEmpty());
        assertTrue(reviewModelList.contains(R1));
        assertTrue(reviewModelList.contains(R2));
        assertTrue(reviewModelList.contains(R3));
    }

    @Test
    public void testGetAverageScore() {
        Double adv1Review = reviewDao.getAverageScore(1);
        Double adv2Review = reviewDao.getAverageScore(7);
        Double adv3Review = reviewDao.getAverageScore(8);

        assertEquals(adv1Review, ADV1_REV);
        assertEquals(adv2Review, ADV2_REV);
        assertEquals(adv3Review, ADV3_REV);
    }

    @Test
    public void testGetReviewCount() {
        assertEquals(new Integer(0), reviewDao.getReviewCount(9));
        assertEquals(new Integer(3), reviewDao.getReviewCount(1));
        assertEquals(new Integer(3), reviewDao.getReviewCount(7));
        assertEquals(new Integer(3), reviewDao.getReviewCount(8));
    }

    @Test
    public void testGetReviewAndUser() {
        final List<ReviewUserModel> reviewUserModelList = reviewDao.getReviewAndUser(1);
        assertNotNull(reviewUserModelList);
        assertFalse(reviewUserModelList.isEmpty());

        assertTrue(reviewUserModelList.contains(new ReviewUserModel(1, "Title1", "Desc1", 1, 1, new Date(2022, 1,1), 1, "owner", "user", new Long(0))));
        assertTrue(reviewUserModelList.contains(new ReviewUserModel(2, "Title1", "Desc2", 2, 1, new Date(2022, 1,1), 2, "owner2", "user2", new Long(0))));
        assertTrue(reviewUserModelList.contains(new ReviewUserModel(3, "Title1", "Desc3", 3, 1, new Date(2022, 1,1), 3, "owner3", "user3", new Long(0))));
    }

    @Test
    public void testGetById() {
        final Optional<ReviewModel> reviewModel = reviewDao.getById(1);
        assertTrue(reviewModel.isPresent());
        assertEquals(R1, reviewModel.get());
    }

    @Test
    public void testGetByUserId() {
        final List<ReviewUserModel> reviewModelList = reviewDao.getByUserId(1);
        assertFalse(reviewModelList.isEmpty());
        assertTrue(reviewModelList.contains(U1R1));
        assertTrue(reviewModelList.contains(U1R4));
        assertTrue(reviewModelList.contains(U1R7));
    }

    @Test
    @Rollback
    public void testDeleteReview() {
        assertTrue(reviewDao.delete(1));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviews", "reviewId = " + 1));
    }

    @Test
    @Rollback
    public void testUpdateReview() {
        assertTrue(reviewDao.update(1, new ReviewModel(1,"TitleUpdate", "DescUpdate", 5, 7, new Date(2020,1,1), 4)));
        Optional<ReviewModel> reviewModel = reviewDao.getById(1);
        assertTrue(reviewModel.isPresent());
        assertEquals( "TitleUpdate", reviewModel.get().getTitle());
        assertEquals( "DescUpdate", reviewModel.get().getDescription());
        assertEquals( 5, reviewModel.get().getScore());
        assertEquals( 7, reviewModel.get().getExperienceId());
        assertEquals( 4, reviewModel.get().getUserId());
        assertEquals(  new Date(2020,1,1), reviewModel.get().getReviewDate());
    }
}
