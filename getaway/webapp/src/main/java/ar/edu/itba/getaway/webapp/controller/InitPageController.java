package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.ExperienceService;
import ar.edu.itba.getaway.services.FavExperienceService;
import ar.edu.itba.getaway.webapp.forms.FavExperienceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InitPageController {

    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private FavExperienceService favExperienceService;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView init(@ModelAttribute("loggedUser") final UserModel loggedUser,
                             @ModelAttribute("favExperienceForm") final FavExperienceForm favForm) {
        final ModelAndView mav = new ModelAndView("mainPage");

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
            List<Long> favExperienceModels = favExperienceService.listByUserId(loggedUser.getId());
            mav.addObject("favExperienceModels", favExperienceModels);
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        List<ExperienceModel> experienceList = experienceService.getRandom();

        List<Long> avgReviews = new ArrayList<>();
        for(ExperienceModel experience : experienceList){
            avgReviews.add(experienceService.getAvgReviews(experience.getId()).get());
        }

        mav.addObject("activities", experienceList);
        mav.addObject("avgReviews", avgReviews);

        return mav;
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    public ModelAndView initSet(@ModelAttribute("favExperienceForm") final FavExperienceForm favForm,
                                         @ModelAttribute("loggedUser") final UserModel loggedUser) {

        if(favForm.getFavExp()){
            favExperienceService.create(loggedUser.getId(), favForm.getExperienceId());
        }else{
            favExperienceService.delete(loggedUser.getId(), favForm.getExperienceId());
        }

        return new ModelAndView("redirect:/" );
    }

}