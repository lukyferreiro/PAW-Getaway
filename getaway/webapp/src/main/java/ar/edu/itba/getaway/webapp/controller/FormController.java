package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
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
    @Autowired
    TagService tagService;

    @RequestMapping(value = "/create_experience", method = {RequestMethod.GET})
    public ModelAndView createActivityForm(@ModelAttribute("experienceForm") final ExperienceForm form){
        ModelAndView mav = new ModelAndView("form");
        ExperienceCategory[] categoryModels = ExperienceCategory.values();
        List<String> categories = new ArrayList<>();
        for (int i = 0 ; i < categoryModels.length ; i++){
            categories.add(categoryModels[i].getName());
        }
        List<CityModel> cityModels = cityService.listAll();
        List<TagModel> tagModels = tagService.listAll();

        mav.addObject("categories", categories);
        mav.addObject("cities", cityModels);
        mav.addObject("tags", tagModels);
        return mav;
    }

    @RequestMapping(value = "/create_experience", method = {RequestMethod.POST})
    public ModelAndView createActivity(@Valid @ModelAttribute("experienceForm") final ExperienceForm form, final BindingResult errors) throws Exception {

        if(errors.hasErrors()){
            return createActivityForm(form);
        }

        long categoryId = form.getActivityCategoryId();
        if(categoryId <= 0){
            throw new Exception();
        }

        List<CityModel> cityModels = cityService.listAll();

        int cityId = 0;
        boolean flag = true;
        for (int j = 0; j<cityModels.size() && flag ; j++){
            if(cityModels.get(j).getName().equals(form.getActivityCity())){
                cityId = j;
                flag=false;
            }
        }

        int userId = 1 ; //USUARIO FORZADO
        final ExperienceModel experienceModel = exp.create(form.getActivityName(),form.getActivityAddress(), form.getActivityInfo(), form.getActivityUrl(), form.getActivityPrice(), cityId , categoryId, userId);

        return new ModelAndView("redirect:/" + experienceModel.getCategoryName() + "/" + experienceModel.getId());
    }
}
