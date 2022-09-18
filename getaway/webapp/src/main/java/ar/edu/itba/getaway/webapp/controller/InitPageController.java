package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.ExperienceService;
import ar.edu.itba.getaway.services.UserService;
import ar.edu.itba.getaway.webapp.auth.MyUserDetails;
import ar.edu.itba.getaway.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class InitPageController {

    @Autowired
    private UserService userService;
    @Autowired
    ExperienceService experienceService;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView init(@AuthenticationPrincipal MyUserDetails userDetails) {
        final ModelAndView mav = new ModelAndView("mainPage");

        try {
            String email = userDetails.getUsername();
            UserModel userModel = userService.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
            mav.addObject("hasSign", userModel.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("hasSign", false);
        }

        List<ExperienceModel> experienceList = experienceService.getRandom();

        mav.addObject("activities", experienceList);
        return mav;
    }

}