package ar.edu.itba.getaway.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InitPageController {

    @RequestMapping("/")
    public ModelAndView init(){
        return new ModelAndView("mainPage");
    }

}