package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.ExperienceService;
import ar.edu.itba.getaway.services.FavExperienceService;
import ar.edu.itba.getaway.services.ReviewService;
import ar.edu.itba.getaway.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class InitPageController {

    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private FavExperienceService favExperienceService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InitPageController.class);

    @RequestMapping(value = "/")
    public ModelAndView init(Principal principal,
                             @RequestParam Optional<Long> experience,
                             @RequestParam Optional<Boolean> set) {
        final ModelAndView mav = new ModelAndView("mainPage");

        final List<ExperienceModel> experienceList = experienceService.listAll("");

        final List<Long> avgReviews = new ArrayList<>();
        for (ExperienceModel exp : experienceList) {
            avgReviews.add(reviewService.getAverageScore(exp.getExperienceId()));
        }

        LOGGER.debug("Size of avgReviews {}", avgReviews.size());

        if (principal != null) {
            final Optional<UserModel> user = userService.getUserByEmail(principal.getName());

            if (user.isPresent()) {
                final long userId = user.get().getId();
                List<Long> favExperienceModels = favExperienceService.listByUserId(userId);

                if (set.isPresent() && experience.isPresent()) {
                    if (set.get()) {
                        if (!favExperienceModels.contains(experience.get()))
                            favExperienceService.create(userId, experience.get());
                    } else {
                        favExperienceService.delete(userId, experience.get());
                    }
                }

                favExperienceModels = favExperienceService.listByUserId(userId);
                mav.addObject("favExperienceModels", favExperienceModels);
            } else {
                mav.addObject("favExperienceModels", new ArrayList<>());
            }

        }

        List<List<ExperienceModel>> listByCategory = new ArrayList<>();
        for(int i=0 ; i<=5 ; i++){
            listByCategory.add(new ArrayList<>());
            listByCategory.get(i).addAll(experienceService.listByBestRanked(i + 1));
        }


        mav.addObject("listByCategory", listByCategory);


        mav.addObject("experiences", experienceList);
        mav.addObject("avgReviews", avgReviews);
        mav.addObject("isEditing", false);

        return mav;
    }

}