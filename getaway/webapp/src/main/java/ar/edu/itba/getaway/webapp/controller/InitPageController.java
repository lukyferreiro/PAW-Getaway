package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.models.pagination.Page;
import ar.edu.itba.getaway.services.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class InitPageController {

    @Autowired
    private ExperienceService experienceService;

    private Page<ExperienceModel> experienceModelPage;

    //TODO hacer el post para que le llegue la page number

    @RequestMapping(value = {"/","page/{page}"}, method = {RequestMethod.GET})
    public ModelAndView init(@ModelAttribute("loggedUser") final UserModel loggedUser, @RequestParam(defaultValue = "1") int page) {
        final ModelAndView mav = new ModelAndView("mainPage");

        try {
            mav.addObject("loggedUser", loggedUser.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("loggedUser", false);
        }

        Page<ExperienceModel> experienceList = experienceService.listByScore(page,1);

        List<Long> avgReviews = new ArrayList<>();
        for(ExperienceModel experience : experienceList.getContent()){
           // avgReviews.add(experienceService.getAvgReviews(experience.getId()).get());
        }

        mav.addObject("activities", experienceList);
       // mav.addObject("avgReviews", avgReviews);

        return mav;
    }

}