package ar.edu.itba.getaway.webapp.controller.forms;

import ar.edu.itba.getaway.services.ExperienceService;
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
    ExperienceService experienceService;

    @RequestMapping(value = "/{categoryName}/{experienceId}/create_review", method = {RequestMethod.GET})
    public ModelAndView createReviewForm(@PathVariable("categoryName") final String categoryName,
                                         @PathVariable("experienceId") final long experienceId,
                                         @ModelAttribute("reviewForm") final ReviewForm form) {
        final ModelAndView mav = new ModelAndView("review_form");

        String dbCategoryName = "Crear Reseña";
        mav.addObject("dbCategoryName", dbCategoryName);

        return mav;
    }

    @RequestMapping(value = "/{categoryName}/{experienceId}/create_review", method = {RequestMethod.POST})
    public ModelAndView experienceWithReview(@PathVariable("categoryName") final String categoryName,
                                             @PathVariable("experienceId") final long experienceId,
                                             @Valid @ModelAttribute("reviewForm") final ReviewForm form,
                                       final BindingResult errors) {
        if (errors.hasErrors()) {
            return createReviewForm(categoryName, experienceId, form);
        }


        Date date = Date.from(Instant.now());
        System.out.println(date);
        return new ModelAndView("redirect:/" + categoryName + "/" + experienceId);
    }
}
