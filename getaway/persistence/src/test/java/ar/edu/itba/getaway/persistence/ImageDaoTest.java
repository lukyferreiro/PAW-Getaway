package ar.edu.itba.getaway.persistence;

import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.persistence.config.TestConfig;
import ar.edu.itba.getaway.interfaces.persistence.ImageDao;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Arrays;
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
    private DataSource ds;
    @PersistenceContext
    private EntityManager em;

    private static final byte[] imgInfo1 = {1, 2, 3, 4};
    private static final byte[] imgInfo2 = {5, 6, 7, 8};
    private static final byte[] imgInfo3 = {33, 18, 86};

    private static final String DEFAULT_TYPE = "JPG";

    private static final ImageModel TO_UPDATE_IMAGE = new ImageModel(1L, imgInfo1, DEFAULT_TYPE);
    private static final ImageModel TO_DELETE_IMAGE = new ImageModel(2L, imgInfo2, DEFAULT_TYPE);
    private static final ImageModel TO_GET_IMAGE = new ImageModel(3L, imgInfo3, DEFAULT_TYPE);


    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Rollback
    public void testCreateImg() {
        final ImageModel imageModel = imageDao.createImg(imgInfo1, DEFAULT_TYPE);
        assertNotNull(imageModel);
        assertEquals(imgInfo1, imageModel.getImage());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "images", "imgId = " + imageModel.getImageId()));
    }

    @Test
    @Rollback
    public void testUpdateImg() {
        imageDao.updateImg(imgInfo2, DEFAULT_TYPE, TO_UPDATE_IMAGE);
        ImageModel imageModel = em.find(ImageModel.class, TO_UPDATE_IMAGE.getImageId());
        assertNotNull(imageModel);
        assertArrayEquals(imgInfo2, imageModel.getImage());
    }

    @Test
    @Rollback
    public void testDeleteImg() {
        ImageModel toDeleteImage = em.find(ImageModel.class, TO_DELETE_IMAGE.getImageId());
        imageDao.deleteImg(toDeleteImage);
        em.flush();
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "images", "imgId = " + TO_DELETE_IMAGE.getImageId()));
    }

    @Test
    public void getImgById() {
        ImageModel imageModel = em.find(ImageModel.class, TO_GET_IMAGE.getImageId());
        assertNotNull(imageModel);
        assertEquals(TO_GET_IMAGE, imageModel);

        System.out.println(Arrays.toString(imageModel.getImage()));

        assertArrayEquals(imgInfo3, imageModel.getImage());
    }
}
