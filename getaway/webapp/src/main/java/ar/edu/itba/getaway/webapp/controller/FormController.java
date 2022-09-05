package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.CategoryModel;
import ar.edu.itba.getaway.models.CityModel;
import ar.edu.itba.getaway.models.ExperienceCategory;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.services.CategoryService;
import ar.edu.itba.getaway.services.CityService;
import ar.edu.itba.getaway.services.ExperienceService;
import ar.edu.itba.getaway.webapp.forms.ActivityForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FormController {
    @Autowired
    ExperienceService exp;
    @Autowired
    CityService cityService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/create_experience", method = {RequestMethod.GET})
    public ModelAndView createActivityForm(@ModelAttribute("experienceForm") final ActivityForm form){
        ModelAndView mav = new ModelAndView("form");
        ExperienceCategory[] categoryModels = ExperienceCategory.values();
        List<String> categories = new ArrayList<>();
        for (int i = 0 ; i < categoryModels.length ; i++){
            categories.add(categoryModels[i].getName());
        }

        List<CityModel> cityModels = cityService.listAll();

        mav.addObject("categories", categories);
        mav.addObject("cities", cityModels);

        return mav;
    }

    @RequestMapping(value = "/create_experience", method = {RequestMethod.POST})
    public ModelAndView createActivity(@Valid @ModelAttribute("experienceForm") final ActivityForm form, final BindingResult errors) throws Exception {
        ModelAndView mav = new ModelAndView("experiences_page");

        if(errors.hasErrors()){
            return createActivityForm(form);
        }
//        final ExperienceModel experienceModel = new ExperienceModel();
//        experienceModel.setName(form.getActivityName());
//        experienceModel.setAddress(form.getActivityAddress());
//        long i = form.getActivityCategory();
//        if(i <= 0){
//            throw new Exception();
//        }
//        experienceModel.setCategoryId(form.getActivityCategoryId());
//        experienceModel.setDescription(form.getActivityInfo());
//        experienceModel.setPrice(form.getActivityPrice());
//        //GET PARA VER Q ID TIENE LA CITY
//        experienceModel.setCityId(form.getActivityCity());
//        experienceModel.setSiteUrl(form.getActivityUrl());
        //        ni idea como pasarle el id
//        final Activity act = exp.create(form.getActivityName(),form.getActivityAddress(), form.getActivityInfo(), form.getActivityCategory(), form.getActivityMail(), form.getActivityImg(), form.getActivityTags());

//        return new ModelAndView("redirect:/" + act.getCategory() + "/" + act.getId());
        return mav;
    }
}
