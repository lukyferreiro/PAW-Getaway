package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.VerificationToken;
import ar.edu.itba.getaway.persistence.PasswordResetTokenDao;
import ar.edu.itba.getaway.persistence.UserDao;
import ar.edu.itba.getaway.persistence.VerificationTokenDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    /** Data for tests **/
    private final static String PASSWORD = "password";
    private final static String NAME = "NAME";
    private final static String SURNAME = "SURNAME";
    private final static String EMAIL = "e@mail.com";

    private static final Collection<Roles> DEFAULT_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    private static final UserModel DEFAULT_USER = new UserModel(new Long(1), PASSWORD, NAME, SURNAME, EMAIL, DEFAULT_ROLES,null);

    private static final String TOKEN = "12345";
    private static final String URL = "http:/paw-2022b-1/user/verifyAccount/" + TOKEN;
    private static final LocalDateTime DEFAULT_TIME = LocalDateTime.ofEpochSecond(1619457499, 0, ZoneOffset.UTC);
    private static final String VERIFICATION_SUBJECT = "Verify your account";
    private static final Map<String, Object> DEFAULT_MAIL_ATTRS = Stream.of(new String[][]{
            {"confirmationURL", URL},
            {"to", DEFAULT_USER.getEmail()},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    /****/

    @InjectMocks
    private final UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private UserDao mockUserDao;

    @Mock
    private VerificationTokenDao mockVerificationTokenDao;

    @Mock
    private EmailService mockEmailService;

    @Mock
    private PasswordEncoder mockEncoder;

    @Mock
    private MessageSource mockMessageSource;

    @Mock
    private PasswordResetTokenDao mockPasswordResetTokenDao;

    @Before
    public void setTest() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
    }

    @Test
    public void testCreate() throws DuplicateUserException, MessagingException {
        // 1. Setup!
        when(mockMessageSource.getMessage(anyString(),any(),eq(LocaleContextHolder.getLocale()))).
                thenReturn(VERIFICATION_SUBJECT);
        when(mockVerificationTokenDao.createVerificationToken(eq(DEFAULT_USER.getId()), anyString(), any(LocalDateTime.class)))
                .thenReturn(new VerificationToken(1, TOKEN, DEFAULT_USER.getId(), DEFAULT_TIME));
        when(mockEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        when(mockUserDao.createUser(PASSWORD, NAME, SURNAME, EMAIL, null)).thenReturn(DEFAULT_USER);


        // 2. "ejercito" la class under test
        UserModel maybeUser = userService.createUser(PASSWORD, NAME, SURNAME, EMAIL);

        // 3. Asserts!
        verify(mockEmailService, times(1)).sendMail("verification", VERIFICATION_SUBJECT,
                DEFAULT_MAIL_ATTRS, LocaleContextHolder.getLocale());
        assertNotNull(maybeUser);
        assertEquals(PASSWORD, maybeUser.getPassword());
        assertEquals(NAME, maybeUser.getName());
        assertEquals(SURNAME, maybeUser.getSurname());
        assertEquals(EMAIL, maybeUser.getEmail());
        assertEquals(null, maybeUser.getProfileImageId());
    }
}
