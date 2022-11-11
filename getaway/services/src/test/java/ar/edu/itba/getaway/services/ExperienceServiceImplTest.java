package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.interfaces.persistence.ExperienceDao;
import ar.edu.itba.getaway.interfaces.services.ImageService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperienceServiceImplTest {
    /** Data for tests **/
    private final long EXP_USER = 1L;
    private final static String PASSWORD = "password";
    private final static String NAME = "NAME";
    private final static String SURNAME = "SURNAME";
    private final static String EMAIL = "e@mail.com";
    private final static RoleModel PROVIDER_MODEL = new RoleModel(1L, Roles.PROVIDER);
    private final static RoleModel USER_MODEL = new RoleModel(2L, Roles.USER);
    private final static RoleModel VERIFIED_MODEL = new RoleModel(3L, Roles.VERIFIED);
    private final static RoleModel NOT_VERIFIED_MODEL = new RoleModel(4L, Roles.NOT_VERIFIED);
    private final static Collection<RoleModel> DEFAULT_ROLES_MODELS = new ArrayList<>(Arrays.asList(USER_MODEL, NOT_VERIFIED_MODEL));
    private final static ImageModel IMAGE = new ImageModel( null, 15L);
    private final UserModel defaultUser = new UserModel(EXP_USER, PASSWORD, NAME, SURNAME, EMAIL, DEFAULT_ROLES_MODELS, IMAGE);

    private final long EXP_ID = 1L;
    private final String EXP_NAME = "TestName";
    private final String EXP_ADD = "TestAdd";
    private final String EXP_DESC = "TestDesc";
    private final String EXP_MAIL = "test@email.com";
    private final String EXP_URL = "http://test.com";
    private final Double EXP_PRICE = 100D;
    private final CountryModel EXP_COUNTRY = new CountryModel(1L, "Argentina");
    private final CityModel EXP_CITY = new CityModel(1L, EXP_COUNTRY, "ciudad");
    private final CategoryModel EXP_CAT = new CategoryModel(1L, "Aventura");
    private final byte[] EXP_IMG = null;
    private final boolean EXP_HAS_IMG = false;

    private final ExperienceModel defaultExp = new ExperienceModel(EXP_ID, EXP_NAME, EXP_ADD, EXP_DESC, EXP_MAIL, EXP_URL,
            EXP_PRICE, EXP_CITY, EXP_CAT, defaultUser, IMAGE, true, 0);
//    private final ImageExperienceModel defaultImgExp = new ImageExperienceModel(EXP_IMG_ID, EXP_ID, true);
//    private final ImageModel defaultImg = new ImageModel(EXP_IMG_ID, EXP_IMG);

    /** **/

    @InjectMocks
    private ExperienceServiceImpl experienceService = new ExperienceServiceImpl();

    @Mock
    private ExperienceDao mockExperienceDao;

    @Mock
    private ImageService mockImageService;

    @Mock
    private UserService mockUserService;

    @Test
    public void testCreateExperience(){
        when(mockExperienceDao.createExperience(Mockito.eq(EXP_NAME), Mockito.eq(EXP_ADD),
                Mockito.eq(EXP_DESC), Mockito.eq(EXP_MAIL), Mockito.eq(EXP_URL),
                Mockito.eq(EXP_PRICE), Mockito.eq(EXP_CITY), Mockito.eq(EXP_CAT), Mockito.eq(defaultUser),
                Mockito.eq(IMAGE))).thenReturn(defaultExp);

        when(mockUserService.getUserById(EXP_USER)).thenReturn(Optional.of(defaultUser));

        experienceService.createExperience(EXP_NAME, EXP_ADD, EXP_DESC, EXP_MAIL, EXP_URL, EXP_PRICE, EXP_CITY, EXP_CAT, defaultUser, EXP_IMG);

        Mockito.verify(mockExperienceDao).createExperience(EXP_NAME, EXP_ADD, EXP_DESC, EXP_MAIL, EXP_URL, EXP_PRICE, EXP_CITY, EXP_CAT, defaultUser, IMAGE);
    }

    @Test
    public void  testGetVisibleExperienceById(){

    }

    @Test
    public void  testListExperiencesByFilter(){

    }

    @Test
    public void  testListExperiencesByBestRanked(){

    }

    @Test
    public void testListExperiencesFavsByUser(){

    }

    @Test
    public void testListExperiencesSearch(){

    }

    @Test
    public void testGetExperiencesListByCategories(){

    }

    @Test
    public void testListExperiencesListByUser(){

    }

    @Test
    public void testIncreaseViews(){

    }

    @Test
    public void testChangeVisibility(){

    }
}
