package ar.edu.itba.getaway.persistence.config;

import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.CountryModel;
import ar.edu.itba.getaway.interfaces.persistence.LocationDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:location-dao-test.sql")
public class LocationDaoTest {
    /** Data for tests **/
    private final static CountryModel COUNTRY_1 = new CountryModel(1L, "TestCountryFirst");
    private final static CountryModel COUNTRY_2 = new CountryModel(2L, "TestCountrySecond");

    private final static List<CountryModel> COUNTRY_LIST = new ArrayList<>(Arrays.asList(COUNTRY_1, COUNTRY_2));

    private final static CityModel CITY_1 = new CityModel(1L, COUNTRY_1, "Test FirstOne");
    private final static CityModel CITY_2 = new CityModel(2L, COUNTRY_1, "Test FirstTwo");
    private final static CityModel CITY_3 = new CityModel(3L, COUNTRY_2, "Test SecondOne");
    private final static CityModel CITY_4 = new CityModel(4L, COUNTRY_2, "Test SecondTwo");

    private final static List<CityModel> CITY_LIST = new ArrayList<>(Arrays.asList(CITY_1,CITY_2, CITY_3, CITY_4));
    /****/

    @Autowired
    private DataSource ds;

    @Autowired
    private LocationDao locationDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testListAllCities() {
        List<CityModel> cityModels = locationDao.listAllCities();
        assertEquals(CITY_LIST.size(), cityModels.size());
        assertTrue(cityModels.containsAll(CITY_LIST));
    }

    @Test
    public void testGetCityById() {
        Optional<CityModel> cityModel = locationDao.getCityById(2L);
        assertTrue(cityModel.isPresent());
        assertEquals(CITY_2, cityModel.get());
    }

    @Test
    public void testGetCityByName() {
        Optional<CityModel> cityModel = locationDao.getCityByName("Test SecondOne");
        assertTrue(cityModel.isPresent());
        assertEquals(CITY_3, cityModel.get());
    }

    @Test
    public void testGetCitiesByCountry() {
        List<CityModel> cityModels = locationDao.getCitiesByCountry(COUNTRY_1);
        assertEquals(2, cityModels.size());
        assertTrue(cityModels.contains(CITY_1));
        assertTrue(cityModels.contains(CITY_2));
        assertFalse(cityModels.contains(CITY_3));
        assertFalse(cityModels.contains(CITY_4));
    }

    @Test
    public void testListAllCountries() {
        List<CountryModel> countryModels = locationDao.listAllCountries();
        assertEquals(COUNTRY_LIST.size(), countryModels.size());
        assertTrue(countryModels.containsAll(COUNTRY_LIST));
    }

    @Test
    public void testGetCountryById() {
        Optional<CountryModel> countryModel = locationDao.getCountryById(2L);
        assertTrue(countryModel.isPresent());
        assertEquals(COUNTRY_2, countryModel.get());
    }

    @Test
    public void testGetCountryByName() {
        Optional<CountryModel> countryModel = locationDao.getCountryByName("TestCountryFirst");
        assertTrue(countryModel.isPresent());
        assertEquals(COUNTRY_1, countryModel.get());
    }
}
