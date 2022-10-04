package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.OrderByModel;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:experience-dao-test.sql")
public class ExperienceDaoTest {
    /** Data for tests **/

    private final ExperienceModel DEFAULT_ADV = new ExperienceModel(1L, "testaventura", "diraventura", null, null, null, (double) 0, 1L, 1L, 1L, 1L, false);
    private final ExperienceModel DEFAULT_GAS = new ExperienceModel(2L, "testgastro", "dirgastro", null, null, null, 1000.0, 1L, 2L, 1L, 2L, false);
    private final ExperienceModel DEFAULT_HOT = new ExperienceModel(3L, "testhotel", "dirhotel", null, null, null, 1000.0, 1L, 3L, 1L, 3L, false);
    private final ExperienceModel DEFAULT_REL = new ExperienceModel(4L, "testrelax", "dirrelax", null, null, null, 10000.0, 2L, 4L, 1L, 4L, false);
    private final ExperienceModel DEFAULT_NIG = new ExperienceModel(5L, "testnight", "dirnight", null, null, null, null,2L, 5L, 1L,  5L, false);
    private final ExperienceModel DEFAULT_HIS = new ExperienceModel(6L, "testhist", "dirhist", null, null, null, 5000.0, 2L, 6L, 2L,  6L, false);
    private final ExperienceModel DEFAULT_ADV2 = new ExperienceModel(7L, "testaventura2", "diraventura2", null, null, null, 1500.0, 1L, 1L, 2L,  7L, false);
    private final ExperienceModel DEFAULT_ADV3 = new ExperienceModel(8L, "testaventura3", "diraventura3", null, null, null, 2000.0, 2L, 1L, 2L,  8L, false);

    private final List<ExperienceModel> DEF_LIST_ALL = new ArrayList<>(Arrays.asList(DEFAULT_ADV, DEFAULT_GAS, DEFAULT_HOT, DEFAULT_REL, DEFAULT_NIG, DEFAULT_HIS, DEFAULT_ADV2, DEFAULT_ADV3));

    private final Long ADV1_REV = 2L;
    private final Long ADV2_REV = 4L;
    private final Long ADV3_REV = 5L;

