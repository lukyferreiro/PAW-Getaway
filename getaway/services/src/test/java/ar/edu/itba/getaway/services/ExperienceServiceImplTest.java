package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.persistence.ExperienceDao;
import ar.edu.itba.getaway.persistence.UserDao;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExperienceServiceImplTest {

    @InjectMocks
    private ExperienceServiceImpl experienceService = new ExperienceServiceImpl();

    @Mock
    private ExperienceDao mockExperienceDao;

    @Mock
    private ImageService mockImageService;

    @Mock
    private UserDao userDao;

}
