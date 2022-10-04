package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.interfaces.persistence.ReviewDao;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceImplTest {

    @InjectMocks
    private final ReviewService reviewService=new ReviewServiceImpl();

    @Mock
    private ReviewDao reviewDao;

    @Mock
    private UserService userService;

}
