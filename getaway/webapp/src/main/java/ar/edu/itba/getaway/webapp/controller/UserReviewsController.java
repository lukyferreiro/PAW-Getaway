package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.ReviewNotFoundException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.webapp.forms.DeleteForm;
import ar.edu.itba.getaway.webapp.forms.ReviewForm;
import ar.edu.itba.getaway.webapp.forms.SearchForm;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.ImageService;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserReviewsController {

    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private ReviewService reviewService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserReviewsController.class);

    @RequestMapping(value = "/user/reviews", method = {RequestMethod.GET})
    public ModelAndView review(Principal principal,
                               @ModelAttribute("searchForm") final SearchForm searchForm) {
        final ModelAndView mav = new ModelAndView("userReviews");

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final List<ReviewUserModel> reviewList = reviewService.getReviewsByUserId(user.getUserId());

        final List<Boolean> listReviewsHasImages = new ArrayList<>();
        final List<ExperienceModel> listExperiencesOfReviews = new ArrayList<>();
        for(ReviewUserModel review : reviewList){
            listReviewsHasImages.add(imageService.getImgById(review.getImgId()).get().getImage() != null);
            listExperiencesOfReviews.add(experienceService.getExperienceById(review.getExperienceId()).get());
        }

        mav.addObject("reviews", reviewList);
        mav.addObject("listReviewsHasImages", listReviewsHasImages);
        mav.addObject("listExperiencesOfReviews", listExperiencesOfReviews);
        mav.addObject("isEditing", true);

        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canDeleteReviewById(authentication, #reviewId)")
    @RequestMapping(value = "/user/reviews/delete/{reviewId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewDelete(@PathVariable("reviewId") final long reviewId,
                                     @ModelAttribute("deleteForm") final DeleteForm form,
                                     @ModelAttribute("searchForm") final SearchForm searchForm) {
        final ModelAndView mav = new ModelAndView("deleteReview");
        final ReviewModel review = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);

        mav.addObject("review", review);

        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canDeleteReviewById(authentication, #reviewId)")
    @RequestMapping(value = "/user/reviews/delete/{reviewId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView reviewDeletePost(@PathVariable(value = "reviewId") final long reviewId,
                                         @ModelAttribute("deleteForm") final DeleteForm form,
                                         @ModelAttribute("searchForm") final SearchForm searchForm,
                                         final BindingResult errors) {
        if (errors.hasErrors()) {
            return reviewDelete(reviewId, form,searchForm);
        }

        reviewService.deleteReview(reviewId);
        return new ModelAndView("redirect:/user/reviews");
    }

    @PreAuthorize("@antMatcherVoter.canEditReviewById(authentication, #reviewId)")
    @RequestMapping(value = "/user/reviews/edit/{reviewId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewEdit(@PathVariable("reviewId") final long reviewId,
                                   @ModelAttribute("reviewForm") final ReviewForm form,
                                   @ModelAttribute("searchForm") final SearchForm searchForm) {
        final ModelAndView mav = new ModelAndView("reviewEditForm");

        final ReviewModel review = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);

        form.setTitle(review.getTitle());
        form.setScore(review.getStringScore());
        form.setDescription(review.getDescription());

        final String endpoint = "/user/reviews/edit/" + reviewId;
        mav.addObject("endpoint", endpoint);
        mav.addObject("review", review);

        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canEditReviewById(authentication, #reviewId)")
    @RequestMapping(value = "/user/reviews/edit/{reviewId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView reviewEditPost(@PathVariable(value = "reviewId") final long reviewId,
                                       @ModelAttribute("reviewForm") final ReviewForm form,
                                       Principal principal,
                                       @ModelAttribute("searchForm") final SearchForm searchForm,
                                       final BindingResult errors) {
        if (errors.hasErrors()) {
            return reviewEdit(reviewId, form,searchForm);
        }

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final Long userId = user.getUserId();

//        final ReviewModel review = reviewService.getById(reviewId).get();
        final ReviewModel review = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);

        final ReviewModel newReviewModel = new ReviewModel(reviewId, form.getTitle(), form.getDescription(),
                form.getLongScore(),review.getExperienceId(), Date.from(Instant.now()), userId);

        reviewService.updateReview(reviewId,newReviewModel);

        return new ModelAndView("redirect:/user/reviews");
    }

}
