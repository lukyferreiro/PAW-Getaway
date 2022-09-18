package ar.edu.itba.getaway.webapp.controller.forms;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ReviewModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.ExperienceService;
import ar.edu.itba.getaway.services.ReviewService;
import ar.edu.itba.getaway.services.UserService;
import ar.edu.itba.getaway.webapp.auth.MyUserDetails;
import ar.edu.itba.getaway.webapp.exceptions.AccessDeniedException;
import ar.edu.itba.getaway.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.webapp.forms.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    ExperienceService experienceService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/experiences/{categoryName}/{experienceId}/create_review", method = {RequestMethod.GET})
    public ModelAndView createReviewForm(@PathVariable("categoryName") final String categoryName,
                                         @PathVariable("experienceId") final long experienceId,
                                         @ModelAttribute("reviewForm") final ReviewForm form) {
        final ModelAndView mav = new ModelAndView("review_form");

        String dbCategoryName = "Crear Reseña";
        mav.addObject("dbCategoryName", dbCategoryName);

        return mav;
    }

    @RequestMapping(value = "/experiences/{categoryName}/{experienceId}/create_review", method = {RequestMethod.POST})
    public ModelAndView experienceWithReview(@PathVariable("categoryName") final String categoryName,
                                             @PathVariable("experienceId") final long experienceId,
                                             @Valid @ModelAttribute("reviewForm") final ReviewForm form,
                                             final BindingResult errors,
                                             @AuthenticationPrincipal MyUserDetails userDetails) {
        if (errors.hasErrors()) {
            return createReviewForm(categoryName, experienceId, form);
        }

        Date date = Date.from(Instant.now());

        long userId;
        try {
            String email = userDetails.getUsername();
            UserModel userModel = userService.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
            userId = userModel.getId();
        }catch (NullPointerException e){
            throw new AccessDeniedException();
        }

        final ReviewModel reviewModel = reviewService.create(form.getTitle(), form.getDescription(), form.getLongScore(), experienceId ,date, userId);

        return new ModelAndView("redirect:/experiences/" + categoryName + "/" + experienceId);
    }
}
