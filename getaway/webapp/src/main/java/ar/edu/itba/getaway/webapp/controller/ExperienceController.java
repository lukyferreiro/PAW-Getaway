package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.services.*;
import ar.edu.itba.getaway.webapp.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.webapp.forms.FilterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ExperienceController {

    @Autowired
    ExperienceService exp;
//    @Autowired
//    CategoryService category;
    @Autowired
    CityService cityService;

    @RequestMapping(value = "/{categoryName}", method = {RequestMethod.GET})
    public ModelAndView experience(@PathVariable("categoryName") final String categoryName,
                                   @ModelAttribute("filterForm") final FilterForm form,
                                   @RequestParam Optional<Long> cityId,
                                   @RequestParam Optional<Long> maxPrice) {
        final ModelAndView mav = new ModelAndView("experiences");

        // Ordinal empieza en 0
        ExperienceCategory category = ExperienceCategory.valueOf(categoryName);
        String dbCategoryName = category.getName();
        int id = category.ordinal() + 1 ;
        List<ExperienceModel> experienceList;
        List<CityModel> cityModels = cityService.listAll();

        //Filtros
        if(cityId.isPresent()){
            if(maxPrice.isPresent() && maxPrice.get() > 0){
                experienceList = exp.listByCategoryPriceAndCity(id, maxPrice.get(), cityId.get());
            }else{
                experienceList = exp.listByCategoryAndCity(id, cityId.get());
            }
        }else if(maxPrice.isPresent() && maxPrice.get()>0){
            experienceList = exp.listByCategoryAndPrice(id,maxPrice.get());
        }else{
            experienceList = exp.listByCategory(id);
        }

        mav.addObject("cities", cityModels);
        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("categoryName", categoryName);
        mav.addObject("activities", experienceList);
        return mav;
    }

    @RequestMapping("/{categoryName}/{categoryId}")
    public ModelAndView experienceView(@PathVariable("categoryName") final String categoryName,
                                       @PathVariable("categoryId") final long categoryId){
        final ModelAndView mav = new ModelAndView("experienceDetails");

        final ExperienceModel experience = exp.getById(categoryId).orElseThrow(ExperienceNotFoundException::new);
        String dbCategoryName = ExperienceCategory.valueOf(categoryName).getName();

        mav.addObject("dbCategoryName", dbCategoryName);
        mav.addObject("activity", experience);
        return mav;
    }

    @RequestMapping(value = "/{categoryName}", method = {RequestMethod.POST})
    public ModelAndView experienceCity(@PathVariable("categoryName") final String categoryName,
                                       @Valid @ModelAttribute("filterForm") final FilterForm form,
                                       final BindingResult errors){
        final ModelAndView mav = new ModelAndView("redirect:/" + categoryName);

        if(errors.hasErrors()){
            return experience(categoryName, form, Optional.empty(), Optional.empty());
        }

        Optional<CityModel> cityModel = cityService.getIdByName(form.getActivityCity());
//        List<ExperienceModel> experienceModel = exp.listByCategoryAndPrice(form.getActivityPriceMax());
        if(cityModel.isPresent()){
            long cityId = cityModel.get().getId();
            mav.addObject("cityId", cityId);
        }

//        if (!experienceModel.isEmpty()){
            mav.addObject("maxPrice", form.getActivityPriceMax());
//        }

        return mav;
    }
}
