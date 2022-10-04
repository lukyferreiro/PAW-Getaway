package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.ReviewUserModel;
import ar.edu.itba.getaway.interfaces.persistence.ReviewDao;
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

    private final ReviewModel R1 = new ReviewModel(1L, "Title1", "Desc1", 1L, 1L, new Date(2022,1,1), 1L);
    private final ReviewModel R2 = new ReviewModel(2L, "Title2", "Desc2", 2L, 1L, new Date(2022,1,1), 2L);
    private final ReviewModel R3 = new ReviewModel(3L, "Title3", "Desc3", 3L, 1L, new Date(2022,1,1), 3L);

    private final Long ADV1_REV = 2L;
    private final Long ADV2_REV = 4L;
    private final Long ADV3_REV = 5L;

    private final ReviewUserModel U1R1 = new ReviewUserModel(1L, "Title1", "Desc1", 1L, 1L, new Date(2022,1,1), 1L, "owner", "user", 0L);
    private final ReviewUserModel U1R4 = new ReviewUserModel(4L, "Title4", "Desc4", 3L, 7L, new Date(2022,1,1), 1L, "owner", "user", 0L);
    private final ReviewUserModel U1R7 = new ReviewUserModel(7L, "Title7", "Desc7", 5L, 8L, new Date(2022,1,1), 1L, "owner", "user", 0L);

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
        final ReviewModel reviewModel = reviewDao.createReview("TestTitle", "TestDesc", 3L, 1L, new Date(2022, 1,1), 4L);
        assertNotNull(reviewModel);
        assertEquals("TestTitle", reviewModel.getTitle());
        assertEquals("TestDesc", reviewModel.getDescription());
        assertEquals(new Long(3), reviewModel.getScore());
        assertEquals(new Long(1), reviewModel.getExperienceId());
        assertEquals(new Date(2022, 1,1), reviewModel.getReviewDate());
        assertEquals(new Long(4), reviewModel.getUserId());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviews", "reviewId = " + reviewModel.getReviewId()));
    }

    @Test
    public void testGetReviewsByExperienceId() {
        final List<ReviewModel> reviewModelList = reviewDao.getReviewsByExperienceId(1L);
        assertNotNull(reviewModelList);
        assertFalse(reviewModelList.isEmpty());
        assertTrue(reviewModelList.contains(R1));
        assertTrue(reviewModelList.contains(R2));
        assertTrue(reviewModelList.contains(R3));
    }

    @Test
    public void testGetAverageScore() {
        Long adv1Review = reviewDao.getReviewAverageScore(1L);
        Long adv2Review = reviewDao.getReviewAverageScore(7L);
        Long adv3Review = reviewDao.getReviewAverageScore(8L);

        assertEquals(adv1Review, ADV1_REV);
        assertEquals(adv2Review, ADV2_REV);
        assertEquals(adv3Review, ADV3_REV);
    }

    @Test
    public void testGetReviewCount() {
        assertEquals(new Integer(0), reviewDao.getReviewCount(9L));
        assertEquals(new Integer(3), reviewDao.getReviewCount(1L));
        assertEquals(new Integer(3), reviewDao.getReviewCount(7L));
        assertEquals(new Integer(3), reviewDao.getReviewCount(8L));
    }

    @Test
    public void testGetReviewAndUser() {
        final List<ReviewUserModel> reviewUserModelList = reviewDao.getReviewAndUser(1L);
        assertNotNull(reviewUserModelList);
        assertFalse(reviewUserModelList.isEmpty());

        assertTrue(reviewUserModelList.contains(new ReviewUserModel(1L, "Title1", "Desc1", 1L, 1L, new Date(2022, 1,1), 1L, "owner", "user", 0L)));
        assertTrue(reviewUserModelList.contains(new ReviewUserModel(2L, "Title1", "Desc2", 2L, 1L, new Date(2022, 1,1), 2L, "owner2", "user2", 0L)));
        assertTrue(reviewUserModelList.contains(new ReviewUserModel(3L, "Title1", "Desc3", 3L, 1L, new Date(2022, 1,1), 3L, "owner3", "user3", 0L)));
    }

    @Test
    public void testGetById() {
        final Optional<ReviewModel> reviewModel = reviewDao.getReviewById(1L);
        assertTrue(reviewModel.isPresent());
        assertEquals(R1, reviewModel.get());
    }

    @Test
    public void testGetByUserId() {
        final List<ReviewUserModel> reviewModelList = reviewDao.getReviewsByUserId(1L);
        assertFalse(reviewModelList.isEmpty());
        assertTrue(reviewModelList.contains(U1R1));
        assertTrue(reviewModelList.contains(U1R4));
        assertTrue(reviewModelList.contains(U1R7));
    }

    @Test
    @Rollback
    public void testDeleteReview() {
        assertTrue(reviewDao.deleteReview(1L));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviews", "reviewId = " + 1));
    }

    @Test
    @Rollback
    public void testUpdateReview() {
        assertTrue(reviewDao.updateReview(1L, new ReviewModel(1L,"TitleUpdate", "DescUpdate", 5L, 7L, new Date(2020,1,1), 4L)));
        Optional<ReviewModel> reviewModel = reviewDao.getReviewById(1L);
        assertTrue(reviewModel.isPresent());
        assertEquals( "TitleUpdate", reviewModel.get().getTitle());
        assertEquals( "DescUpdate", reviewModel.get().getDescription());
        assertEquals(new Long(5), reviewModel.get().getScore());
        assertEquals(new Long(7), reviewModel.get().getExperienceId());
        assertEquals(new Long(4), reviewModel.get().getUserId());
        assertEquals(new Date(2020,1,1), reviewModel.get().getReviewDate());
    }
}
