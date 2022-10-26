//package ar.edu.itba.getaway.services;
//
//import ar.edu.itba.getaway.interfaces.exceptions.DuplicateUserException;
//import ar.edu.itba.getaway.interfaces.services.ImageService;
//import ar.edu.itba.getaway.models.*;
//import ar.edu.itba.getaway.interfaces.persistence.PasswordResetTokenDao;
//import ar.edu.itba.getaway.interfaces.persistence.UserDao;
//import ar.edu.itba.getaway.interfaces.persistence.VerificationTokenDao;
//import ar.edu.itba.getaway.interfaces.services.EmailService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.mail.MessagingException;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserServiceImplTest {
//    /**
//     * Data for tests
//     **/
//
//    private static final Long USER_ID = 1L;
//    private static final String PASSWORD = "password";
//    private static final String NEW_PASSWORD = "new_password";
//    private static final String NAME = "name";
//    private static final String SURNAME = "surname";
//    private static final String EMAIL = "example@example.com";
//    private static final Long PASSWORD_IMAGE_ID = 5L;
//    private static final Long VERIFICATION_TOKEN_ID = 1L;
//    private static final String TOKEN = "123456789";
//    private static final String URL = "http://pawserver.it.itba.edu.ar/paw-2022b-1/user/verifyAccount/" + TOKEN;
//    private static final String SUBJECT = "Verify";
//
//    private static final Collection<Roles> DEFAULT_ROLES =
//            Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));
//
//    private static final UserModel DEFAULT_USER =
//            new UserModel(USER_ID, PASSWORD, NAME, SURNAME, EMAIL, DEFAULT_ROLES, 1L);
//
//    private static final ImageModel IMAGE_MODEL = new ImageModel(1L, null);
//
//    private static final LocalDateTime DEFAULT_TIME =
//            LocalDateTime.ofEpochSecond(1619457499, 0, ZoneOffset.UTC);
//
//    private static final PasswordResetToken PASSWORD_RESET_TOKEN =
//            new PasswordResetToken(PASSWORD_IMAGE_ID, "", DEFAULT_USER.getUserId(),
//                    LocalDateTime.now().plusDays(4L));
//
//    private static final VerificationToken VERIFICATION_TOKEN =
//            new VerificationToken(VERIFICATION_TOKEN_ID, TOKEN, DEFAULT_USER.getUserId(),
//                    LocalDateTime.now().plusDays(4L));
//
//    private static final Map<String, Object> DEFAULT_MAIL_ATTRS = Stream.of(new String[][]{
//            {"confirmationURL", URL},
//            {"to", DEFAULT_USER.getEmail()},
//    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
//
//    /****/
//
//    @InjectMocks
//    private final UserServiceImpl userService = new UserServiceImpl();
//
//    @Mock
//    private UserDao mockUserDao;
//
//    @Mock
//    private VerificationTokenDao mockVerificationTokenDao;
//
//    @Mock
//    private EmailService mockEmailService;
//
//    @Mock
//    private PasswordEncoder mockEncoder;
//
//    @Mock
//    private ImageService mockImageService;
//    @Mock
//    private MessageSource mockMessageSource;
//
//    @Mock
//    private PasswordResetTokenDao mockPasswordResetTokenDao;
//
//    @Before
//    public void setTest() {
//        LocaleContextHolder.setLocale(Locale.ENGLISH);
//    }
//
////    @Test
////    public void testCreate() throws DuplicateUserException {
////        when(mockMessageSource.getMessage(anyString(), any(), Mockito.eq(LocaleContextHolder.getLocale()))).
////                thenReturn(SUBJECT);
////        when(mockVerificationTokenDao.createVerificationToken(Mockito.eq(DEFAULT_USER.getUserId()), anyString(), any(LocalDateTime.class)))
////                .thenReturn(new VerificationToken(VERIFICATION_TOKEN_ID, TOKEN, DEFAULT_USER.getUserId(), DEFAULT_TIME));
////        when(mockEncoder.encode(PASSWORD))
////                .thenReturn(PASSWORD);
////
////        when(mockImageService.createImg(Mockito.eq(IMAGE_MODEL.getImage()))).thenReturn(IMAGE_MODEL);
////        when(mockUserDao.createUser(Mockito.eq(PASSWORD), Mockito.eq(NAME), Mockito.eq(SURNAME),
////                Mockito.eq(EMAIL), Mockito.eq(DEFAULT_ROLES), Mockito.eq(IMAGE_MODEL.getImageId())))
////                .thenReturn(DEFAULT_USER);
////
////        UserModel maybeUserModel = userService.createUser(PASSWORD, NAME, SURNAME, EMAIL);
////
////        assertNotNull(maybeUserModel);
////        assertEquals(DEFAULT_USER, maybeUserModel);
////    }
////
////    @Test(expected = DuplicateUserException.class)
////    public void testCreateAlreadyExists() throws DuplicateUserException {
////        //Whether DEFAULT_USER already exists
////        when(mockEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
////        when(mockUserDao.createUser(anyString(), anyString(), anyString(),
////                eq(DEFAULT_USER.getEmail()), any(), anyLong()))
////                .thenThrow(new DuplicateUserException());
////
////        userService.createUser(PASSWORD, NAME, SURNAME, DEFAULT_USER.getEmail());
////    }
//
//    @Test
//    public void getUserByIdTest() {
//
//        when(mockUserDao.getUserById(eq(DEFAULT_USER.getUserId())))
//                .thenReturn(Optional.of(DEFAULT_USER));
//
//        final Optional<UserModel> optionalUserModel = userService.getUserById(DEFAULT_USER.getUserId());
//
//        assertNotNull(optionalUserModel);
//        assertTrue(optionalUserModel.isPresent());
//        assertEquals(DEFAULT_USER, optionalUserModel.get());
//    }
//
//    @Test
//    public void getUserByIdUnsuccessfullyTest() {
//
//        //An user that does not exists
//        final Long userId = -1234567890L;
//        when(mockUserDao.getUserById(eq(userId)))
//                .thenReturn(Optional.empty());
//
//        final Optional<UserModel> optionalUserModel = userService.getUserById(userId);
//
//        assertNotNull(optionalUserModel);
//        assertFalse(optionalUserModel.isPresent());
//    }
//
//    @Test
//    public void getUserByEmailTest() {
//
//        when(mockUserDao.getUserByEmail(eq(DEFAULT_USER.getEmail())))
//                .thenReturn(Optional.of(DEFAULT_USER));
//
//        final Optional<UserModel> optionalUserModel = userService.getUserByEmail(DEFAULT_USER.getEmail());
//
//        assertNotNull(optionalUserModel);
//        assertTrue(optionalUserModel.isPresent());
//        assertEquals(DEFAULT_USER, optionalUserModel.get());
//    }
//
//    @Test
//    public void verifyAccountTest() {
//        final Collection<Roles> newRoles = new LinkedList<>(DEFAULT_USER.getRoles());
//
//        newRoles.remove(Roles.NOT_VERIFIED);
//        newRoles.add(Roles.VERIFIED);
//
//        when(mockVerificationTokenDao.getTokenByValue(eq(TOKEN)))
//                .thenReturn(Optional.of(VERIFICATION_TOKEN));
//        Mockito.doNothing().when(mockVerificationTokenDao)
//                .removeTokenById(eq(VERIFICATION_TOKEN.getId()));
//        when(mockUserDao.updateRoles(eq(VERIFICATION_TOKEN.getId()), eq(Roles.NOT_VERIFIED), eq(Roles.VERIFIED)))
//                .thenReturn(Optional.of(new UserModel(DEFAULT_USER.getUserId(), DEFAULT_USER.getPassword(),
//                        DEFAULT_USER.getName(), DEFAULT_USER.getSurname(), DEFAULT_USER.getEmail(),
//                        newRoles, DEFAULT_USER.getProfileImageId())));
//
//        final Optional<UserModel> optionalUserModel = userService.verifyAccount(TOKEN);
//
//        assertNotNull(optionalUserModel);
//        assertTrue(optionalUserModel.isPresent());
//        assertTrue(optionalUserModel.get().getRoles().contains(Roles.VERIFIED));
//    }
//
//    @Test
//    public void updatePasswordTest() {
//
//        when(mockPasswordResetTokenDao.getTokenByValue(Mockito.eq(TOKEN)))
//                .thenReturn(Optional.of(PASSWORD_RESET_TOKEN));
//        Mockito.doNothing().when(mockPasswordResetTokenDao)
//                .removeTokenById(Mockito.eq(PASSWORD_RESET_TOKEN.getId()));
//        when(mockEncoder.encode(Mockito.eq(PASSWORD)))
//                .thenReturn(NEW_PASSWORD);
//        when(mockUserDao.updatePassword(Mockito.eq(PASSWORD_RESET_TOKEN.getUserId()), Mockito.anyString()))
//                .thenReturn(Optional.of(new UserModel(DEFAULT_USER.getUserId(), NEW_PASSWORD,
//                        DEFAULT_USER.getName(), DEFAULT_USER.getSurname(), DEFAULT_USER.getEmail(),
//                        DEFAULT_USER.getRoles(), DEFAULT_USER.getProfileImageId())));
//
//        final Optional<UserModel> optionalUserModel = userService.updatePassword(TOKEN, PASSWORD);
//
//        assertNotNull(optionalUserModel);
//        assertTrue(optionalUserModel.isPresent());
//        assertNotEquals(optionalUserModel.get().getPassword(), DEFAULT_USER.getPassword());
//        assertEquals(optionalUserModel.get(), DEFAULT_USER);
//
//    }
//
//}
