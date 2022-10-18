//package ar.edu.itba.getaway.services;
//
//import ar.edu.itba.getaway.interfaces.persistence.ExperienceDao;
//import ar.edu.itba.getaway.interfaces.persistence.ImageDao;
//import ar.edu.itba.getaway.interfaces.persistence.UserDao;
//import ar.edu.itba.getaway.interfaces.services.ImageService;
//import ar.edu.itba.getaway.interfaces.services.UserService;
//import ar.edu.itba.getaway.models.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Optional;
//
//import static org.mockito.Mockito.lenient;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ExperienceServiceImplTest {
//    /** Data for tests **/
//    private final Long EXP_USER = 1L;
//    private final static String PASSWORD = "password";
//    private final static String NAME = "NAME";
//    private final static String SURNAME = "SURNAME";
//    private final static String EMAIL = "e@mail.com";
//    private static final Collection<Roles> DEFAULT_ROLES = new ArrayList<>(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));
//    private static final Long PFP_IMG = 1L;
//    private final UserModel defaultUser = new UserModel(EXP_USER, PASSWORD, NAME, SURNAME, EMAIL, DEFAULT_ROLES, PFP_IMG);
//
//    private final Long EXP_ID = 1L;
//    private final String EXP_NAME = "TestName";
//    private final String EXP_ADD = "TestAdd";
//    private final String EXP_DESC = "TestDesc";
//    private final String EXP_MAIL = "test@email.com";
//    private final String EXP_URL = "http://test.com";
//    private final Double EXP_PRICE = 100D;
//    private final Long EXP_CITY = 1L;
//    private final Long EXP_CAT = 1L;
//    private final byte[] EXP_IMG = null;
//    private final Long EXP_IMG_ID = 1L;
//    private final boolean EXP_HAS_IMG = false;
//
//    private final ExperienceModel defaultExp = new ExperienceModel(EXP_ID,EXP_NAME, EXP_ADD, EXP_DESC,  EXP_URL, EXP_MAIL,
//            EXP_PRICE, EXP_CITY, EXP_CAT, EXP_USER, EXP_IMG_ID, EXP_HAS_IMG);
//    private final ImageExperienceModel defaultImgExp = new ImageExperienceModel(EXP_IMG_ID, EXP_ID, true);
//    private final ImageModel defaultImg = new ImageModel(EXP_IMG_ID, EXP_IMG);
//
//    /** **/
//
//    @InjectMocks
//    private ExperienceServiceImpl experienceService = new ExperienceServiceImpl();
//
//    @Mock
//    private ExperienceDao mockExperienceDao;
//
//    @Mock
//    private ImageService mockImageService;
//
//    @Mock
//    private UserService mockUserService;
//
//    @Test
//    public void testCreateExperience(){
//        when(mockExperienceDao.createExperience(Mockito.eq(EXP_NAME), Mockito.eq(EXP_ADD),
//                Mockito.eq(EXP_DESC), Mockito.eq(EXP_MAIL), Mockito.eq(EXP_URL),
//                Mockito.eq(EXP_PRICE), Mockito.eq(EXP_CITY), Mockito.eq(EXP_CAT), Mockito.eq(EXP_USER))).thenReturn(defaultExp);
//
//        when(mockImageService.createExperienceImg(Mockito.eq(EXP_IMG), Mockito.eq(EXP_ID), Mockito.eq(true)))
//                .thenReturn(defaultImgExp);
//
//        when(mockUserService.getUserById(EXP_USER)).thenReturn(Optional.of(defaultUser));
//
//        experienceService.createExperience(EXP_NAME, EXP_ADD, EXP_DESC, EXP_MAIL, EXP_URL, EXP_PRICE, EXP_CITY, EXP_CAT, EXP_USER, EXP_IMG);
//
//        Mockito.verify(mockExperienceDao).createExperience(EXP_NAME, EXP_ADD, EXP_DESC, EXP_MAIL, EXP_URL, EXP_PRICE, EXP_CITY, EXP_CAT, EXP_USER);
//    }
//}
