package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.services.CategoryService;
import ar.edu.itba.getaway.services.ExperienceService;
import ar.edu.itba.getaway.webapp.forms.ActivityForm;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class HelloWorldController {

    ExperienceService exp;
    CategoryService category;

    @RequestMapping("/")
    public ModelAndView helloWorld(){
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("username", "GETAWAY");
        return mav;
    }

    @RequestMapping("/adventures")
    public ModelAndView adventures() throws Exception {
        final ModelAndView mav = new ModelAndView("adventures");
        Optional<CategoryModel> cat =  category.getId("Aventura");

        if(cat.isPresent()){
            long id_adventure = cat.get().getId();
            List<ExperienceModel> experienceList = exp.listByCategory(id_adventure);

            if(experienceList == null){
                throw new Exception();
            }else{
                mav.addObject("activities", experienceList);
            }
        }

        return mav;
    }

    @RequestMapping("/adventures/{adventureId}")
    public ModelAndView adventuresView(@PathVariable("adventureId") final long adventureId){
        final ModelAndView mav = new ModelAndView("activity_card");

//        mav.addObject("activity", exp.getById(adventureId).orElseThrow(Exception::new));
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
//        ni idea como pasarle el id
//        final Activity act = exp.create(form.getActivityName(),form.getActivityAddress(), form.getActivityInfo(), form.getActivityCategory(), form.getActivityMail(), form.getActivityImg(), form.getActivityTags());

//        return new ModelAndView("redirect:/" + act.getCategory() + "/" + act.getId());
            return new ModelAndView("redirect:/adventures");
    }

}