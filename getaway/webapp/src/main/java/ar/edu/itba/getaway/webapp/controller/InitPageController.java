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

    @RequestMapping(value = "/create_experience", method = {RequestMethod.GET})
    public ModelAndView createActivityForm(@ModelAttribute("activityForm") final ActivityForm form){
        return new ModelAndView("form");
    }

    @RequestMapping(value = "/create_experience", method = {RequestMethod.POST})
    public ModelAndView createActivity(@Valid @ModelAttribute("activityForm") final ActivityForm form, final BindingResult errors){
        if(errors.hasErrors()){
            return createActivityForm(form);
        }
//        ni idea como pasarle el id
//        final Activity act = exp.create(form.getActivityName(),form.getActivityAddress(), form.getActivityInfo(), form.getActivityCategory(), form.getActivityMail(), form.getActivityImg(), form.getActivityTags());

//        return new ModelAndView("redirect:/" + act.getCategory() + "/" + act.getId());
            return new ModelAndView("experiences_page");
    }

}