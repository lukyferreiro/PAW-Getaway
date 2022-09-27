//package ar.edu.itba.getaway.persistence.config;
//
//import ar.edu.itba.getaway.models.ExperienceModel;
//import ar.edu.itba.getaway.persistence.ExperienceDao;
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
//import static org.junit.Assert.*;
//import static org.junit.Assert.assertEquals;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@Sql(scripts = "classpath:experience-dao-test.sql")
//public class ExperienceDaoTest {
//    /** Data for tests **/
//    private final ExperienceModel DEFAULT_ADV = new ExperienceModel(1, "testaventura", "diraventura", null, null,new Double(0), 1, 1, 1, false);
//    private final ExperienceModel DEFAULT_GAS = new ExperienceModel(2, "testgastro", "dirgastro", null, null,new Double(1000), 1, 2, 1, false);
//    private final ExperienceModel DEFAULT_HOT = new ExperienceModel(3, "testhotel", "dirhotel", null, null,new Double(1000), 1, 3, 1, false);
//    private final ExperienceModel DEFAULT_REL = new ExperienceModel(4, "testrelax", "dirrelax", null, null,new Double(10000), 2, 4, 1, false);
//    private final ExperienceModel DEFAULT_NIG = new ExperienceModel(5, "testnight", "dirnight", null, null, null, 2, 5, 1, false);
//    private final ExperienceModel DEFAULT_HIS = new ExperienceModel(6, "testhist", "dirhist", null, null,new Double(5000), 2, 6, 2, false);
//    private final ExperienceModel DEFAULT_ADV2 = new ExperienceModel(7, "testaventura2", "diraventura2", null, null,new Double(1500), 1, 1, 2, false);
//    private final ExperienceModel DEFAULT_ADV3 = new ExperienceModel(8, "testaventura3", "diraventura3", null, null,new Double(2000), 2, 1, 2, false);
//
//    private final List<ExperienceModel> DEF_LIST_ALL = new ArrayList<>(Arrays.asList(DEFAULT_ADV, DEFAULT_GAS, DEFAULT_HOT, DEFAULT_REL, DEFAULT_NIG, DEFAULT_HIS, DEFAULT_ADV2, DEFAULT_ADV3));
//
//    private final Long ADV1_REV = new Long(2);
//    private final Long ADV2_REV = new Long(4);
//    private final Long ADV3_REV = new Long(5);
//    /****/
//
//    @Autowired
//    private DataSource ds;
//
//    @Autowired
//    private ExperienceDao experienceDao;
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
//    public void testCreateExperience() {
//        final ExperienceModel experienceModel= experienceDao.create("TestCreate", "DirectionCreate", null, null, null, 1, 1, 1, false);
//        assertNotNull(experienceModel);
//        assertEquals("TestCreate", experienceModel.getName());
//        assertEquals("DirectionCreate", experienceModel.getAddress());
//        assertEquals(null, experienceModel.getDescription());
//        assertEquals(null, experienceModel.getSiteUrl());
//        assertEquals(null, experienceModel.getPrice());
//        assertEquals(1, experienceModel.getCityId());
//        assertEquals(1, experienceModel.getCategoryId());
//        assertEquals(1, experienceModel.getUserId());
//        assertEquals(false, experienceModel.isHasImage());
//        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + experienceModel.getId()));
//    }
//
//    @Test
//    @Rollback
//    public void testUpdateExperience() {
//        final ExperienceModel experienceModel = new ExperienceModel(1, "TestUpdate", "DirectionUpdate", "newdesc", "newsite", new Double(235), 2, 2, 1, true);
//        assertTrue(experienceDao.update(1, experienceModel));
//
//        assertEquals("TestUpdate", experienceModel.getName());
//        assertEquals("DirectionUpdate", experienceModel.getAddress());
//        assertEquals("newdesc", experienceModel.getDescription());
//        assertEquals("newsite", experienceModel.getSiteUrl());
//        assertEquals(new Double(235), experienceModel.getPrice());
//        assertEquals(2, experienceModel.getCityId());
//        assertEquals(2, experienceModel.getCategoryId());
//        assertEquals(1, experienceModel.getUserId());
//        assertEquals(true, experienceModel.isHasImage());
//        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + experienceModel.getId()));
//    }
//
//    @Test
//    @Rollback
//    public void testDeleteExperience() {
//        assertTrue(experienceDao.delete(1));
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "experiences", "experienceId = " + 1));
//    }
//
//    @Test
//    public void testListAllExperience() {
//        List<ExperienceModel> experienceModelList = experienceDao.listAll();
//        assertEquals(DEF_LIST_ALL.size(), experienceModelList.size());
//        assertTrue(experienceModelList.containsAll(DEF_LIST_ALL));
//    }
//
//    @Test
//    public void testGetByIdExperience() {
//        Optional<ExperienceModel> experienceModel = experienceDao.getById(1);
//        assertTrue(experienceModel.isPresent());
//        assertEquals(DEFAULT_ADV,experienceModel.get());
//    }
//
//    @Test
//    public void testListByCategory() {
//        List<ExperienceModel> experienceModelList = experienceDao.listByCategory(1);
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryAndCity() {
//        List<ExperienceModel> experienceModelList = experienceDao.listByCategoryAndCity(1,1);
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryAndPrice() {
//        List<ExperienceModel> experienceModelList = experienceDao.listByCategoryAndPrice(1, new Double(1750));
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryPriceAndCity() {
//        List<ExperienceModel> experienceModelList = experienceDao.listByCategoryPriceAndCity(1, new Double(1250), 1);
//        assertFalse(experienceModelList.isEmpty());
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    //Weird behavior, not sure how to test
////    @Test
////    public void testRandom() {
////        List<ExperienceModel> experienceModelList = experienceDao.getRandom();
////        assertFalse(experienceModelList.isEmpty());
////        assertTrue(experienceModelList.containsAll(DEF_LIST_ALL));
////    }
//
//    @Test
//    public void testGetCountryCity() {
//        String countryCity = experienceDao.getCountryCity(1);
//        String toMatch = String.format("Test Country, Test City One");
//        assertNotNull(countryCity);
//        assertEquals(countryCity,toMatch);
//    }
//
//    @Test
//    public void testGetByUserId() {
//        List<ExperienceModel> experienceModelList = experienceDao.listByUserId(1);
//        assertFalse(experienceModelList.isEmpty());
//
//        //User 1 experiences
//        assertTrue(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_GAS));
//        assertTrue(experienceModelList.contains(DEFAULT_HOT));
//        assertTrue(experienceModelList.contains(DEFAULT_REL));
//        assertTrue(experienceModelList.contains(DEFAULT_NIG));
//
//        //User 2 experiences
//        assertFalse(experienceModelList.contains(DEFAULT_HIS));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    //TODO: unify methods in getAverageReviews?
//    @Test
//    public void testGetAvgReviews() {
//        Optional<Long> adv1Review = experienceDao.getAvgReviews(DEFAULT_ADV.getId());
//        Optional<Long> adv2Review = experienceDao.getAvgReviews(DEFAULT_ADV2.getId());
//        Optional<Long> adv3Review = experienceDao.getAvgReviews(DEFAULT_ADV3.getId());
//
//        assertTrue(adv1Review.isPresent());
//        assertTrue(adv2Review.isPresent());
//        assertTrue(adv3Review.isPresent());
//
//        assertEquals(adv1Review.get(), ADV1_REV);
//        assertEquals(adv2Review.get(), ADV2_REV);
//        assertEquals(adv3Review.get(), ADV3_REV);
//    }
//
//    @Test
//    public void testListByCategoryAndScore(){
//        List<ExperienceModel> experienceModelList = experienceDao.listByCategoryAndScore(1, 3);
//
//        for (ExperienceModel exp: experienceModelList) {
//            System.out.println(exp.getName());
//        }
//
//        assertFalse(experienceModelList.isEmpty());
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryCityAndScore() {
//        List<ExperienceModel> experienceModelList = experienceDao.listByCategoryCityAndScore(1, 1, 3);
//        assertFalse(experienceModelList.isEmpty());
//
//
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryPriceAndScore() {
//        List<ExperienceModel> experienceModelList = experienceDao.listByCategoryPriceAndScore(1,new Double(1750), 3);
//        assertFalse(experienceModelList.isEmpty());
//
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//    @Test
//    public void testListByCategoryPriceCityAndScore() {
//        List<ExperienceModel> experienceModelList = experienceDao.listByCategoryPriceCityAndScore(1, new Double(1750), 1, 3);
//        assertFalse(experienceModelList.isEmpty());
//
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertTrue(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//
//        experienceModelList = experienceDao.listByCategoryPriceCityAndScore(1, new Double(1750), 2, 3);
//        assertTrue(experienceModelList.isEmpty());
//
//        assertFalse(experienceModelList.contains(DEFAULT_ADV));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV2));
//        assertFalse(experienceModelList.contains(DEFAULT_ADV3));
//    }
//
//}
