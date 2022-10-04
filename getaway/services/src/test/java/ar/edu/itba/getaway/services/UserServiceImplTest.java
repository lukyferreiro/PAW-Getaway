package ar.edu.itba.getaway.services;

import ar.edu.itba.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.interfaces.persistence.PasswordResetTokenDao;
import ar.edu.itba.interfaces.persistence.UserDao;
import ar.edu.itba.interfaces.persistence.VerificationTokenDao;
import ar.edu.itba.interfaces.services.EmailService;
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
import java.util.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    /** Data for tests **/
    private final static String PASSWORD = "password";
    private final static String NAME = "NAME";
    private final static String SURNAME = "SURNAME";
    private final static String EMAIL = "e@mail.com";

    private static final Collection<Roles> DEFAULT_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    private static final UserModel DEFAULT_USER = new UserModel(new Long(1), PASSWORD, NAME, SURNAME, EMAIL, DEFAULT_ROLES,null);
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
    }
}
