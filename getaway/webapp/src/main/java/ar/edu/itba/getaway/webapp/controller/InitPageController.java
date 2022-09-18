package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.Roles;
import ar.edu.itba.getaway.models.UserModel;
import ar.edu.itba.getaway.services.UserService;
import ar.edu.itba.getaway.webapp.auth.MyUserDetails;
import ar.edu.itba.getaway.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InitPageController {

    @Autowired
    private UserService us;

    @RequestMapping("/")
    public ModelAndView init(@AuthenticationPrincipal MyUserDetails userDetails) {
        final ModelAndView mav = new ModelAndView("mainPage");
        try {
            String email = userDetails.getUsername();
            UserModel userModel = us.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
            mav.addObject("hasSign", userModel.hasRole(Roles.USER));
        } catch (NullPointerException e) {
            mav.addObject("hasSign", false);
        }
        return mav;
    }

}