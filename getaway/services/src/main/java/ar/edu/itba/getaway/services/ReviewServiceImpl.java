package ar.edu.itba.getaway.services;

import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.persistence.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    private ReviewDao reviewDao;


    @Override
    public ReviewModel create(String title, String description, long score, long experienceId, Date reviewDate) {
        return reviewDao.create(title,description,score,experienceId,reviewDate);
    }

    @Override
    public List<ReviewModel> getReviewsFromId(long experienceId) {
        return reviewDao.getReviewsFromId(experienceId);
    }
}
