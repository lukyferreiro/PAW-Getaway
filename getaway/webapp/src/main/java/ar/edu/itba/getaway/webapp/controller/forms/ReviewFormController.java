package ar.edu.itba.getaway.webapp.controller.forms;

import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.ReviewService;
import ar.edu.itba.getaway.services.UserService;
import ar.edu.itba.getaway.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.webapp.forms.ReviewForm;
import ar.edu.itba.getaway.webapp.forms.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.time.Instant;
import java.util.Date;

@Controller
public class ReviewFormController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewFormController.class);

    @RequestMapping(value = "/experiences/{categoryName:[A-Za-z_]+}/{experienceId:[0-9]+}/create_review", method = {RequestMethod.GET})
    public ModelAndView createReviewForm(@PathVariable("categoryName") final String categoryName,
                                         @PathVariable("experienceId") final long experienceId,
                                         @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                         @ModelAttribute("reviewForm") final ReviewForm form) {
        return new ModelAndView("review_form");
    }

    @RequestMapping(value = "/experiences/{categoryName:[A-Za-z_]+}/{experienceId:[0-9]+}/create_review", method = {RequestMethod.POST})
    public ModelAndView experienceWithReview(@PathVariable("categoryName") final String categoryName,
                                             @PathVariable("experienceId") final long experienceId,
                                             @Valid @ModelAttribute("reviewForm") final ReviewForm form,
                                             @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                             final BindingResult errors,
                                             Principal principal) {
        final ModelAndView mav = new ModelAndView("redirect:/experiences/" + categoryName + "/" + experienceId);

        if (errors.hasErrors()) {
            return createReviewForm(categoryName, experienceId, searchForm,form);
        }

        final Date date = Date.from(Instant.now());

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final Long userId = user.getId();

        reviewService.create(form.getTitle(), form.getDescription(), form.getLongScore(), experienceId ,date, userId);

        return mav;
    }
}
