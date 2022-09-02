package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.webapp.forms.ActivityForm;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class HelloWorldController {

//    ActivityService activity ;
    @RequestMapping("/")
    public ModelAndView helloWorld(){
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("username", "GETAWAY");
        return mav;
    }

    @RequestMapping("/adventures")
    public ModelAndView adventures(){
        return new ModelAndView("adventures");
    }

    @RequestMapping("/adventures/{adventureId}")
    public ModelAndView adventuresView(@PathVariable("adventureId") final long adventureId){
        final ModelAndView mav = new ModelAndView("activity_card");

//        mav.addObject("activity", activity.getActivityById(adventureId).orElseThrow(Exception::new));
        return mav;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView userNotFound(){
        return new ModelAndView("404");
    }

    @RequestMapping(value = "/experience_form", method = {RequestMethod.GET})
    public ModelAndView createActivityForm(@ModelAttribute("activityForm") final ActivityForm form){
        return new ModelAndView("form");
    }

    @RequestMapping(value = "/experience_form", method = {RequestMethod.POST})
    public ModelAndView createActivity(@Valid @ModelAttribute("activityForm") final ActivityForm form, final BindingResult errors){
        if(errors.hasErrors()){
            return createActivityForm(form);
        }
//        final Activity act = activity.create(form.getActivityName(), form.getActivityCategory(),form.getActivityAddress(), form.getActivityMail(), form.getActivityImg(), form.getActivityInfo(), form.getActivityTags());

//        return new ModelAndView("redirect:/" + act.getCategory() + "/" + act.getId());
            return new ModelAndView("redirect:/adventures");
    }

}