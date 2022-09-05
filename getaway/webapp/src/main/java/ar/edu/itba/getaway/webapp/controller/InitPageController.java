package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.ExperienceCategory;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.services.CategoryService;
import ar.edu.itba.getaway.services.ExperienceService;
import ar.edu.itba.getaway.webapp.forms.ActivityForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class InitPageController {

    @RequestMapping("/")
    public ModelAndView init(){
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("username", "GETAWAY");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView userNotFound(){
        return new ModelAndView("404");
    }



}