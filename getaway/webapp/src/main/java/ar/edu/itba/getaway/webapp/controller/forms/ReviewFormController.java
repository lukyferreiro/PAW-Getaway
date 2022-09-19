package ar.edu.itba.getaway.webapp.controller.forms;

import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.ReviewService;
import ar.edu.itba.getaway.webapp.exceptions.AccessDeniedException;
import ar.edu.itba.getaway.webapp.forms.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;

@Controller
public class ReviewFormController {

    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = "/experiences/{categoryName}/{experienceId}/create_review", method = {RequestMethod.GET})
    public ModelAndView createReviewForm(@PathVariable("categoryName") final String categoryName,
                                         @PathVariable("experienceId") final long experienceId,
                                         @ModelAttribute("reviewForm") final ReviewForm form,
                                         @ModelAttribute("loggedUser") final UserModel loggedUser) {
        final ModelAndView mav =new ModelAndView("review_form");

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        return mav;
    }

    @RequestMapping(value = "/experiences/{categoryName}/{experienceId}/create_review", method = {RequestMethod.POST})
    public ModelAndView experienceWithReview(@PathVariable("categoryName") final String categoryName,
                                             @PathVariable("experienceId") final long experienceId,
                                             @Valid @ModelAttribute("reviewForm") final ReviewForm form,
                                             final BindingResult errors,
                                             @ModelAttribute("loggedUser") final UserModel loggedUser) {
        final ModelAndView mav = new ModelAndView("redirect:/experiences/" + categoryName + "/" + experienceId);

        if (errors.hasErrors()) {
            return createReviewForm(categoryName, experienceId, form, loggedUser);
        }

        Date date = Date.from(Instant.now());

        long userId;
        try {
            userId = loggedUser.getId();
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
            throw new AccessDeniedException();
        }

        final ReviewModel reviewModel = reviewService.create(form.getTitle(), form.getDescription(),
                form.getLongScore(), experienceId ,date, userId);

        return mav;
    }
}
