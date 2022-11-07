package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.interfaces.persistence.FavAndViewExperienceDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:favandviewexperience-dao-test.sql")
public class FavAndViewExperienceDaoTest {
    /** Data for tests **/

    private final List<Long> USER_FAVOURITES = new ArrayList<>(Arrays.asList(1L, 5L));

    /****/

    @Autowired
    private DataSource ds;

    @Autowired
    private FavAndViewExperienceDao favAndViewExperienceDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    @Rollback
    public void testAddFav() {

    }

    @Test
    @Rollback
    public void testDeleteFav() {

    }

    @Test
    @Rollback
    public void testAddViewed() {

    }
}
