package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.webapp.forms.CityForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ExperienceController {
    @Autowired
    ExperienceService exp;
    @Autowired
    CategoryService category;
    @Autowired
    CityService cityService;


    @RequestMapping(value = "/{categoryName}", method = {RequestMethod.GET})
    public ModelAndView experience(@PathVariable("categoryName") final String categoryName, @ModelAttribute("filterForm") final CityForm form) {
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        ExperienceCategory category = ExperienceCategory.valueOf(categoryName);
        int id = category.ordinal() + 1 ;

        List<ExperienceModel> experienceList = exp.listByCategory(id);
        String dbCategoryName = category.getName();

        //Filtros
        List<CityModel> cityModels = cityService.listAll();

        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        return mav;
    }

    @RequestMapping("/{categoryName}/{categoryId}")
    public ModelAndView experienceView(@PathVariable("categoryName") final String categoryName, @PathVariable("categoryId") final long categoryId){
        final ModelAndView mav = new ModelAndView("experienceDetails");

        final ExperienceModel experience = exp.getById(categoryId).orElseThrow(ExperienceNotFoundException::new);
        String dbCategoryName = ExperienceCategory.valueOf(categoryName).getName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("activity", experience);
        return mav;
    }

    @RequestMapping(value = "/{categoryName}/city/{cityId}", method = {RequestMethod.GET})
    public ModelAndView experienceCity(@PathVariable("categoryName") final String categoryName, @PathVariable("cityId") final long cityId, @Valid @ModelAttribute("filterForm") final CityForm form){
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        ExperienceCategory category = ExperienceCategory.valueOf(categoryName);
        int id = category.ordinal()  + 1;
        String dbCategoryName = category.getName();
        List<ExperienceModel> experienceList = exp.listByCategoryAndCity(id, cityId);

        //Filtros
        List<CityModel> cityModels = cityService.listAll();

        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        return mav;
    }


    @RequestMapping(value = "/{categoryName}", method = {RequestMethod.POST})
    public ModelAndView experienceCity(@PathVariable("categoryName") final String categoryName,@Valid @ModelAttribute("filterForm") final CityForm form, final BindingResult errors){
        if(errors.hasErrors()){
            return experience(categoryName, form);
        }


        long cityId = cityService.getIdByName(form.getActivityCity()).get().getId();


        return new ModelAndView("redirect:/" + categoryName + "/city/" + cityId);

    }
}
