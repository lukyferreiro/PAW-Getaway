package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.models.ImageExperienceModel;
import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.persistence.ExperienceDao;
import ar.edu.itba.getaway.persistence.ImageDao;
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

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:image-dao-test.sql")
public class ImageDaoTest {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ExperienceDao experienceDao;

    @Autowired
    private DataSource ds;

    private static final byte[] imgInfo1={0,1,2,3,4,5,6,7,8,9};
    private static final byte[] imgInfo2={0,9,8,7,6,5,4,3,2,1};
    private static final byte[] imgInfo3={2,2,1,3,0,4,9,4,9,9};

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Rollback
    public void testCreateImg() {
        final ImageModel imageModel = imageDao.createImg(imgInfo1);
        assertNotNull(imageModel);
        assertEquals(imgInfo1, imageModel.getImage());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "images", "imgId = " + imageModel.getId()));
    }

//    @Test
//    @Rollback
//    public void testUpdateImg() {
//        assertTrue(imageDao.updateImg(imgInfo2,8));
//        Optional<ImageModel> imageModel = imageDao.getImgById(8);
//        assertEquals(imgInfo2,imageModel.get().getImage());
//    }

    @Test
    @Rollback
    public void testDeleteImg() {
        assertTrue(imageDao.deleteImg(8));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "images", "imgId = 8"));
    }

    @Test
    public void getImgById() {
        final Optional<ImageModel> imageModel = imageDao.getImgById(8);
        assertTrue(imageModel.isPresent());
        assertNull(imageModel.get().getImage());
    }

//    @Test
//    @Rollback
//    public void createExperienceImg() {
//        final ImageExperienceModel imageExperienceModel = imageDao.createExperienceImg(imgInfo3, 8,true);
//        assertEquals(imgInfo3, imageDao.getImgById(imageExperienceModel.getImageId()).get().getImage());
//        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "imagesExperiences", "imgId = " + imageExperienceModel.getImageId() + " AND experienceId = " + imageExperienceModel.getExperienceId()));
//    }

    //TODO: modify later with an actual bytea
//    @Test
//    public void testGetImgByExperienceId() {
//        final Optional<ImageModel> imageModel = imageDao.getImgByExperienceId(8);
////        assertEquals(true, imageModel.isPresent());
//        assertNull(imageModel);
//    }
}
