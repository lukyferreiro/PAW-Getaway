package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.ReviewNotFoundException;
import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.webapp.forms.DeleteForm;
import ar.edu.itba.getaway.webapp.forms.ReviewForm;
import ar.edu.itba.getaway.webapp.forms.SearchForm;
import ar.edu.itba.getaway.interfaces.services.ReviewService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class UserReviewsController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserReviewsController.class);

    @RequestMapping(value = "/user/reviews", method = {RequestMethod.GET})
    public ModelAndView review(Principal principal,
                               @ModelAttribute("searchForm") final SearchForm searchForm,
                               HttpServletRequest request,
                               Optional<Boolean> successReview,
                               Optional<Boolean> deleteReview,
                               @RequestParam(value = "pageNum", defaultValue = "1") final int pageNum) {
        LOGGER.debug("Endpoint GET {}", request.getServletPath());

        final ModelAndView mav = new ModelAndView("userReviews");
        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        final Page<ReviewModel> currentPage = reviewService.getReviewsByUser(user, pageNum);
        final List<ReviewModel> reviewList = currentPage.getContent();

        boolean hasImage = false;
        long imageId = -1;
        if(user.getProfileImage()!=null){
            hasImage = user.getProfileImage().getImage() != null;
            imageId = user.getProfileImage().getImageId();
        }

        mav.addObject("reviews", reviewList);
        mav.addObject("isEditing", true);
        mav.addObject("successReview", successReview.isPresent());
        mav.addObject("deleteReview", deleteReview.isPresent());
        mav.addObject("currentPage", currentPage.getCurrentPage());
        mav.addObject("minPage", currentPage.getMinPage());
        mav.addObject("maxPage", currentPage.getMaxPage());
        mav.addObject("totalPages", currentPage.getTotalPages());
        mav.addObject("hasImage", hasImage);
        mav.addObject("profileImageId", imageId);

        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canDeleteReviewById(authentication, #reviewId)")
    @RequestMapping(value = "/user/reviews/delete/{reviewId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewDelete(@PathVariable("reviewId") final Long reviewId,
                                     @ModelAttribute("deleteForm") final DeleteForm form,
                                     @ModelAttribute("searchForm") final SearchForm searchForm,
                                     HttpServletRequest request) {
        LOGGER.debug("Endpoint GET {}", request.getServletPath());

        final ModelAndView mav = new ModelAndView("deleteReview");
        final ReviewModel review = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);

        mav.addObject("review", review);

        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canDeleteReviewById(authentication, #reviewId)")
    @RequestMapping(value = "/user/reviews/delete/{reviewId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView reviewDeletePost(@PathVariable(value = "reviewId") final Long reviewId,
                                         @ModelAttribute("deleteForm") final DeleteForm form,
                                         @ModelAttribute("searchForm") final SearchForm searchForm,
                                         final BindingResult errors,
                                         HttpServletRequest request) {
        LOGGER.debug("Endpoint POST {}", request.getServletPath());

        if (errors.hasErrors()) {
            return reviewDelete(reviewId, form, searchForm, request);
        }

        final ReviewModel toDeleteReview = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);
        reviewService.deleteReview(toDeleteReview);

        ModelAndView mav = new ModelAndView("redirect:/user/reviews");
        mav.addObject("deleteReview", true);
        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canEditReviewById(authentication, #reviewId)")
    @RequestMapping(value = "/user/reviews/edit/{reviewId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView reviewEdit(@PathVariable("reviewId") final Long reviewId,
                                   @ModelAttribute("reviewForm") final ReviewForm form,
                                   @ModelAttribute("searchForm") final SearchForm searchForm,
                                   HttpServletRequest request) {
        LOGGER.debug("Endpoint GET {}", request.getServletPath());

        final ModelAndView mav = new ModelAndView("reviewEditForm");
        final ReviewModel review = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);

        form.setTitle(review.getTitle());
        form.setScore(review.getStringScore());
        form.setDescription(review.getDescription());

        if(form.getTitle() == null){
            form.setTitle(review.getTitle());
            form.setDescription(review.getDescription());
            form.setScore(review.getStringScore());
        } else {
            form.setTitle(form.getTitle());
            form.setDescription(form.getDescription());
            form.setScore(form.getScore());
        }

        mav.addObject("endpoint", request.getServletPath());
        mav.addObject("review", review);

        return mav;
    }

    @PreAuthorize("@antMatcherVoter.canEditReviewById(authentication, #reviewId)")
    @RequestMapping(value = "/user/reviews/edit/{reviewId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView reviewEditPost(@PathVariable(value = "reviewId") final Long reviewId,
                                       @Valid @ModelAttribute("reviewForm") final ReviewForm form,
                                       final BindingResult errors,
                                       @ModelAttribute("searchForm") final SearchForm searchForm,
                                       Principal principal,
                                       HttpServletRequest request) {
        LOGGER.debug("Endpoint POST {}", request.getServletPath());

        if (errors.hasErrors()) {
            return reviewEdit(reviewId, form, searchForm, request);
        }

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final ReviewModel review = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new);
        final ReviewModel newReviewModel = new ReviewModel(reviewId, form.getTitle(), form.getDescription(),
                form.getLongScore(),review.getExperience(), Date.from(Instant.now()), user);

        reviewService.updateReview(reviewId,newReviewModel);

        ModelAndView mav = new ModelAndView("redirect:/user/reviews");
        mav.addObject("successReview", true);

        return mav;
    }

}
