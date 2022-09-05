package ar.edu.itba.getaway.webapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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