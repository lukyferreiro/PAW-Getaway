package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.OrderByModel;
import ar.edu.itba.getaway.persistence.ExperienceDao;
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

    private final ExperienceModel DEFAULT_ADV = new ExperienceModel(1, "testaventura", "diraventura", null, null, null, new Double(0), 1, 1, 1, new Long(1), false);
    private final ExperienceModel DEFAULT_GAS = new ExperienceModel(2, "testgastro", "dirgastro", null, null, null, new Double(1000), 1, 2, 1, new Long(2), false);
    private final ExperienceModel DEFAULT_HOT = new ExperienceModel(3, "testhotel", "dirhotel", null, null, null, new Double(1000), 1, 3, 1, new Long(3), false);
    private final ExperienceModel DEFAULT_REL = new ExperienceModel(4, "testrelax", "dirrelax", null, null, null, new Double(10000), 2, 4, 1, new Long(4), false);
    private final ExperienceModel DEFAULT_NIG = new ExperienceModel(5, "testnight", "dirnight", null, null, null, null,2, 5, 1,  new Long(5), false);
    private final ExperienceModel DEFAULT_HIS = new ExperienceModel(6, "testhist", "dirhist", null, null, null, new Double(5000), 2, 6, 2,  new Long(6), false);
    private final ExperienceModel DEFAULT_ADV2 = new ExperienceModel(7, "testaventura2", "diraventura2", null, null, null,new Double(1500), 1, 1, 2,  new Long(7), false);
    private final ExperienceModel DEFAULT_ADV3 = new ExperienceModel(8, "testaventura3", "diraventura3", null, null, null, new Double(2000), 2, 1, 2,  new Long(8), false);

    private final List<ExperienceModel> DEF_LIST_ALL = new ArrayList<>(Arrays.asList(DEFAULT_ADV, DEFAULT_GAS, DEFAULT_HOT, DEFAULT_REL, DEFAULT_NIG, DEFAULT_HIS, DEFAULT_ADV2, DEFAULT_ADV3));

    private final Long ADV1_REV = new Long(2);
    private final Long ADV2_REV = new Long(4);
    private final Long ADV3_REV = new Long(5);

    private final long DEFAULT_SCORE = 0;
    private final Optional<OrderByModel> NO_ORDER = Optional.empty();
    private final Long NO_CITY = new Long(-1);
    private final int PAGE_SIZE = 3;

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
        final ExperienceModel experienceModel= experienceDao.create("TestCreate", "DirectionCreate", null, "owner@mail.com", null, null, 1, 1, 1);
        assertNotNull(experienceModel);
        assertEquals("TestCreate", experienceModel.getExperienceName());
        assertEquals("DirectionCreate", experienceModel.getAddress());
        assertNull(experienceModel.getDescription());
        assertNull(experienceModel.getSiteUrl());
        assertNull(experienceModel.getPrice());
        assertEquals("owner@mail.com", experienceModel.getEmail());
        assertEquals(1, experienceModel.getCityId());
        assertEquals(1, experienceModel.getCategoryId());
        assertEquals(1, experienceModel.getUserId());
        assertFalse(experienceModel.isHasImage());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + experienceModel.getExperienceId()));
    }

    @Test
    @Rollback
    public void testUpdateExperience() {
        final ExperienceModel experienceModel = new ExperienceModel(1, "TestUpdate", "DirectionUpdate", "newdesc", "newemail", "newsite", new Double(235), 2, 2, 1, new Long(1), false);

        assertTrue(experienceDao.update( experienceModel));
        assertEquals("TestUpdate", experienceModel.getExperienceName());
        assertEquals("DirectionUpdate", experienceModel.getAddress());
        assertEquals("newdesc", experienceModel.getDescription());
        assertEquals("newsite", experienceModel.getSiteUrl());
        assertEquals("newemail", experienceModel.getEmail());
        assertEquals(new Double(235), experienceModel.getPrice());
        assertEquals(2, experienceModel.getCityId());
        assertEquals(2, experienceModel.getCategoryId());
        assertEquals(1, experienceModel.getUserId());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + experienceModel.getExperienceId()));
    }

    @Test
    @Rollback
    public void testDeleteExperience() {
        assertTrue(experienceDao.delete(1));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + 1));
    }

    @Test
    public void testListAllExperience() {
        List<ExperienceModel> experienceModelList = experienceDao.listAll("");
        assertEquals(DEF_LIST_ALL.size(), experienceModelList.size());
        assertTrue(experienceModelList.containsAll(DEF_LIST_ALL));
    }

    @Test
    public void testGetByIdExperience() {
        Optional<ExperienceModel> experienceModel = experienceDao.getById(1);
        assertTrue(experienceModel.isPresent());
        assertEquals(DEFAULT_ADV,experienceModel.get());
    }

    @Test
    public void testListByCategory() {
        final Optional<Double> maxPrice = experienceDao.getMaxPrice(1);
        List<ExperienceModel> experienceModelList = experienceDao.listByFilter(1, maxPrice.get(), DEFAULT_SCORE, NO_CITY, NO_ORDER, 1, PAGE_SIZE);
        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryAndCity() {
        final Optional<Double> maxPrice = experienceDao.getMaxPrice(1);
        List<ExperienceModel> experienceModelList = experienceDao.listByFilter(1, maxPrice.get(), DEFAULT_SCORE, new Long(1), NO_ORDER, 1, PAGE_SIZE);
        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryAndPrice() {
        List<ExperienceModel> experienceModelList = experienceDao.listByFilter(1, new Double(1750), DEFAULT_SCORE, NO_CITY, NO_ORDER, 1, PAGE_SIZE);
        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryPriceAndCity() {
        List<ExperienceModel> experienceModelList = experienceDao.listByFilter(1, new Double(1250), DEFAULT_SCORE, new Long(1), NO_ORDER, 1, PAGE_SIZE);
        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testGetByUserId() {
        List<ExperienceModel> experienceModelList = experienceDao.listByUserId(1, NO_ORDER);
        assertFalse(experienceModelList.isEmpty());

        //User 1 experiences
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_GAS));
        assertTrue(experienceModelList.contains(DEFAULT_HOT));
        assertTrue(experienceModelList.contains(DEFAULT_REL));
        assertTrue(experienceModelList.contains(DEFAULT_NIG));

        //User 2 experiences
        assertFalse(experienceModelList.contains(DEFAULT_HIS));
        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryAndScore(){
        final Optional<Double> maxPrice = experienceDao.getMaxPrice(1);
        List<ExperienceModel> experienceModelList = experienceDao.listByFilter(1, maxPrice.get(), 3, NO_CITY, NO_ORDER, 1, PAGE_SIZE);

        assertFalse(experienceModelList.isEmpty());
        assertFalse(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryCityAndScore() {
        final Optional<Double> maxPrice = experienceDao.getMaxPrice(1);
        List<ExperienceModel> experienceModelList = experienceDao.listByFilter(1, maxPrice.get(), 3, new Long(1), NO_ORDER, 1, PAGE_SIZE);

        assertFalse(experienceModelList.isEmpty());
        assertFalse(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryPriceAndScore() {
        List<ExperienceModel> experienceModelList = experienceDao.listByFilter(1, new Double(1750), 3, NO_CITY, NO_ORDER, 1, PAGE_SIZE);

        assertFalse(experienceModelList.isEmpty());
        assertFalse(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryPriceCityAndScoreOne() {
        List<ExperienceModel> experienceModelList = experienceDao.listByFilter(1, new Double(1750), 3, new Long(1), NO_ORDER, 1, PAGE_SIZE);

        assertFalse(experienceModelList.isEmpty());
        assertFalse(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByCategoryPriceCityAndScoreTwo() {
        List<ExperienceModel> experienceModelList = experienceDao.listByFilter(1, new Double(1750), 3, new Long(2), NO_ORDER, 1, PAGE_SIZE);

        assertTrue(experienceModelList.isEmpty());
        assertFalse(experienceModelList.contains(DEFAULT_ADV));
        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
    }

    @Test
    public void testListByBestRanked() {
        List<ExperienceModel> experienceModelList = experienceDao.listByBestRanked(1);

        assertFalse(experienceModelList.isEmpty());
        assertEquals( DEFAULT_ADV3, experienceModelList.get(0));
        assertEquals( DEFAULT_ADV2, experienceModelList.get(1));
        assertEquals( DEFAULT_ADV, experienceModelList.get(2));
    }

    @Test
    public void testListFavsByUserId() {
        List<ExperienceModel> experienceModelList = experienceDao.listFavsByUserId(new Long(1), NO_ORDER);

        assertFalse(experienceModelList.isEmpty());
        assertTrue(experienceModelList.contains(DEFAULT_ADV));
        assertTrue(experienceModelList.contains(DEFAULT_NIG));
        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
    }

    @Test
    public void testGetByName() {
        List<ExperienceModel> experienceModelList = experienceDao.getByName("testave", NO_ORDER,1, PAGE_SIZE);

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
