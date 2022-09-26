package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.exceptions.ReviewNotFoundException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.webapp.forms.DeleteForm;
import ar.edu.itba.getaway.webapp.forms.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Controller
public class UserReviewsController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = "/user/reviews", method = {RequestMethod.GET})
    public ModelAndView review(Principal principal) {
        final ModelAndView mav = new ModelAndView("user_reviews");

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final List<ReviewUserModel> reviewList = reviewService.getByUserId(user.getId());
        mav.addObject("reviews", reviewList);

        return mav;
    }

    @RequestMapping(value = "/user/reviews/delete/{reviewId}", method = {RequestMethod.GET})
    public ModelAndView reviewDelete(@PathVariable("reviewId") final long reviewId,
                                         @ModelAttribute("deleteForm") final DeleteForm form) {
        final ModelAndView mav = new ModelAndView("deleteReview");
//        final ReviewModel review = reviewService.getById(reviewId).get();
        final ReviewModel review = reviewService.getById(reviewId).orElseThrow(ReviewNotFoundException::new);

        mav.addObject("review", review);
        return mav;
    }

    @RequestMapping(value = "/user/reviews/delete/{reviewId}", method = {RequestMethod.POST})
    public ModelAndView reviewDeletePost(@PathVariable(value = "reviewId") final long reviewId,
                                             @ModelAttribute("deleteForm") final DeleteForm form,
                                             final BindingResult errors) {
        if (errors.hasErrors()) {
            return reviewDelete(reviewId, form);
        }

        reviewService.delete(reviewId);
        return new ModelAndView("redirect:/user/reviews");
    }

    @RequestMapping(value = "/user/reviews/edit/{reviewId}", method = {RequestMethod.GET})
    public ModelAndView reviewEdit(@PathVariable("reviewId") final long reviewId,
                                       @ModelAttribute("reviewForm") final ReviewForm form) {
        final ModelAndView mav = new ModelAndView("review_edit_form");


//        final ReviewModel review = reviewService.getById(reviewId).get();
        final ReviewModel review = reviewService.getById(reviewId).orElseThrow(ReviewNotFoundException::new);

        form.setTitle(review.getTitle());
        form.setScore(review.getStringScore());
        form.setDescription(review.getDescription());

        mav.addObject("review", review);

        return mav;
    }

    @RequestMapping(value = "/user/reviews/edit/{reviewId}", method = {RequestMethod.POST})
    public ModelAndView reviewEditPost(@PathVariable(value = "reviewId") final long reviewId,
                                           @ModelAttribute("reviewForm") final ReviewForm form,
                                           Principal principal,
                                           final BindingResult errors) {
        if (errors.hasErrors()) {
            return reviewEdit(reviewId, form);
        }

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final Long userId = user.getId();

//        final ReviewModel review = reviewService.getById(reviewId).get();
        final ReviewModel review = reviewService.getById(reviewId).orElseThrow(ReviewNotFoundException::new);

        final ReviewModel newReviewModel = new ReviewModel(reviewId, form.getTitle(), form.getDescription(),
                form.getLongScore(),review.getExperienceId(), Date.from(Instant.now()), userId);

        reviewService.update(reviewId,newReviewModel);

        return new ModelAndView("redirect:/user/reviews");
    }

}
