//package ar.edu.itba.getaway.persistence.config;
//
//import ar.edu.itba.getaway.models.FavExperienceModel;
//import ar.edu.itba.getaway.interfaces.persistence.FavAndViewExperienceDao;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.jdbc.JdbcTestUtils;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import static org.junit.Assert.*;
//
//@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@Sql(scripts = "classpath:favexperience-dao-test.sql")
//public class FavAndViewExperienceDaoTest {
//    /** Data for tests **/
//
//    private final List<Long> USER_FAVOURITES = new ArrayList<>(Arrays.asList(1L, 5L));
//
//    /****/
//
//    @Autowired
//    private DataSource ds;
//
//    @Autowired
//    private FavAndViewExperienceDao favAndViewExperienceDao;
//
//    private JdbcTemplate jdbcTemplate;
//
//    @Before
//    public void setUp() {
//        this.jdbcTemplate = new JdbcTemplate(ds);
//    }
//
//    @Test
//    @Rollback
//    public void testCreate() {
//        final FavExperienceModel favExperienceModel = favAndViewExperienceDao.createFav(2L, 8L);
//        assertNotNull(favExperienceModel);
//        assertEquals(new Long(2), favExperienceModel.getUserId());
//        assertEquals(new Long(8), favExperienceModel.getExperienceId());
//        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "favuserexperience", "experienceId = " + favExperienceModel.getExperienceId() + " AND userId = " + favExperienceModel.getUserId()));
//    }
//
//    @Test
//    @Rollback
//    public void testDelete() {
//        assertTrue(favAndViewExperienceDao.deleteFav(1L,1L));
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "favuserexperience", "experienceId = " + 1 + " AND userId = " + 1));
//
//    }
//
//    @Test
//    public void testListByUserId() {
//        List<Long> experiencesIds = favAndViewExperienceDao.listFavsByUserId(1L);
//        assertFalse(experiencesIds.isEmpty());
//        assertTrue(experiencesIds.containsAll(USER_FAVOURITES));
//    }
//
//    @Test
//    public void testIsFavTrue() {
//        assertTrue(favAndViewExperienceDao.isFav(1L, 1L));
//    }
//
//    @Test
//    public void testIsFavFalse() {
//        assertFalse(favAndViewExperienceDao.isFav(1L, 8L));
//    }
//}
