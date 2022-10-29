package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.FavExperienceService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import ar.edu.itba.getaway.webapp.forms.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(InitPageController.class);

    @RequestMapping(value = "/")
    public ModelAndView init(Principal principal,
                             @RequestParam Optional<Long> experience,
                             @RequestParam Optional<Boolean> set,
                             @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                             HttpServletRequest request) {
        LOGGER.debug("Endpoint GET {}", request.getServletPath());
        final ModelAndView mav = new ModelAndView("mainPage");


        UserModel user = null;
        if (principal != null) {
            final Optional<UserModel> maybeUser = userService.getUserByEmail(principal.getName());
            if(maybeUser.isPresent()){
                user = maybeUser.get();
            }
        }

        if (user != null) {
            if (experience.isPresent()) {
                final Optional<ExperienceModel> addFavExperience = experienceService.getVisibleExperienceById(experience.get(), user);
                favExperienceService.setFav(user, set, addFavExperience);
            }
        } else {
            if (set.isPresent()) {
                return new ModelAndView("redirect:/login");
            }
        }

        final List<List<ExperienceModel>> listByCategory = experienceService.getExperiencesListByCategories(user);

        mav.addObject("listByCategory", listByCategory);
        return mav;
    }

}