    private final Long DEFAULT_SCORE = 0L;
    private final Optional<OrderByModel> NO_ORDER = Optional.empty();
    private final Long NO_CITY = (long) -1;
    private final Integer PAGE_SIZE = 3;

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
        final ExperienceModel experienceModel= experienceDao.createExperience("TestCreate", "DirectionCreate", null, "owner@mail.com", null, null, 1L, 1L, 1L);
        assertNotNull(experienceModel);
        assertEquals("TestCreate", experienceModel.getExperienceName());
        assertEquals("DirectionCreate", experienceModel.getAddress());
        assertNull(experienceModel.getDescription());
        assertNull(experienceModel.getSiteUrl());
        assertNull(experienceModel.getPrice());
        assertEquals("owner@mail.com", experienceModel.getEmail());
        //TODO corregir
        assertEquals(new Long(1), experienceModel.getCityId());
        assertEquals(new Long(1), experienceModel.getCategoryId());
        assertEquals(new Long(1), experienceModel.getUserId());
        assertFalse(experienceModel.isHasImage());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + experienceModel.getExperienceId()));
    }

    @Test
    @Rollback
    public void testUpdateExperience() {
        final ExperienceModel experienceModel = new ExperienceModel(1L, "TestUpdate", "DirectionUpdate", "newdesc", "newemail", "newsite", 235.0, 2L, 2L, 1L, 1L, false);

        assertTrue(experienceDao.updateExperience( experienceModel));
        assertEquals("TestUpdate", experienceModel.getExperienceName());
        assertEquals("DirectionUpdate", experienceModel.getAddress());
        assertEquals("newdesc", experienceModel.getDescription());
        assertEquals("newsite", experienceModel.getSiteUrl());
        assertEquals("newemail", experienceModel.getEmail());
        assertEquals(new Double(235), experienceModel.getPrice());
        //TODO check
        assertEquals(new Long(2), experienceModel.getCityId());
        assertEquals(new Long(2), experienceModel.getCategoryId());
        assertEquals(new Long(1), experienceModel.getUserId());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + experienceModel.getExperienceId()));
    }

    @Test
    @Rollback
    public void testDeleteExperience() {
        assertTrue(experienceDao.deleteExperience(1L));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + 1));
    }

    @Test
    public void testGetExperienceById() {
        Optional<ExperienceModel> experienceModel = experienceDao.getExperienceById(1L);
        assertTrue(experienceModel.isPresent());
        assertEquals(DEFAULT_ADV,experienceModel.get());
    }

    @Test
    public void testListByCategory() {
        final Optional<Double> maxPrice = experienceDao.getMaxPriceByCategoryId(1L);
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, maxPrice.get(), DEFAULT_SCORE, NO_CITY, NO_ORDER, 1, PAGE_SIZE);
        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryAndCity() {
        final Optional<Double> maxPrice = experienceDao.getMaxPriceByCategoryId(1L);
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, maxPrice.get(), DEFAULT_SCORE, 1L, NO_ORDER, 1, PAGE_SIZE);
        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryAndPrice() {
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, 1750.0, DEFAULT_SCORE, NO_CITY, NO_ORDER, 1, PAGE_SIZE);
        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryPriceAndCity() {
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, 1250.0, DEFAULT_SCORE, 1L, NO_ORDER, 1, PAGE_SIZE);
        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testGetByUserId() {
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByUserId(2L, 1L);
        assertFalse(experienceModelList.isEmpty());

        //User 1 experiences
        assertFalse(experienceModelList.contains(DEFAULT_ADV));
        //User 2 experiences
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryAndScore(){
        final Optional<Double> maxPrice = experienceDao.getMaxPriceByCategoryId(1L);
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, maxPrice.get(), 3L, NO_CITY, NO_ORDER, 1, PAGE_SIZE);

        assertFalse(experienceModelList.isEmpty());
        assertFalse(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryCityAndScore() {
        final Optional<Double> maxPrice = experienceDao.getMaxPriceByCategoryId(1L);
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, maxPrice.get(), 3L, 1L, NO_ORDER, 1, PAGE_SIZE);

        assertFalse(experienceModelList.isEmpty());
        assertFalse(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryPriceAndScore() {
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, 1750.0, 3L, NO_CITY, NO_ORDER, 1, PAGE_SIZE);

        assertFalse(experienceModelList.isEmpty());
        assertFalse(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryPriceCityAndScoreOne() {
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, 1750.0, 3L, 1L, NO_ORDER, 1, PAGE_SIZE);

        assertFalse(experienceModelList.isEmpty());
        assertFalse(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryPriceCityAndScoreTwo() {
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByFilter(1L, 1750.0, 3L, 2L, NO_ORDER, 1, PAGE_SIZE);

        assertTrue(experienceModelList.isEmpty());
        //TODO CHECK
        assertFalse(experienceModelList.contains(DEFAULT_ADV));
        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByBestRanked() {
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByBestRanked(1L);

        //TODO CHEQUEAR
        assertFalse(experienceModelList.isEmpty());
        assertEquals( DEFAULT_ADV3, experienceModelList.get(0));
        assertEquals( DEFAULT_ADV2, experienceModelList.get(1));
        assertEquals( DEFAULT_ADV, experienceModelList.get(2));
    }

    @Test
    public void testListFavsByUserId() {
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesFavsByUserId(1L, NO_ORDER, 1, 6);

        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_NIG));
        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
    }

    @Test
    public void testGetByName() {
        List<ExperienceModel> experienceModelList = experienceDao.listExperiencesByName("testave", NO_ORDER,1, PAGE_SIZE);

        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
        assertFalse(experienceModelList.contains(DEFAULT_NIG));
    }

    @Test
    public void testGetCountByName() {
        assertEquals(new Integer(3), experienceDao.getCountByName("testave"));
    }

}
